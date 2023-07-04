package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.Citizen;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.CreateDiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.CreateDiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.DiplomaResponseApi;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Country;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaStatusRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.ApplicationRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.CountryRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiplomaService {

    private final DiplomaRepository diplomaRepository;
    private final DiplomaApi diplomaApi;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final ApplicationRepository applicationRepository;

    //bu integratsiyadan kelayotgan diplomlar
    @Transactional
    public Result saveAndGetDiplomaByDiplomaApi(Principal principal) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            List<Diploma> diplomaByUser = diplomaRepository.findAllDiplomaByUser(principal.getName());
            if (diplomaByUser.size() == 0) {
                List<DiplomaResponseApi> diplomas = diplomaApi.getDiploma(user.getPinfl());
                if (diplomas.size() == 0) {
                    return new Result("Sizning diplom malumotlaringiz d-arxiv.edu.uz tizimidan topilmadi", false);
                }
                List<DiplomaResponse> diplomaResponses = new ArrayList<>();
                diplomas.stream().sorted(Comparator.comparing(DiplomaResponseApi::getStatusId)).forEach(diploma -> {
                    if (String.valueOf(diploma.getDegreeId()).equals(ApiConstant.DEGREE_ID)) {
                        if (diploma.getStatusId() == 1) {
                            Diploma diplomaNew = createDiplomaNew(diploma);
                            diplomaNew.setUser(user);
                            Diploma save = diplomaRepository.save(diplomaNew);
                            diplomaResponses.add(new DiplomaResponse(save));
                        } else if (diploma.getStatusId() == 2) {
                            Diploma diplomaNew = createDiplomaNew(diploma);
                            diplomaNew.setUser(user);
                            Diploma save = diplomaRepository.save(diplomaNew);
                            diplomaResponses.add(new DiplomaResponse(save));
                        } else if (diploma.getStatusId() == 8) {
                            Diploma diplomaNew = createDiplomaNew(diploma);
                            diplomaNew.setUser(user);
                            Diploma save = diplomaRepository.save(diplomaNew);
                            diplomaResponses.add(new DiplomaResponse(save));
                        }
                    }
                });
                return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, diplomaResponses);
            }
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, diplomaByUser.stream().map(DiplomaResponse::new).toList());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }

    @Transactional
    public Result createDiploma(Principal principal, DiplomaRequest request) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            List<Diploma> diplomaList = diplomaRepository.findAllDiplomaByUser(principal.getName());
            Country country = countryRepository.findById(request.getCountryId()).get();
            if (diplomaList.size() == 0) {
                if (request.getCountryId() == 1) {
                    Citizen citizen = new Citizen(user);
                    CreateDiplomaRequest createDiplomaRequest = new CreateDiplomaRequest(request.getDiplomaRequestApi(), citizen);
                    CreateDiplomaResponse diploma = diplomaApi.createDiploma(createDiplomaRequest);
                    DiplomaResponseApi diplomaResponseApi = diploma.getDataCreateDiplomaResponse().getDiplomaResponseApi();
                    Diploma diplomaNew = new Diploma();
                    diplomaNew.setUser(user);
                    diplomaNew.setDiplomaSerialId(diplomaResponseApi.getDiplomaSerialId());
                    diplomaNew.setDiplomaId(diplomaResponseApi.getId());
                    diplomaNew.setDiplomaSerialAndNumber(diplomaResponseApi.getDiplomaSerial() + " " + diplomaResponseApi.getDiplomaNumber());
                    diplomaNew.setDegreeId(diplomaResponseApi.getDegreeId());
                    diplomaNew.setDegreeName(diplomaResponseApi.getDegreeName());
                    diplomaNew.setEduFinishingDate(diplomaResponseApi.getEduFinishingDate());
                    diplomaNew.setEduFormId(diplomaResponseApi.getEduFormId());
                    diplomaNew.setEduFormName(diplomaResponseApi.getEduFormName());
                    diplomaNew.setInstitutionId(diplomaResponseApi.getInstitutionId());
                    diplomaNew.setInstitutionName(diplomaResponseApi.getInstitutionName());
                    diplomaNew.setInstitutionOldId(diplomaResponseApi.getInstitutionOldNameId());
                    diplomaNew.setInstitutionOldName(diplomaResponseApi.getInstitutionOldName());
                    diplomaNew.setSpecialityId(diplomaResponseApi.getSpecialityId());
                    diplomaNew.setSpecialityName(diplomaResponseApi.getSpecialityName());
                    diplomaNew.setCountryName(country.getName());
                    diplomaNew.setStatusId(diplomaResponseApi.getStatusId());
                    diplomaNew.setStatusName(diplomaResponseApi.getStatusName());
                    if (request.getSpeciality_custom_name() != null) {
                        diplomaNew.setSpecialityCustomName(request.getDiplomaRequestApi().getSpeciality_custom_name());
                    }
                    diplomaRepository.save(diplomaNew);
                    return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
                }
                Diploma diplomaNew = new Diploma();
                diplomaNew.setUser(user);
                diplomaNew.setDiplomaSerialAndNumber(request.getDiplomaSerial() + " " + request.getDiplomaRequestApi().getDiplomaNumber());
                diplomaNew.setDegreeId(request.getDiplomaRequestApi().getDegreeId());
                diplomaNew.setDegreeName(request.getDegreeName());
                diplomaNew.setEduFinishingDate(request.getEduFinishingDate());
                diplomaNew.setInstitutionName(request.getForeignInstitutionName());
                if (request.getSpeciality_custom_name() != null) {
                    diplomaNew.setSpecialityCustomName(request.getDiplomaRequestApi().getSpeciality_custom_name());
                }
                diplomaNew.setCountryName(country.getName());
                diplomaRepository.save(diplomaNew);
                return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
            }
            return new Result("Sizda diplom mavjud", false);
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }

    @Transactional
    public Result updateDiploma(Principal principal, DiplomaRequest request) {
        try {
            User user = userRepository.findUserByPinfl(principal.getName()).get();
            Diploma diplomaNew = diplomaRepository.findDiplomaByDiplomaIdAndUser(request.getId(), principal.getName()).get();
            Country country = countryRepository.findById(request.getCountryId()).get();
            if (request.getCountryId() == 1) {
                Citizen citizen = new Citizen(user);
                CreateDiplomaRequest createDiplomaRequest = new CreateDiplomaRequest(request.getDiplomaRequestApi(), citizen);
                CreateDiplomaResponse createDiplomaResponse = diplomaApi.createDiploma(createDiplomaRequest);
                DiplomaResponseApi diplomaResponseApi = createDiplomaResponse.getDataCreateDiplomaResponse().getDiplomaResponseApi();
                diplomaNew.setUser(user);
                diplomaNew.setDiplomaSerialId(diplomaResponseApi.getDiplomaSerialId());
                diplomaNew.setDiplomaId(diplomaResponseApi.getId());
                diplomaNew.setDiplomaSerialAndNumber(diplomaResponseApi.getDiplomaSerial() + " " + diplomaResponseApi.getDiplomaNumber());
                diplomaNew.setDegreeId(diplomaResponseApi.getDegreeId());
                diplomaNew.setDegreeName(diplomaResponseApi.getDegreeName());
                diplomaNew.setEduFinishingDate(diplomaResponseApi.getEduFinishingDate());
                diplomaNew.setEduFormId(diplomaResponseApi.getEduFormId());
                diplomaNew.setEduFormName(diplomaResponseApi.getEduFormName());
                diplomaNew.setInstitutionId(diplomaResponseApi.getInstitutionId());
                diplomaNew.setInstitutionName(diplomaResponseApi.getInstitutionName());
                diplomaNew.setInstitutionOldId(diplomaResponseApi.getInstitutionOldNameId());
                diplomaNew.setInstitutionOldName(diplomaResponseApi.getInstitutionOldName());
                diplomaNew.setSpecialityId(diplomaResponseApi.getSpecialityId());
                diplomaNew.setSpecialityName(diplomaResponseApi.getSpecialityName());
                diplomaNew.setCountryName(country.getName());
                diplomaNew.setStatusId(diplomaResponseApi.getStatusId());
                diplomaNew.setStatusName(diplomaResponseApi.getStatusName());
                diplomaNew.setModifiedDate(LocalDateTime.now());
                if (request.getSpeciality_custom_name() != null) {
                    diplomaNew.setSpecialityCustomName(request.getDiplomaRequestApi().getSpeciality_custom_name());
                }
                diplomaRepository.save(diplomaNew);
                return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
            }
            diplomaNew.setUser(user);
            diplomaNew.setDiplomaSerialAndNumber(request.getDiplomaSerial() + " " + request.getDiplomaRequestApi().getDiplomaNumber());
            diplomaNew.setDegreeId(request.getDiplomaRequestApi().getDegreeId());
            diplomaNew.setDegreeName(request.getDegreeName());
            diplomaNew.setEduFinishingDate(request.getEduFinishingDate());
            diplomaNew.setInstitutionName(request.getForeignInstitutionName());
            if (request.getSpeciality_custom_name() != null) {
                diplomaNew.setSpecialityCustomName(request.getDiplomaRequestApi().getSpeciality_custom_name());
            }
            diplomaNew.setCountryName(country.getName());
            diplomaNew.setModifiedDate(LocalDateTime.now());
            diplomaRepository.save(diplomaNew);
            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
        }
    }

    @Transactional
    public Result changeDiplomaStatusApi(Integer diplomaId, Integer statusId, String statusName) {
        try {
            Optional<Diploma> diploma = diplomaRepository.findDiplomaByDiplomaId(diplomaId);
            if (diploma.isEmpty()) {
                return new Result(ResponseMessage.NOT_FOUND.getMessage() + ": " + diplomaId, false);
            }
            diploma.get().setStatusId(statusId);
            diploma.get().setStatusName(statusName);
            diplomaRepository.save(diploma.get());
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    @Transactional
    public Result diplomaIsActive(String pinfl, Integer diplomaId, Boolean b) {
        try {
            Boolean aBoolean = diplomaRepository.existsDiplomaByIsActiveCount(pinfl);
            if (aBoolean) {
                return new Result("Sizda belgilangan diplom bor", false);
            }
            Diploma diploma = diplomaRepository.findDiplomaByDiplomaIdAndUser(diplomaId, pinfl).get();
            diploma.setIsActive(b);
            diplomaRepository.save(diploma);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public List<DiplomaResponse> getAllDiplomaByPrincipal(String pinfl) {
        return diplomaRepository.findAllDiplomaByUser(pinfl).stream().map(DiplomaResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public DiplomaResponse getDiplomaByPrincipal(int diplomaId, String pinfl) {
        return diplomaRepository.findDiplomaByDiplomaIdAndUser(diplomaId, pinfl).map(DiplomaResponse::new).get();
    }

    private Diploma createDiplomaNew(DiplomaResponseApi diplomaResponseApi) {
        Diploma diplomaNew = new Diploma();
        diplomaNew.setDiplomaSerialId(diplomaResponseApi.getDiplomaSerialId());
        diplomaNew.setDiplomaId(diplomaResponseApi.getId());
        diplomaNew.setDiplomaSerialAndNumber(diplomaResponseApi.getDiplomaSerial() + " " + diplomaResponseApi.getDiplomaNumber());
        diplomaNew.setDegreeId(diplomaResponseApi.getDegreeId());
        diplomaNew.setDegreeName(diplomaResponseApi.getDegreeName());
        diplomaNew.setEduFinishingDate(diplomaResponseApi.getEduFinishingDate());
        diplomaNew.setEduFormId(diplomaResponseApi.getEduFormId());
        diplomaNew.setEduFormName(diplomaResponseApi.getEduFormName());
        diplomaNew.setInstitutionId(diplomaResponseApi.getInstitutionId());
        diplomaNew.setInstitutionName(diplomaResponseApi.getInstitutionName());
        diplomaNew.setInstitutionOldId(diplomaResponseApi.getInstitutionOldNameId());
        diplomaNew.setInstitutionOldName(diplomaResponseApi.getInstitutionOldName());
        diplomaNew.setSpecialityId(diplomaResponseApi.getSpecialityId());
        diplomaNew.setSpecialityName(diplomaResponseApi.getSpecialityName());
        diplomaNew.setStatusId(diplomaResponseApi.getStatusId());
        diplomaNew.setStatusName(diplomaResponseApi.getStatusName());
        return diplomaNew;
    }

    @Transactional(readOnly = true)
    public Page<DiplomaResponse> getAllDiploma(int page, int size) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return diplomaRepository.findAll(pageable).map(DiplomaResponse::new);
    }

    @Transactional
    public Result deleteDiploma(Integer id, Principal principal) {
        try {
            Diploma diploma = diplomaRepository.findDiplomaByDiplomaIdAndUser(id, principal.getName()).get();
            diplomaRepository.deleteById(id);
            return new Result(ResponseMessage.SUCCESSFULLY_DELETED.getMessage(), true);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_DELETED.getMessage(), false);
        }
    }


    @Transactional(readOnly = true)
    public List<Country> getAllCountry() {
        return countryRepository.findAll(Sort.by("id"));
    }
}

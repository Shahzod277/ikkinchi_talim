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
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.DiplomaResponseApi;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.*;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;
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
    private final UserService userService;
    private final ApplicationRepository applicationRepository;
    private final DiplomaSpecialityRepository diplomaSpecialityRepository;
    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final EduFormRepository eduFormRepository;
    private final DurationRepository durationRepository;
//private String tokena="Mm0Q8nSW2sr6Vv2K9RK0FmUbwlXzD6BcBgdQ0l2OZhSMqlAYDhBRtuY2SD0XPYctuITQFLGE+R1+kIMjWms6lHJ02ZgDIsmjQpyRaCGB8jmoEn/7MyKO1R502lGgRMkg230HCRGIf4kO4w7UIp9a/WxlQ4iEg6nr00e1QoTsVLk=";

    @Transactional
    public Result saveAndGetDiplomaByDiplomaApi(String token) {
        try {
            Result result = userService.checkUser(token);
            if (!result.isSuccess()) {
                return result;
            }
            Integer id = (Integer) result.getObject();
            User user = userRepository.findById(id).get();
            List<Diploma> diplomaByUser = diplomaRepository.findAllDiplomaByUser(id);
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
    public Result createDiploma(String token, DiplomaRequest request) {
        try {
            Result result = userService.checkUser(token);
            if (!result.isSuccess()) {
                return result;
            }
            Integer id = (Integer) result.getObject();
            User user = userRepository.findById(id).get();
            List<Diploma> diplomaList = diplomaRepository.findAllDiplomaByUser(id);
            if (diplomaList.size() == 0) {
                Country country = countryRepository.findById(request.getCountryId()).get();
                Duration duration = durationRepository.findById(request.getEduDurationId()).get();
                EduForm eduForm = eduFormRepository.findById(request.getEduFormId()).get();
                if (request.getCountryId() == 1) {
                    DiplomaSpeciality diplomaSpeciality = diplomaSpecialityRepository.findById(request.getSpecialityId()).get();
                    DiplomaOldInstitution diplomaOldInstitution = diplomaOldInstitutionRepository.findById(request.getInstitutionId()).get();

                    Diploma diplomaNew = new Diploma();
                    diplomaNew.setUser(user);
                    diplomaNew.setDiplomaSerialId(request.getDiplomaSerialId());
                    diplomaNew.setDiplomaNumber(request.getDiplomaNumber());
                    diplomaNew.setDiplomaSerial(request.getDiplomaSerial());
                    diplomaNew.setDegreeId(2);
                    diplomaNew.setEduDurationId(duration.getDurationId());
                    diplomaNew.setEduDurationName(duration.getNameOz());
                    diplomaNew.setDegreeName("Bakalavr");
                    diplomaNew.setEduFormId(request.getEduFormId());
                    diplomaNew.setEduFormName(eduForm.getNameOz());
                    diplomaNew.setInstitutionId(diplomaOldInstitution.getClassificatorId());
                    diplomaNew.setInstitutionIdDb(diplomaOldInstitution.getId());
                    diplomaNew.setEduStartingDate(request.getEduStartingDate());
                    diplomaNew.setEduFinishingDate(request.getEduFinishingDate());
                    diplomaNew.setInstitutionName(diplomaOldInstitution.getInstitutionName());
                    diplomaNew.setInstitutionOldId(diplomaOldInstitution.getInstitutionOldId());
                    diplomaNew.setInstitutionOldName(diplomaOldInstitution.getInstitutionOldNameOz());
                    diplomaNew.setSpecialityIdDb(diplomaSpeciality.getId());
                    diplomaNew.setSpecialityId(diplomaSpeciality.getSpecialitiesId());
                    diplomaNew.setSpecialityName(diplomaSpeciality.getNameOz());
                    diplomaNew.setCountryId(country.getId());
                    diplomaNew.setCountryName(country.getName());
                    diplomaNew.setIlovaUrl(request.getIlovaUrl());
                    diplomaNew.setDiplomaUrl(request.getDiplomaUrl());

                    diplomaNew.setStatusName("Haqiqiyligi tekshirilmoqda");
                    if (request.getSpeciality_custom_name() != null) {
                        diplomaNew.setSpecialityCustomName(request.getSpeciality_custom_name());
                    }
                    diplomaRepository.save(diplomaNew);
                    return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
                }
                Diploma diplomaNew = new Diploma();
                diplomaNew.setUser(user);
                diplomaNew.setDiplomaNumber(request.getDiplomaNumber());
                diplomaNew.setCountryId(country.getId());
                diplomaNew.setCountryName(country.getName());
                diplomaNew.setDiplomaSerial(request.getDiplomaSerial());
                diplomaNew.setStatusName("Haqiqiyligi tekshirilmoqda");
                diplomaNew.setDegreeId(2);
                diplomaNew.setDegreeName("Bakalavr");
//                diplomaNew.setEduFormId(eduForm.getId());
                diplomaNew.setEduFormName(request.getEduFormName());
                diplomaNew.setEduDurationId(duration.getDurationId());
                diplomaNew.setEduDurationName(duration.getNameOz());
                diplomaNew.setEduFinishingDate(request.getEduFinishingDate());
                diplomaNew.setEduStartingDate(request.getEduStartingDate());
                diplomaNew.setInstitutionName(request.getForeignOtmName());
                diplomaNew.setSpecialityCustomName(request.getSpeciality_custom_name());
                diplomaNew.setIlovaUrl(request.getIlovaUrl());
                diplomaNew.setDiplomaUrl(request.getDiplomaUrl());
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
    public Result updateDiploma(String token, Integer id, DiplomaRequest request) {
//        try {
            Result result = userService.checkUser(token);
            if (!result.isSuccess()) {
                return result;
            }
            Integer userId = (Integer) result.getObject();
            User user = userRepository.findById(id).get();
            Diploma diplomaNew = diplomaRepository.findDiplomaByDiplomaIdAndUser(id, userId).get();
            Country country = countryRepository.findById(request.getCountryId()).get();
            Duration duration = durationRepository.findById(request.getEduDurationId()).get();
            EduForm eduForm = eduFormRepository.findById(request.getEduFormId()).get();
            if (request.getCountryId() == 1) {
                DiplomaSpeciality diplomaSpeciality = diplomaSpecialityRepository.findById(request.getSpecialityId()).get();
                DiplomaOldInstitution diplomaOldInstitution = diplomaOldInstitutionRepository.findById(request.getInstitutionId()).get();
                diplomaNew.setUser(user);
                diplomaNew.setDiplomaSerialId(request.getDiplomaSerialId());
                diplomaNew.setDiplomaNumber(request.getDiplomaNumber());
                diplomaNew.setDiplomaSerial(request.getDiplomaSerial());
                diplomaNew.setDegreeId(2);
                diplomaNew.setEduDurationId(duration.getDurationId());
                diplomaNew.setEduDurationName(duration.getNameOz());
                diplomaNew.setDegreeName("Bakalavr");
                diplomaNew.setEduFormId(request.getEduFormId());
                diplomaNew.setEduFormName(eduForm.getNameOz());
                diplomaNew.setInstitutionId(diplomaOldInstitution.getClassificatorId());
                diplomaNew.setInstitutionIdDb(diplomaOldInstitution.getId());
                diplomaNew.setEduStartingDate(request.getEduStartingDate());
                diplomaNew.setEduFinishingDate(request.getEduFinishingDate());
                diplomaNew.setInstitutionName(diplomaOldInstitution.getInstitutionName());
                diplomaNew.setInstitutionOldId(diplomaOldInstitution.getInstitutionOldId());
                diplomaNew.setInstitutionOldName(diplomaOldInstitution.getInstitutionOldNameOz());
                diplomaNew.setSpecialityIdDb(diplomaSpeciality.getId());
                diplomaNew.setSpecialityId(diplomaSpeciality.getSpecialitiesId());
                diplomaNew.setSpecialityName(diplomaSpeciality.getNameOz());
                diplomaNew.setCountryId(country.getId());
                diplomaNew.setCountryName(country.getName());
                diplomaNew.setIlovaUrl(request.getIlovaUrl());
                diplomaNew.setDiplomaUrl(request.getDiplomaUrl());

                diplomaNew.setStatusName("Haqiqiyligi tekshirilmoqda");
                if (request.getSpeciality_custom_name() != null) {
                    diplomaNew.setSpecialityCustomName(request.getSpeciality_custom_name());
                }
                diplomaRepository.save(diplomaNew);
                return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
            }
            diplomaNew.setUser(user);
            diplomaNew.setDiplomaNumber(request.getDiplomaNumber());
            diplomaNew.setCountryId(country.getId());
            diplomaNew.setCountryName(country.getName());
            diplomaNew.setDiplomaSerial(request.getDiplomaSerial());
            diplomaNew.setStatusName("Haqiqiyligi tekshirilmoqda");
            diplomaNew.setDegreeId(2);
            diplomaNew.setDegreeName("Bakalavr");
//            diplomaNew.setEduFormId(eduForm.getId());
            diplomaNew.setEduFormName(request.getEduFormName());
            diplomaNew.setEduDurationId(duration.getDurationId());
            diplomaNew.setEduDurationName(duration.getNameOz());
            diplomaNew.setEduStartingDate(request.getEduStartingDate());
            diplomaNew.setEduFinishingDate(request.getEduFinishingDate());
            diplomaNew.setInstitutionName(request.getForeignOtmName());
            diplomaNew.setSpecialityCustomName(request.getSpeciality_custom_name());
            diplomaNew.setIlovaUrl(request.getIlovaUrl());
            diplomaNew.setDiplomaUrl(request.getDiplomaUrl());
            diplomaNew.setCountryName(country.getName());
            diplomaRepository.save(diplomaNew);

            return new Result(ResponseMessage.SUCCESSFULLY_UPDATE.getMessage(), true);
//        } catch (Exception exception) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return new Result(ResponseMessage.ERROR_UPDATE.getMessage(), false);
//        }
    }


    @Transactional
    public Result diplomaIsActive(String token, Integer diplomaId) {
        try {
            Result result = userService.checkUser(token);
            if (!result.isSuccess()) {
                return result;
            }
            List<Diploma> diplomaList = new ArrayList<>();
            Integer id = (Integer) result.getObject();
            Optional<Diploma> diplomaByUser = diplomaRepository.findActiveDiplomaByUser(id);
            if (diplomaByUser.isPresent()) {
                diplomaByUser.get().setIsActive(false);
                diplomaList.add(diplomaByUser.get());
            }
            Diploma diploma = diplomaRepository.findDiplomaByDiplomaIdAndUser(diplomaId, id).get();
            diploma.setIsActive(true);
            diplomaList.add(diploma);
            diplomaRepository.saveAll(diplomaList);
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR.getMessage(), false);
        }
    }

    private Diploma createDiplomaNew(DiplomaResponseApi diplomaResponseApi) {
        Country country = countryRepository.findById(1).get();
        Diploma diplomaNew = new Diploma();
        Optional<DiplomaOldInstitution> optional = diplomaOldInstitutionRepository.findDiplomaOldInstitutionByOldId(diplomaResponseApi.getInstitutionId(), diplomaResponseApi.getInstitutionOldNameId());
        optional.ifPresent(diplomaOldInstitution -> diplomaNew.setInstitutionIdDb(diplomaOldInstitution.getId()));
        Optional<DiplomaSpeciality> speciality = diplomaSpecialityRepository.findDiplomaSpecialitiesById(diplomaResponseApi.getSpecialityId());
        speciality.ifPresent(diplomaSpeciality -> diplomaNew.setSpecialityIdDb(diplomaSpeciality.getId()));
        diplomaNew.setCountryId(1);
        diplomaNew.setCountryName(country.getName());
        diplomaNew.setDiplomaSerialId(diplomaResponseApi.getDiplomaSerialId());
        diplomaNew.setDiplomaId(diplomaResponseApi.getId());
        diplomaNew.setDiplomaSerial(diplomaResponseApi.getDiplomaSerial());
        diplomaNew.setDiplomaNumber(diplomaResponseApi.getDiplomaNumber());
        diplomaNew.setDiplomaSerialId(diplomaResponseApi.getDiplomaSerialId());
        diplomaNew.setDegreeId(diplomaResponseApi.getDegreeId());
        diplomaNew.setDegreeName(diplomaResponseApi.getDegreeName());
        diplomaNew.setEduFinishingDate(diplomaResponseApi.getEduFinishingDate());
        diplomaNew.setEduStartingDate(diplomaResponseApi.getEduStartingDate());
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
        diplomaNew.setEduDurationName(diplomaResponseApi.getEduDurationName());
        diplomaNew.setEduDurationId(diplomaResponseApi.getEduDurationId());
        return diplomaNew;
    }

    @Transactional
    public Result deleteDiploma(Integer id, String token) {
        try {
            Result result = userService.checkUser(token);
            if (!result.isSuccess()) {
                return result;
            }
            Integer userId = (Integer) result.getObject();
            Diploma diploma = diplomaRepository.findDiplomaByDiplomaIdAndUser(id, userId).get();
            diplomaRepository.delete(diploma);
            return new Result(ResponseMessage.SUCCESSFULLY_DELETED.getMessage(), true);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_DELETED.getMessage(), false);
        }
    }

}

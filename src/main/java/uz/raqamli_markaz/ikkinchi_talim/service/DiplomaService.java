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
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.DiplomaResponseInfo;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.CreateDiplomaRequest;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.CreateDiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Country;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaStatusRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.ApplicationRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.CountryRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

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
    public Result saveAndGetDiplomaByDiplomaApi(String pinfl) {

        List<Diploma> diplomaByUser = diplomaRepository.findAllDiplomaByUser(pinfl);
        if (diplomaByUser.size() == 0) {
            List<DiplomaResponseInfo> diplomas = diplomaApi.getDiploma(pinfl);
            if (diplomas.size() == 0) {
                return new Result("Sizning diplom malumotlaringiz d-arxiv.edu.uz tizimidan topilmadi", false);
            }
            List<Diploma> diplomaList = new ArrayList<>();
            diplomas.forEach(diploma -> {
                Thread thread = new Thread(() -> {
                    if (String.valueOf(diploma.getDegreeId()).equals(ApiConstant.DEGREE_ID)) {
                        Diploma diplomaNew = new Diploma();
                        diplomaNew.setDiplomaSerialId(diploma.getDiplomaSerialId());
                        diplomaNew.setDiplomaSerialAndNumber(diploma.getDiplomaSerial() + " " + diploma.getDiplomaNumber());
                        diplomaNew.setDegreeId(diploma.getDegreeId());
                        diplomaNew.setDegreeName(diploma.getDegreeName());
                        diplomaNew.setEduFinishingDate(diploma.getEduFinishingDate());
                        diplomaNew.setEduFormId(diploma.getEduFormId());
                        diplomaNew.setEduFormName(diploma.getEduFormName());
                        diplomaNew.setInstitutionId(diploma.getInstitutionId());
                        diplomaNew.setInstitutionName(diploma.getInstitutionName());
                        diplomaNew.setInstitutionOldId(diploma.getInstitutionOldNameId());
                        diplomaNew.setInstitutionOldName(diploma.getInstitutionOldName());
                        diplomaNew.setSpecialityId(diploma.getSpecialityId());
                        diplomaNew.setSpecialityName(diploma.getSpecialityName());
                        diplomaList.add(diplomaNew);
                    }
                });
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new RuntimeException(e);
                }
            });
            List<DiplomaResponse> list = diplomaRepository.saveAll(diplomaList).stream().map(DiplomaResponse::new).toList();
            return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, list);
        }
        return new Result(ResponseMessage.SUCCESSFULLY.getMessage(), true, diplomaByUser.stream().map(DiplomaResponse::new).toList());
    }

    @Transactional
    public Result createDiploma(CreateDiplomaRequest request) {
        try {
            CreateDiplomaResponse diploma = diplomaApi.createDiploma(request);
            uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.DiplomaResponse diplomaResponse = diploma.getDataCreateDiplomaResponse().getDiplomaResponse();
            Diploma diplomaNew = new Diploma();
            diplomaNew.setDiplomaSerialId(diplomaResponse.getDiplomaSerialId());
            diplomaNew.setDiplomaSerialAndNumber(diplomaResponse.getDiplomaSerial() + " " + diplomaResponse.getDiplomaNumber());
            diplomaNew.setDegreeId(diplomaResponse.getDegreeId());
            diplomaNew.setDegreeName(diplomaResponse.getDegreeName());
            diplomaNew.setEduFinishingDate(diplomaResponse.getEduFinishingDate());
            diplomaNew.setEduFormId(diplomaResponse.getEduFormId());
            diplomaNew.setEduFormName(diplomaResponse.getEduFormName());
            diplomaNew.setInstitutionId(diplomaResponse.getInstitutionId());
            diplomaNew.setInstitutionName(diplomaResponse.getInstitutionName());
            diplomaNew.setInstitutionOldId(diplomaResponse.getInstitutionOldNameId());
            diplomaNew.setInstitutionOldName(diplomaResponse.getInstitutionOldName());
            diplomaNew.setSpecialityId(diplomaResponse.getSpecialityId());
            diplomaNew.setSpecialityName(diplomaResponse.getSpecialityName());
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }

    // Admin panel

    @Transactional
    public Result updateDiplomaStatus(DiplomaStatusRequest request) {
        try {
            Application application = applicationRepository.findById(request.getApplicationId()).get();
            application.setDiplomaStatus(request.getDiplomaStatus());
            application.setMessage(request.getMessage());
            applicationRepository.save(application);
            return new Result(ResponseMessage.SUCCESSFULLY_SAVED.getMessage(), true);
        } catch (Exception exception) {
            return new Result(ResponseMessage.ERROR_SAVED.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public Page<DiplomaResponse> getAllDiploma(int page, int size) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return diplomaRepository.findAll(pageable).map(DiplomaResponse::new);
    }

    @Transactional(readOnly = true)
    public DiplomaResponse getDiplomaById(int diplomaId) {
        try {
            Diploma diploma = diplomaRepository.findById(diplomaId).get();
            return new DiplomaResponse(diploma);
        } catch (Exception ex) {
            return new DiplomaResponse();
        }
    }

    @Transactional
    public Result deleteDiploma(int diplomaId) {
        try {
            diplomaRepository.deleteById(diplomaId);
            return new Result(ResponseMessage.SUCCESSFULLY_DELETED.getMessage(), true);
        } catch (Exception ex) {
            return new Result(ResponseMessage.ERROR_DELETED.getMessage(), false);
        }
    }

    @Transactional(readOnly = true)
    public List<Country> getAllCountry() {
        return countryRepository.findAll(Sort.by("id"));
    }
}

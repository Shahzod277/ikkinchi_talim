package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.api.diplom_api.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.Diploma;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.Country;
import uz.raqamli_markaz.ikkinchi_talim.model.request.DiplomaStatusRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.ResponseMessage;
import uz.raqamli_markaz.ikkinchi_talim.model.response.Result;
import uz.raqamli_markaz.ikkinchi_talim.repository.ApplicationRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.CountryRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.UserRepository;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiplomaService {

    private final DiplomaRepository diplomaRepository;
    private final DiplomaApi diplomaApi;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final ApplicationRepository applicationRepository;

//    @Transactional
//    public void saveDiplomaByApi(String pinfl, EnrolleeInfo enrolleeInfo) {
//            List<DiplomaResponseInfo> diplomas = diplomaApi.getDiploma(pinfl);
//            List<Diploma> diplomaList = new ArrayList<>();
//            diplomas.forEach(d -> {
//                Diploma diploma = new Diploma();
//                diploma.setCountryName("O'zbekiston");
//                diploma.setInstitutionId(d.getInstitutionId());
//                diploma.setInstitutionName(d.getInstitutionName());
//                diploma.setInstitutionOldNameId(d.getInstitutionOldNameId());
//                diploma.setInstitutionOldName(d.getInstitutionOldName());
//                diploma.setSpecialityId(d.getSpecialityId());
//                diploma.setSpecialityName(d.getSpecialityName());
//                diploma.setDiplomaSerialAndNumber(d.getDiplomaSerial()+d.getDiplomaNumber());
//                diploma.setDegreeId(d.getDegreeId());
//                diploma.setDegreeName(d.getDegreeName());
//                diploma.setEduFormId(d.getEduFormId());
//                diploma.setEduFormName(d.getEduFormName());
//                diploma.setEduFinishingDate(d.getEduFinishingDate());
//                diploma.setEnrolleeInfo(enrolleeInfo);
//                diplomaList.add(diploma);
//            });
//            if (diplomaList.size()>0){
//                diplomaRepository.saveAll(diplomaList);
//            }
//    }

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

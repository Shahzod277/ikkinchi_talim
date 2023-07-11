package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Country;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Duration;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaInstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.EduFormResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.OtmProjection;
import uz.raqamli_markaz.ikkinchi_talim.model.response.SpecialityProjection;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassificatorService {

    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final DiplomaSpecialityRepository diplomaSpecialityRepository;
    private final DiplomaSerialRepository diplomaSerialRepository;
    private final EduFormRepository eduFormRepository;
    private final CountryRepository countryRepository;
    private final DurationRepository durationRepository;
    private final KvotaRepository kvotaRepository;

    @Transactional(readOnly = true)
    public List<DiplomaSerial> getAllDiplomaSerials() {
        return diplomaSerialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<SpecialityProjection> getSpecialitiesByInstitutionId(Integer id) {
        return diplomaSpecialityRepository.findDiplomaSpecialitiesByInstitutionId(id);
    }

    @Transactional(readOnly = true)
    public List<DiplomaInstitutionResponse> getAllDiplomaInstitutionResponse() {
        return diplomaOldInstitutionRepository.findAll().stream().sorted(Comparator.comparing(DiplomaOldInstitution::getInstitutionOldNameOz)).map(DiplomaInstitutionResponse::new).toList();
    }


    @Transactional(readOnly = true)
    public List<EduFormResponse> getAllEduFormResponses() {
        return eduFormRepository.findAll().stream().map(EduFormResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public List<Country> getAllCountry() {
        return countryRepository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public List<Duration> getAllDuration() {
        return durationRepository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public List<OtmProjection> getOtmByKvota() {
        return kvotaRepository.getOtmByKvota();
    }

    @Transactional(readOnly = true)
    public List<OtmProjection> getEduForm(String code) {
        return kvotaRepository.getEduFormByOtmCode(code);
    }

    @Transactional(readOnly = true)
    public List<OtmProjection> getLanguageByOtmCodeAndEduFormCode(String code, String otmcode) {
        return kvotaRepository.getLanguageByOtmCodeAndEduFormCode(code, otmcode);
    }

    @Transactional(readOnly = true)
    public List<OtmProjection> getSpeciality(String languageCode, String code, String otmcode) {
        return kvotaRepository.getSpeciality(languageCode, code, otmcode);
    }
}

package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.domain.Kvota;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.*;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaInstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaSpecialityResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.EduFormResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.OtmProjection;
import uz.raqamli_markaz.ikkinchi_talim.repository.*;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClassificatorService {

    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final DiplomaSpecialityRepository diplomaSpecialityRepository;
    private final DiplomaSerialRepository diplomaSerialRepository;
    private final EduFormRepository eduFormRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final DurationRepository durationRepository;
    private final UniversityRepository universityRepository;
    private final KvotaRepository kvotaRepository;

    @Transactional(readOnly = true)
    public List<DiplomaSerial> getAllDiplomaSerials() {
        return diplomaSerialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DiplomaSpeciality> getSpecialitiesByInstitutionId(Integer institutionId) {
        return diplomaSpecialityRepository.findDiplomaSpecialitiesByInstitutionId(institutionId);
    }

    @Transactional(readOnly = true)
    public List<DiplomaInstitutionResponse> getAllDiplomaInstitutionResponse() {
        return diplomaOldInstitutionRepository.findAll().stream().map(DiplomaInstitutionResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public DiplomaInstitutionResponse getAllDiplomaInstitutionById(Integer institutionOldId) {
        return diplomaOldInstitutionRepository.findDiplomaOldInstitutionByOldId(institutionOldId)
                .map(DiplomaInstitutionResponse::new).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<DiplomaSpecialityResponse> getAllDiplomaSpecialityResponse() {
        return diplomaSpecialityRepository.findAll().stream()
                .map(DiplomaSpecialityResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public DiplomaSpecialityResponse getDiplomaSpecialityById(Integer specialityId) {
        return diplomaSpecialityRepository.findDiplomaSpecialitiesById(specialityId)
                .map(DiplomaSpecialityResponse::new).orElse(null);
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
    public List<OtmProjection> getLanguageByOtmCodeAndEduFormCode(String code,String otmcode) {
        return kvotaRepository.getLanguageByOtmCodeAndEduFormCode(code,otmcode);
    }
    @Transactional(readOnly = true)
    public List<OtmProjection> getSpeciality(String languageCode,String code,String otmcode) {
        return kvotaRepository.getSpeciality(languageCode,code,otmcode);
    }
}

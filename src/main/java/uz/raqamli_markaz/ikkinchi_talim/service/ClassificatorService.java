package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaInstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaSpecialityResponse;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaOldInstitutionRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaSerialRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaSpecialityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassificatorService {

    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final DiplomaSpecialityRepository diplomaSpecialityRepository;
    private final DiplomaSerialRepository diplomaSerialRepository;

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

}

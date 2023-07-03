package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;
import uz.raqamli_markaz.ikkinchi_talim.model.response.FutureInstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaInstitutionRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaOldInstitutionRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaSerialRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaSpecialityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClasificatorService {

    private final DiplomaInstitutionRepository diplomaInstitutionRepository;
    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final DiplomaSpecialityRepository diplomaSpecialityRepository;
    private final DiplomaSerialRepository diplomaSerialRepository;



    @Transactional(readOnly = true)
    public List<DiplomaSerial> getAllDiplomaSerials() {
        return diplomaSerialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DiplomaOldInstitution> getAllOldNameInstitution() {
        return diplomaOldInstitutionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DiplomaSpeciality> getSpecialitiesByInstitutionId(Integer institutionId) {
        return diplomaSpecialityRepository.findDiplomaSpecialitiesByInstitutionId(institutionId);
    }


    @Transactional(readOnly = true)
    public Page<FutureInstitutionResponse> getAllFutureInstitution(int page, int size) {
        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return diplomaInstitutionRepository.findAll(pageable)
                .map(FutureInstitutionResponse::new);
    }

}

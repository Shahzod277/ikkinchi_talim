package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaInstitutionRepository;

@Component
@RequiredArgsConstructor
public class Utils {

    private final DiplomaInstitutionRepository diplomaInstitutionRepository;



}

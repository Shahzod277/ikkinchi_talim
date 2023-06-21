package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.InstitutionDataItem;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.InstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.Institutions;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaInstitutionRepository;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Utils {

    private final DiplomaInstitutionRepository diplomaInstitutionRepository;
    private final DiplomaApi diplomaApi;

    @Transactional
    public void saveInstitution() {
        try {
            InstitutionResponse institutionResponse = diplomaApi.getInstitutions();
            Institutions institutions = institutionResponse.getInstitutionData().getInstitutions();
            List<InstitutionDataItem> data = institutions.getData();
            List<DiplomaInstitution> diplomaInstitutions = new ArrayList<>();
            data.forEach(d -> {
                if (d.getInstitutionTypeId() !=null && (d.getInstitutionTypeId() == 1 || d.getInstitutionTypeId() == 2)) {
                    DiplomaInstitution diplomaInstitution = new DiplomaInstitution(d.getId(), d.getNameUz(), d.getNameOz());
                    diplomaInstitutions.add(diplomaInstitution);
                }
            });
            diplomaInstitutionRepository.saveAll(diplomaInstitutions);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Transactional
    public void saveOldInstitution() {
        try {
            InstitutionResponse institutionsOldNames = diplomaApi.getInstitutionsOldNames();
            Institutions institutions = institutionsOldNames.getInstitutionData().getInstitutions();
            List<InstitutionDataItem> data = institutions.getData();
            List<DiplomaInstitution> diplomaInstitutions = new ArrayList<>();
            data.forEach(d -> {
                if (d.getInstitutionTypeId() !=null && (d.getInstitutionTypeId() == 1 || d.getInstitutionTypeId() == 2)) {
                    DiplomaInstitution diplomaInstitution = new DiplomaInstitution(d.getId(), d.getNameUz(), d.getNameOz());
                    diplomaInstitutions.add(diplomaInstitution);
                }
            });
            diplomaInstitutionRepository.saveAll(diplomaInstitutions);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}

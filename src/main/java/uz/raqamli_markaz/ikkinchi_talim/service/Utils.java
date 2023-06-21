package uz.raqamli_markaz.ikkinchi_talim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names.InstitutionOldDataItem;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names.InstitutionOldNames;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names.InstitutionOldNamesResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.InstitutionDataItem;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.InstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.Institutions;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaInstitutionRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaOldInstitutionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Utils {

    private final DiplomaInstitutionRepository diplomaInstitutionRepository;
    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
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
            List<DiplomaInstitution> all = diplomaInstitutionRepository.findAll();
            all.forEach(d -> {

                new Thread(() -> {
                    InstitutionOldNamesResponse institutionOldNamesResponse = diplomaApi.getInstitutionsOldNames();
                    InstitutionOldNames institutionOldNames = institutionOldNamesResponse.getInstitutionOldNamesData().getInstitutionOldNames();
                    List<InstitutionOldDataItem> dataItems = institutionOldNames.getData();
                    dataItems.forEach(odlDiploma -> {
                        if (Objects.equals(odlDiploma.getInstitutionId(), d.getInstitutionId())) {
                            DiplomaOldInstitution diplomaOldInstitution = new DiplomaOldInstitution();
                            diplomaOldInstitution.setInstitutionId(d.getInstitutionId());
                            diplomaOldInstitution.setInstitutionName();
                            diplomaOldInstitution.setInstitutionId(d.getInstitutionId());
                            diplomaOldInstitution.setInstitutionId(d.getInstitutionId());
                        }
                    });
                })
            });




        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}

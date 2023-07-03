package uz.raqamli_markaz.ikkinchi_talim.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.DiplomaApi;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials.DataItemSerials;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials.DiplomaSerialResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials.DiplomaSerials;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names.InstitutionOldDataItem;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names.InstitutionOldNames;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names.InstitutionOldNamesResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.InstitutionDataItem;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.InstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.Institutions;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.specialities.SpecialitiesResponseApi;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.specialities.SpecialityDataItem;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaInstitutionRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaOldInstitutionRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaSerialRepository;
import uz.raqamli_markaz.ikkinchi_talim.repository.DiplomaSpecialityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Utils {

    private final DiplomaInstitutionRepository diplomaInstitutionRepository;
    private final DiplomaOldInstitutionRepository diplomaOldInstitutionRepository;
    private final DiplomaSpecialityRepository diplomaSpecialityRepository;
    private final DiplomaSerialRepository diplomaSerialRepository;
    private final DiplomaApi diplomaApi;

    @Transactional
    public void saveInstitution() {
        try {
            InstitutionResponse institutionResponse = diplomaApi.getInstitutions();
            Institutions institutions = institutionResponse.getInstitutionData().getInstitutions();
            List<InstitutionDataItem> data = institutions.getData();
            List<DiplomaInstitution> diplomaInstitutions = new ArrayList<>();
            data.forEach(d -> {
                if (d.getInstitutionTypeId() != null && (d.getInstitutionTypeId() == 1 || d.getInstitutionTypeId() == 2)) {
                    DiplomaInstitution diplomaInstitution = new DiplomaInstitution();
                    diplomaInstitution.setClassificatorId(d.getId());
                    diplomaInstitution.setInstitutionNameUz(d.getNameUz());
                    diplomaInstitution.setInstitutionNameOz(d.getNameOz());
                    diplomaInstitution.setInstitutionNameRu(d.getNameRu());
                    diplomaInstitution.setInstitutionNameEn(d.getNameEn());
                    diplomaInstitution.setInstitutionTypeId(d.getInstitutionTypeId());
                    diplomaInstitution.setInstitutionTypeName(d.getInstitutionTypeName());
                    diplomaInstitutions.add(diplomaInstitution);
                }
            });
            diplomaInstitutionRepository.saveAll(diplomaInstitutions);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveOldInstitution() {
        try {
            List<DiplomaInstitution> all = diplomaInstitutionRepository.findAll();

            all.forEach(d -> {
                Thread thread = new Thread(() -> {
                    List<DiplomaOldInstitution> diplomaOldInstitutions = new ArrayList<>();
                    InstitutionOldNamesResponse institutionOldNamesResponse = diplomaApi.getInstitutionsOldNames();
                    InstitutionOldNames institutionOldNames = institutionOldNamesResponse.getInstitutionOldNamesData().getInstitutionOldNames();
                    List<InstitutionOldDataItem> dataItems = institutionOldNames.getData();
                    dataItems.forEach(odlDiploma -> {
                        if (Objects.equals(odlDiploma.getInstitutionId(), d.getClassificatorId())) {
                            DiplomaOldInstitution diplomaOldInstitution = new DiplomaOldInstitution();
                            diplomaOldInstitution.setClassificatorId(d.getClassificatorId());
                            diplomaOldInstitution.setInstitutionName(odlDiploma.getInstitutionName());
                            diplomaOldInstitution.setInstitutionOldId(odlDiploma.getId());
                            diplomaOldInstitution.setInstitutionOldNameUz(odlDiploma.getNameUz());
                            diplomaOldInstitution.setInstitutionOldNameOz(odlDiploma.getNameOz());
                            diplomaOldInstitution.setInstitutionOldNameRu(odlDiploma.getNameRu());
                            diplomaOldInstitution.setInstitutionOldNameEn(odlDiploma.getNameEn());
                            diplomaOldInstitutions.add(diplomaOldInstitution);
                        }
                    });
                    diplomaOldInstitutionRepository.saveAll(diplomaOldInstitutions);
                });
                try {
                    thread.join();
                    thread.start();
                } catch (InterruptedException e) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveSpecialities() {
        try {
            SpecialitiesResponseApi specialitiesResponseApi = diplomaApi.getSpecialities();
            List<SpecialityDataItem> dataItems = specialitiesResponseApi.getSpecialityData().getSpecialities().getData();
            List<DiplomaSpeciality> diplomaSpecialities = new ArrayList<>();
            dataItems.forEach(d -> {
                DiplomaSpeciality diplomaSpeciality = new DiplomaSpeciality();
                diplomaSpeciality.setSpecialitiesId(d.getId());
                diplomaSpeciality.setNameUz(d.getNameUz());
                diplomaSpeciality.setNameOz(d.getNameOz());
                diplomaSpeciality.setNameRu(d.getNameRu());
                diplomaSpeciality.setNameEn(d.getNameEn());
                diplomaSpeciality.setInstitutionId(d.getInstitutionId());
                diplomaSpeciality.setStatusId(d.getStatusId());
                diplomaSpecialities.add(diplomaSpeciality);
            });
            diplomaSpecialityRepository.saveAll(diplomaSpecialities);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveDiplomaSerial() {
        try {
            DiplomaSerialResponse response = diplomaApi.getDiplomaSerials();
            DiplomaSerials diplomaSerials = response.getDataSerials().getDiplomaSerials();
            List<DataItemSerials> data = diplomaSerials.getData();
            List<DiplomaSerial> diplomaSerialsListClassificator = new ArrayList<>();
            data.forEach(d -> {
                DiplomaSerial diplomaSerial = new DiplomaSerial();
                diplomaSerial.setSerial(d.getSerial());
                diplomaSerial.setDegreeId(d.getDegreeId());
                diplomaSerial.setBeginYear(d.getBeginYear());
                diplomaSerial.setStatusId(d.getStatusId());
                diplomaSerial.setEndYear(d.getEndYear());
                diplomaSerialsListClassificator.add(diplomaSerial);
            });
            diplomaSerialRepository.saveAll(diplomaSerialsListClassificator);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }

}

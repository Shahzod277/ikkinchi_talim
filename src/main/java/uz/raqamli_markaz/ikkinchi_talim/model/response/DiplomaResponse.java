package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiplomaResponse {
    private Integer id;
    private Integer institutionId;
    private String institutionName;
    private String institutionOldName;
    private String degreeName;
    private Integer eduFormId;
    private String eduFormName;
    private Integer specialityId;
    private String specialityName;
    private String eduFinishingDate;
    private String eduStartingDate;
    private String diplomaGivenDate;
    private Integer diplomaSerialId;
    private String diplomaSerial;
    private Integer diplomaNumber;
    private Integer countryId;
    private String countryName;
    private String specialityCustomName;
    private Integer statusId;
    private String statusName;
    private String diplomaUrl;
    private String ilovaUrl;
    private Boolean isActive;
    private Integer eduDurationId;
    private String eduDurationName;


    public DiplomaResponse(Diploma diploma) {
        this.id = diploma.getId();
        this.countryName = diploma.getCountryName();
        if (diploma.getInstitutionId() != null) {
            this.institutionId = diploma.getInstitutionIdDb();
        }
        if (diploma.getDiplomaGivenDate() != null) {
            this.diplomaGivenDate = diploma.getDiplomaGivenDate();
        }
        if (diploma.getEduStartingDate() != null) {
            this.eduStartingDate = diploma.getEduStartingDate();
        }
        if (diploma.getEduFinishingDate() != null) {
            this.eduFinishingDate = diploma.getEduFinishingDate();
        }
        this.countryId = diploma.getCountryId();
        this.institutionName = diploma.getInstitutionName();
        this.institutionOldName = diploma.getInstitutionOldName();
        this.eduFormId = diploma.getEduFormId();
        this.eduFormName = diploma.getEduFormName();
        this.degreeName = diploma.getDegreeName();
        this.eduDurationId = diploma.getEduDurationId();
        this.eduDurationName = diploma.getEduDurationName();
        if (diploma.getSpecialityIdDb() != null) {
            this.specialityId = diploma.getSpecialityIdDb();
        }
        if (diploma.getSpecialityIdDb() != null) {
            this.specialityName = diploma.getSpecialityName();
        }
        if (diploma.getDiplomaSerialId() != null) {
            this.diplomaSerialId = diploma.getDiplomaSerialId();
        }
        if (diploma.getSpecialityCustomName() != null) {
            this.specialityCustomName = diploma.getSpecialityCustomName();

        }
        this.statusName = diploma.getStatusName();
        this.diplomaSerial = diploma.getDiplomaSerial();
        if (diploma.getDiplomaNumber() != null) {
            this.diplomaNumber = diploma.getDiplomaNumber();

        }
        if (diploma.getDiplomaUrl() != null) {
            this.ilovaUrl = diploma.getIlovaUrl();
            this.diplomaUrl = diploma.getDiplomaUrl();
        }
        this.isActive = diploma.getIsActive();
    }
}

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
    private Integer institutionOldId;
    private String institutionOldName;
    private Integer degreeId;
    private String degreeName;
    private Integer eduFormId;
    private String eduFormName;
    private Integer specialityId;
    private String specialityName;
    private String eduFinishingDate;
    private Integer diplomaSerialId;
    private String diplomaSerialAndNumber;
    private String countryName;
    private String specialityCustomName;
    private Integer statusId;
    private String statusName;
    private String diplomaUrl;
    private String ilovaUrl;

    private Boolean isActive;

    public DiplomaResponse(Diploma diploma) {
        this.id = diploma.getId();
        this.countryName = diploma.getCountryName();
        this.institutionId = diploma.getInstitutionId();
        this.institutionName = diploma.getInstitutionName();
        this.institutionOldName = diploma.getInstitutionOldName();
        this.institutionOldId = diploma.getInstitutionOldId();
        this.eduFormName = diploma.getEduFormName();
        this.degreeId = diploma.getDegreeId();
        this.degreeName = diploma.getDegreeName();
        this.specialityId = diploma.getSpecialityId();
        this.specialityName = diploma.getSpecialityName();
        this.eduFinishingDate = diploma.getEduFinishingDate();
        this.diplomaSerialAndNumber = diploma.getDiplomaSerialAndNumber();
        if (diploma.getDiplomaUrl() != null) {
            this.ilovaUrl = diploma.getIlovaUrl();
            this.diplomaUrl = diploma.getDiplomaUrl();
        }
        this.isActive = diploma.getIsActive();
    }
}

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
public class DiplomResponseAdmin {
    private Integer id;
    private String countryName;
    private Integer institutionId;
    private String institutionName;
    private Integer institutionOldNameId;
    private String institutionOldName;
    private Integer degreeId;
    private String degreeName;
    private Integer eduFormId;
    private String eduFormName;
    private Integer specialityId;
    private String specialityName;
    private String eduFinishingDate;
    private String diplomaStatus;
    private String diplomaNumberAndDiplomaSerial;
    private StoryM storyM;

    public DiplomResponseAdmin(Diploma diploma) {
        this.id = diploma.getId();
        this.countryName = diploma.getCountryName();
        this.institutionId = diploma.getInstitutionId();
        this.institutionName = diploma.getInstitutionName();
        this.institutionOldNameId = diploma.getInstitutionOldId();
        this.institutionOldName = diploma.getInstitutionOldName();
        this.eduFormName = diploma.getEduFormName();
        this.degreeId = diploma.getDegreeId();
        this.degreeName = diploma.getDegreeName();
        this.specialityId = diploma.getSpecialityId();
        this.specialityName = diploma.getSpecialityName();
        this.eduFinishingDate = diploma.getEduFinishingDate();

    }
}

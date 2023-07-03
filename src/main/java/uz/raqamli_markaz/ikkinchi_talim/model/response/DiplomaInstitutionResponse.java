package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiplomaInstitutionResponse {

    private Integer classificatorId; // yangi institutlar id, yani institution id
    private String institutionName;
    private Integer institutionOldId;
    private String institutionOldNameOz;

    public DiplomaInstitutionResponse(DiplomaOldInstitution diplomaOldInstitution) {

        this.classificatorId = diplomaOldInstitution.getClassificatorId();
        this.institutionName = diplomaOldInstitution.getInstitutionName();
        this.institutionOldId = diplomaOldInstitution.getInstitutionOldId();
        this.institutionOldNameOz = diplomaOldInstitution.getInstitutionOldNameOz();
    }
}

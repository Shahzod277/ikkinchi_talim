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

    private Integer id;
    private String institutionName;
    private String institutionOldNameOz;

    public DiplomaInstitutionResponse(DiplomaOldInstitution diplomaOldInstitution) {
        this.id = diplomaOldInstitution.getId();
        this.institutionName = diplomaOldInstitution.getInstitutionName();
        this.institutionOldNameOz = diplomaOldInstitution.getInstitutionOldNameOz();
    }
}

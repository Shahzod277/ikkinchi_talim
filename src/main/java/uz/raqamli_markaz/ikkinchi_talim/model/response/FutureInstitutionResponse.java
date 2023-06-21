package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FutureInstitutionResponse {

    private Integer id;
    private String name;

    public FutureInstitutionResponse(DiplomaInstitution diplomaInstitution) {
        this.id = diplomaInstitution.getId();
        this.name = diplomaInstitution.getName();
    }
}

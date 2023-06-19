package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.FutureInstitution;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FutureInstitutionResponse {

    private Integer id;
    private String name;

    public FutureInstitutionResponse(FutureInstitution futureInstitution) {
        this.id = futureInstitution.getId();
        this.name = futureInstitution.getName();
    }
}

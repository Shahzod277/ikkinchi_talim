package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Direction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectionResponse {

    private Integer id;
    private String name;
    private Integer futureInstitutionId;
    private String futureInstitutionName;

    public DirectionResponse(Direction direction) {
        this.id = direction.getId();
        this.name = direction.getName();
        this.futureInstitutionId = direction.getDiplomaInstitution().getId();
        this.futureInstitutionName = direction.getDiplomaInstitution().getName();
    }
}

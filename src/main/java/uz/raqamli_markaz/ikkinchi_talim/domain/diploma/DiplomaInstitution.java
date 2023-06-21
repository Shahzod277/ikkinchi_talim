package uz.raqamli_markaz.ikkinchi_talim.domain.diploma;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.AbstractEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiplomaInstitution extends AbstractEntity {

    private Integer classificatorId;
    private String institutionNameUz;
    private String institutionNameOz;
    private String institutionNameRu;
    private String institutionNameEn;
    private Integer institutionTypeId;
    private String institutionTypeName;
}

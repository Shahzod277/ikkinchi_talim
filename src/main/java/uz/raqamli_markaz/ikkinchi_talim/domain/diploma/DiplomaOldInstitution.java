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
public class DiplomaOldInstitution extends AbstractEntity {

    private Integer classificatorId; // yangi institutlar id, yani institution id
    private String institutionName;
    private Integer institutionOldId;
    private String institutionOldNameUz;
    private String institutionOldNameOz;
    private String institutionOldNameRu;
    private String institutionOldNameEn;
}

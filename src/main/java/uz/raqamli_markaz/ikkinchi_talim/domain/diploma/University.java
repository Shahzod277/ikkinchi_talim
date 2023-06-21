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
public class University extends AbstractEntity {

    private Integer institutionId;
    private String institutionName;
    private Integer institutionTypeId;
    private String institutionTypeName;
    private String statusName;
    private String nameUz;
    private String nameOz;
    private String nameEn;
    private String nameRu;
    private Integer regionSoatoId;
    private String regionName;

}

package uz.raqamli_markaz.ikkinchi_talim.domain.diploma;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.raqamli_markaz.ikkinchi_talim.domain.AbstractEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EduForm extends AbstractEntity {

    private String nameOz;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Integer statusId;
    private Integer degreeId;
    private String degreeName;
    private Integer code;

}

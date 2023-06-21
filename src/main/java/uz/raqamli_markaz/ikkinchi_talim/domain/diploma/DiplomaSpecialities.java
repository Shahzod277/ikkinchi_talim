package uz.raqamli_markaz.ikkinchi_talim.domain.diploma;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
public class DiplomaSpecialities extends AbstractEntity {

    private Integer specialitiesId;
    private Integer institutionId;
    private Integer statusId;
    private Integer creatorId;
    private String nameUz;
    private String nameOz;
    private String nameRu;
    private String nameEn;
    private Integer beginYear;

    @ManyToOne(fetch = FetchType.LAZY)
    private University university;
}

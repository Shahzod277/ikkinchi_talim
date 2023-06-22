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
public class Language extends AbstractEntity {

    private String name;
    private Integer kvotaSoni;

    @ManyToOne(fetch = FetchType.LAZY)
    private EduForm eduForm;
}

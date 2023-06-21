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

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Direction direction;
}

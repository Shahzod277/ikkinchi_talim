package uz.raqamli_markaz.ikkinchi_talim.domain.classificator;

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
public class Direction extends AbstractEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private FutureInstitution futureInstitution;
}

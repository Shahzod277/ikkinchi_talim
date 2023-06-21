package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminEntity extends AbstractEntity {

    private String fistName;
    private String lastname;
    @OneToOne(fetch = FetchType.LAZY)
    private DiplomaInstitution diplomaInstitution;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<University> universities = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}

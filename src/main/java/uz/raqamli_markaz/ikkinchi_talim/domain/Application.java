package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.EduForm;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Language;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application extends AbstractEntity {

    private String status;
    private Boolean diplomaStatus;
    private String message;
    private String diplomaMessage;

    @OneToOne(fetch = FetchType.LAZY)
    private Language language;

    @OneToOne(fetch = FetchType.LAZY)
    private EduForm eduForm;

    @ManyToOne(fetch = FetchType.LAZY)
    private DiplomaInstitution diplomaInstitution;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}

package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kvota extends AbstractEntity {

    private String universityCode;
    private String universityName;
    private String specialityName;
    private String specialityCode;
    private String eduFormName;
    private String eduFormCode;
    private String languageName;
    private String languageCode;
    private Integer count;
}

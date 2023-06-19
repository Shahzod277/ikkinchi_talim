package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedEduInfo extends AbstractEntity {

    private Integer institutionCountryId;
    private String institutionName;
    private String institutionOldName;
    private String specialityName;
    private String specialityOldName;
    private String eduFormName;
    private String diplomaSerialAndNumber;
    private String eduFinishingDate;
    //Active diploma
    private boolean actualDiploma = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private EnrolleeInfo enrolleeInfo;

}

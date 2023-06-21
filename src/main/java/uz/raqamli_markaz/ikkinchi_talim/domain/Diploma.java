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
public class Diploma extends AbstractEntity {

    private Integer id;
    private Integer institutionId;
    private String institutionName;
    private Integer institutionOldId;
    private String institutionOldName;
    private Integer degreeId;
    private String degreeName;
    private Integer eduFormId;
    private String eduFormName;
    private Integer specialityId;
    private String specialityName;
    private String eduFinishingDate;
    private Integer diplomaSerialId;
    private String diplomaSerialAndNumber;
    private String countryName;
    private Boolean isActive = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

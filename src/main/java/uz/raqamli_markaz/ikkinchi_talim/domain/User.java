package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Role;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = @Index(columnList = "pinfl"))
public class User extends AbstractEntity {

    private String password;
    private String citizenship;
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String passportSerial;
    private String passportNumber;
    private String passportGivenDate;
    private String pinfl;
    private String phoneNumber;
    private String permanentAddress;
    private Integer myEduId;
    private String fotoUrl;

    private Integer diplomaInstitutionId;
    private String universityCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Application application;
}

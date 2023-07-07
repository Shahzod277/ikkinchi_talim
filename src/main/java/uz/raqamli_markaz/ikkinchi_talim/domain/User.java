package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Role;

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
    private Integer passportNumber;
    private String passportGivenDate;
    private String pinfl;
    private String phoneNumber;
    private String permanentAddress;
    private Integer myEduId;
    private String fotoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Application application;
}

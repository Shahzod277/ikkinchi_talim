package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrolleeInfo extends AbstractEntity {

    private String citizenship;
    private String firstname;
    private String middleName;
    private String lastname;
    private String dateOfBirth;
    private String gender;
    private String passportSerialAndNumber;
    private String passportGivenDate;
    private String pinfl;
    private String phoneNumber;
    private String nationality;
    private String permanentRegion;
    private String permanentDistrict;
    private String permanentAddress;
    private String photo;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}

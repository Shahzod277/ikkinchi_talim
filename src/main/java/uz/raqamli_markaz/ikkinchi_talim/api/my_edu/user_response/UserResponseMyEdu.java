package uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserResponseMyEdu {

    @JsonProperty("pinfl")
    private String pinfl;

    @JsonIgnore
    @JsonProperty("birth_certificate")
    private BirthCertificate birthCertificate;

    @JsonProperty("address")
    private String address;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("birth_date")
    private String birthDate;

    @JsonProperty("citizenship")
    private Citizenship citizenship;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("photo")
    private Photo photo;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("passport")
    private Passport passport;
    @JsonIgnore
    @JsonProperty("nationality")
    private Nationality nationality;
    @JsonIgnore
    @JsonProperty("district")
    private District district;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("first_name")
    private String firstName;
}
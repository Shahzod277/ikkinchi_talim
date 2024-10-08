package uz.raqamli_markaz.ikkinchi_talim.api.iib_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Data {

    private String pinfl;
    private String gender;
    private String photo;
    private Citizenship citizenship;
    private Nationality nationality;
    @JsonProperty("birth_date")
    private String birthDate;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("passport_serial")
    private String passportSerial;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("permanent_address")
    private String permanentAddress;
    @JsonProperty("permanent_district")
    private PermanentDistrict permanentDistrict;
    @JsonProperty("passport_expire_date")
    private String passportExpireDate;
    @JsonProperty("passport_number")
    private String passportNumber;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("passport_given_date")
    private String passportGivenDate;
}

package uz.raqamli_markaz.ikkinchi_talim.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response.*;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("pinfl")
    private String pinfl;

    @JsonProperty("address")
    private String address;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("birth_date")
    private String birthDate;

    @JsonProperty("citizenship")
    private String citizenship;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String fullName;

    public UserResponse(User user) {

        this.id = user.getId();
        this.pinfl = user.getPinfl();
        this.fullName = user.getFullName();
        this.citizenship = user.getCitizenship();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getDateOfBirth();
        this.gender = user.getGender();
        this.address = user.getPermanentAddress();
        this.photo = user.getFotoUrl();
    }
}

package uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Passport {

    @JsonProperty("pinfl")
    private String seria;
    @JsonProperty("serial")
    private String serial;
    @JsonProperty("number")
    private String number;
}
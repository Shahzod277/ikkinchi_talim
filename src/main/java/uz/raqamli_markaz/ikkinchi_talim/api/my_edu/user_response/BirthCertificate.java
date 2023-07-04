package uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter

public class BirthCertificate{

	@JsonProperty("number")
	private String number;

	@JsonProperty("serial")
	private String serial;
}
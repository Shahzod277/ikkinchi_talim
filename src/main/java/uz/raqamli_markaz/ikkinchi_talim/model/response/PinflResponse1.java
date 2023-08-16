package uz.raqamli_markaz.ikkinchi_talim.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PinflResponse1{

	@JsonProperty("pinfl")
	private String pinfl;

	@JsonProperty("passport_number")
	private String passportNumber;

	@JsonProperty("passport_serial")
	private String passportSerial;

}
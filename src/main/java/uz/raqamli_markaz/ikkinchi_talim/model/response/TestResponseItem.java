package uz.raqamli_markaz.ikkinchi_talim.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TestResponseItem{

	@JsonProperty("pinfl")
	private String pinfl;

	@JsonProperty("passport_number")
	private String passportNumber;

	@JsonProperty("passport_serial")
	private String passportSerial;
}
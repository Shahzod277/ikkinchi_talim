package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Citizen{

	@JsonProperty("pinfl")
	private Long pinfl;

	@JsonProperty("passport_number")
	private Integer passportNumber;

	@JsonProperty("passport_serial")
	private String passportSerial;
	@JsonProperty("given_date")
	private String givenDate;
}
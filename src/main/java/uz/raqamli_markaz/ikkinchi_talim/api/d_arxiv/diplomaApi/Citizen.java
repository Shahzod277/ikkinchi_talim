package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Citizen{

	@JsonProperty("pinfl")
	private Long pinfl;

	@JsonProperty("passport_number")
	private Integer passportNumber;

	@JsonProperty("passport_serial")
	private String passportSerial;

	@JsonProperty("given_date")
	private String givenDate;

//	public Citizen(User user) {
//		this.pinfl = Long.parseLong(user.getPinfl());
////		this.passportNumber = user.getPassportNumber();
//		this.passportSerial = user.getPassportSerial();
//		this.givenDate = user.getPassportGivenDate();
//	}
}
package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiplomaSerialResponse {

	@JsonProperty("data")
	private DataSerials dataSerials;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;
}
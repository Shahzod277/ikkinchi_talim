package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstitutionResponse {

	@JsonProperty("data")
	private InstitutionData institutionData;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;
}
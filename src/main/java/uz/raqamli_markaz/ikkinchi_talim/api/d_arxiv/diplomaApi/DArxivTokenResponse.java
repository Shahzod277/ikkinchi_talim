package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DArxivTokenResponse{

	@JsonProperty("data")
	private DataToken dataToken;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;
}
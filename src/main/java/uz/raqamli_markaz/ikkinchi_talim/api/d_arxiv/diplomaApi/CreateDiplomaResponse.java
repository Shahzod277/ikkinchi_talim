package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateDiplomaResponse{

	@JsonProperty("data")
	private DataCreateDiplomaResponse dataCreateDiplomaResponse;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;

}
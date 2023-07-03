package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class DArxivTokenResponse{

	@JsonProperty("data")
	private DataToken data;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;
}
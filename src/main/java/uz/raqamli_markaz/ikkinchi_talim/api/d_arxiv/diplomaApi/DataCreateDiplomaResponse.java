package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DataCreateDiplomaResponse {

	@JsonProperty("diploma")
	private DiplomaResponseApi diplomaResponseApi;

	@JsonProperty("errors")
	private List<Object> errors;
}
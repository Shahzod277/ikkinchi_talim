package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DataCreateDiplomaResponse {

	@JsonProperty("diploma")
	private DiplomaResponseApi diplomaResponseApi;

	@JsonIgnore
	@JsonProperty("errors")
	private List<Object> errors;
}
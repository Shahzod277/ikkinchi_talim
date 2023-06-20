package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDiplomaRequest{

	@JsonProperty("Diploma")
	private DiplomaRequest diplomaRequest;

	@JsonProperty("Citizen")
	private Citizen citizen;
}
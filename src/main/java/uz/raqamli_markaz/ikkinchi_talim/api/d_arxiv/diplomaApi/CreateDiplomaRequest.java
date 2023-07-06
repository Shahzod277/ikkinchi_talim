package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiplomaRequest{

	@JsonProperty("Diploma")
	private DiplomaRequestApi diplomaRequestApi;

	@JsonProperty("Citizen")
	private Citizen citizen;

}
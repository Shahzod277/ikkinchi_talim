package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstitutionData {

	@JsonProperty("institutions")
	private Institutions institutions;

	@JsonProperty("institution_types")
	private InstitutionTypes institutionTypes;
}
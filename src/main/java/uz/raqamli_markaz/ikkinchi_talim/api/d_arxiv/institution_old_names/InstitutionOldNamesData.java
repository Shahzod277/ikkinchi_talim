package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstitutionOldNamesData {

	@JsonProperty("institution_old_names")
	private InstitutionOldNames institutionOldNames;

	@JsonProperty("institution_types")
	private InstitutionTypes institutionTypes;
}
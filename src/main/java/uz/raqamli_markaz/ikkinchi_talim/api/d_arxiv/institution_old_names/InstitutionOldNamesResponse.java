package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstitutionOldNamesResponse{

	@JsonProperty("data")
	private InstitutionOldNamesData institutionOldNamesData;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;
}
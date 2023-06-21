package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.specialities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SpecialitiesResponse{

	@JsonProperty("data")
	private SpecialityData specialityData;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;
}
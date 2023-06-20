package uz.raqamli_markaz.ikkinchi_talim.api.hemis_university_speciality_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HemisUniversityResponse {

	@JsonProperty("code")
	private Integer code;

	@JsonProperty("data")
	private List<DataItemUniversity> data;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("error")
	private Object error;
}
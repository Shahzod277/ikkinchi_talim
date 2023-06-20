package uz.raqamli_markaz.ikkinchi_talim.api.hemis_university_speciality_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HemisSpecialityResponse {

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("count")
	private Integer count;

	@JsonProperty("type")
	private String type;

	@JsonProperty("data")
	private List<DataItemSpeciality> data;

}
package uz.raqamli_markaz.ikkinchi_talim.api.hemis_university_speciality_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DataItemSpeciality {

	@JsonProperty("universityName")
	private String universityName;

	@JsonProperty("specialityCode")
	private String specialityCode;

	@JsonProperty("specialityName")
	private String specialityName;

	@JsonProperty("id")
	private String id;

	@JsonProperty("facultyName")
	private String facultyName;

	@JsonProperty("universityCode")
	private String universityCode;

	@JsonProperty("facultyCode")
	private String facultyCode;
}
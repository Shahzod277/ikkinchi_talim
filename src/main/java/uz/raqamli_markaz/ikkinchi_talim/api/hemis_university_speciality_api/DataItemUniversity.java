package uz.raqamli_markaz.ikkinchi_talim.api.hemis_university_speciality_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataItemUniversity {

	@JsonProperty("student_url")
	private String studentUrl;

	@JsonProperty("code")
	private String code;

	@JsonProperty("api_url")
	private String apiUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("employee_url")
	private String employeeUrl;
}
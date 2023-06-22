package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DiplomaRequest{

	@JsonProperty("diploma_serial_id")
	private Integer diplomaSerialId;

	@JsonProperty("diploma_given_date")
	private String diplomaGivenDate;

	@JsonProperty("diploma_type_id")
	private Integer diplomaTypeId;

	@JsonProperty("speciality_id")
	private Integer specialityId;

	@JsonProperty("edu_starting_date")
	private String eduStartingDate;

	@JsonProperty("diploma_number")
	private Integer diplomaNumber;

	@JsonProperty("edu_form_id")
	private Integer eduFormId;

	@JsonProperty("institution_id")
	private Integer institutionId;

	@JsonProperty("degree_id")
	private Integer degreeId;

	@JsonProperty("institution_old_name_id")
	private Integer institutionOldNameId;

	@JsonProperty("speciality_code")
	private String specialityCode;

	@JsonProperty("edu_finishing_date")
	private String eduFinishingDate;

	@JsonProperty("edu_duration_id")
	private Integer eduDurationId;
	@JsonProperty("speciality_custom_name")
	private String speciality_custom_name;
}
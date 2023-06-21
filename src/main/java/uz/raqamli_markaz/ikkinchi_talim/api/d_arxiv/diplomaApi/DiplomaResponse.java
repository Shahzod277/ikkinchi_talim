package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DiplomaResponse {

	@JsonProperty("pinfl")
	private Long pinfl;

	@JsonProperty("diploma_serial_id")
	private Integer diplomaSerialId;

	@JsonProperty("speciality_id")
	private Integer specialityId;

	@JsonProperty("edu_starting_date")
	private String eduStartingDate;

	@JsonProperty("institution_name")
	private String institutionName;

	@JsonProperty("edu_form_id")
	private Integer eduFormId;

	@JsonProperty("institution_id")
	private Integer institutionId;

	@JsonProperty("edu_type_id")
	private Integer eduTypeId;

	@JsonProperty("status_id")
	private Integer statusId;

	@JsonProperty("degree_id")
	private Integer degreeId;

	@JsonProperty("speciality_old_id")
	private Integer specialityOldId;

	@JsonProperty("speciality_code")
	private String specialityCode;

	@JsonProperty("edu_finishing_date")
	private String eduFinishingDate;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("institution_type_name")
	private String institutionTypeName;

	@JsonProperty("edu_duration_id")
	private Integer eduDurationId;

	@JsonProperty("diploma_type_name")
	private String diplomaTypeName;

	@JsonProperty("speciality_name")
	private String specialityName;

	@JsonProperty("diploma_serial")
	private String diplomaSerial;

	@JsonProperty("institution_type_id")
	private Integer institutionTypeId;

	@JsonProperty("diploma_given_date")
	private String diplomaGivenDate;

	@JsonProperty("degree_name")
	private String degreeName;

	@JsonProperty("edu_form_name")
	private String eduFormName;

	@JsonProperty("diploma_type_id")
	private Integer diplomaTypeId;

	@JsonProperty("status_name")
	private String statusName;

	@JsonProperty("edu_type_name")
	private String eduTypeName;

	@JsonProperty("diploma_number")
	private Integer diplomaNumber;

	@JsonProperty("institution_old_name")
	private String institutionOldName;

	@JsonProperty("institution_old_name_id")
	private Integer institutionOldNameId;

	@JsonProperty("edu_duration_name")
	private String eduDurationName;
}
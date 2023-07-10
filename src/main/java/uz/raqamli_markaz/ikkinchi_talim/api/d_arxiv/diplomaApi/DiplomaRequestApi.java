package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiplomaRequestApi {

	private Integer id;

	@JsonProperty("diploma_serial_id")
	private Integer diplomaSerialId;

	@JsonProperty("diploma_given_date")
	private String diplomaGivenDate;

	@JsonProperty("diploma_type_id")
	private Integer diplomaTypeId = 0;

	@JsonProperty("speciality_id")
	private Integer specialityId;

	@JsonProperty("edu_starting_date")
	private String eduStartingDate;

	@JsonProperty("diploma_number")
	private Integer diplomaNumber;

	@JsonProperty("edu_form_id")
	private Integer eduFormId ;

	@JsonProperty("institution_id")
	private Integer institutionId;

	@JsonProperty("degree_id")
	private Integer degreeId=2;

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

	@JsonProperty("diploma_url")
	private String diplomaUrl;

	@JsonProperty("ilova_url")
	private String ilovaUrl;

	public DiplomaRequestApi(Diploma diploma) {

		this.institutionId = diploma.getInstitutionId();
		this.institutionOldNameId = diploma.getInstitutionOldId();
		this.eduFormId = diploma.getEduFormId();
		this.specialityId = diploma.getSpecialityId();
		this.specialityCode = diploma.getSpecialityCode();
		this.eduDurationId = diploma.getEduDurationId();
		this.eduStartingDate = diploma.getEduStartingDate();
		this.eduFinishingDate = diploma.getEduFinishingDate();
		this.diplomaGivenDate = diploma.getDiplomaGivenDate();
		this.diplomaSerialId = diploma.getDiplomaSerialId();
		this.diplomaNumber = diploma.getDiplomaNumber();
	}
}
package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstitutionOldDataItem {

	@JsonProperty("status_name")
	private String statusName;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("institution_name")
	private String institutionName;

	@JsonProperty("institution_id")
	private Integer institutionId;

	@JsonProperty("status_id")
	private Integer statusId;

	@JsonProperty("name_ru")
	private String nameRu;

	@JsonProperty("region_soato_id")
	private Integer regionSoatoId;

	@JsonProperty("region_name")
	private String regionName;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name_uz")
	private String nameUz;

	@JsonProperty("name_oz")
	private String nameOz;

	@JsonProperty("end_year")
	private Integer endYear;

	@JsonProperty("name_en")
	private String nameEn;

	@JsonProperty("begin_year")
	private Integer beginYear;

	@JsonProperty("edu_type_id")
	private Integer eduTypeId;

	@JsonProperty("code")
	private String code;

	@JsonProperty("edu_type_name")
	private String eduTypeName;
}
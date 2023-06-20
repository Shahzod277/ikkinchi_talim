package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstitutionDataItem {

	@JsonProperty("code")
	private String code;

	@JsonProperty("institution_type_id")
	private Integer institutionTypeId;

	@JsonProperty("status_name")
	private String statusName;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("status_id")
	private Integer statusId;

	@JsonProperty("name_ru")
	private String nameRu;

	@JsonProperty("termination_date")
	private Object terminationDate;

	@JsonProperty("region_soato_id")
	private Integer regionSoatoId;

	@JsonProperty("region_name")
	private String regionName;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name_uz")
	private String nameUz;

	@JsonProperty("institution_type_name")
	private String institutionTypeName;

	@JsonProperty("name_oz")
	private String nameOz;

	@JsonProperty("opening_date")
	private String openingDate;

	@JsonProperty("name_en")
	private String nameEn;

	@JsonProperty("edu_type_id")
	private Integer eduTypeId;

	@JsonProperty("edu_type_name")
	private String eduTypeName;
}
package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.specialities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialityDataItem {

	@JsonProperty("status_id")
	private Integer statusId;

	@JsonProperty("code")
	private Object code;

	@JsonProperty("name_ru")
	private Object nameRu;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name_uz")
	private String nameUz;

	@JsonProperty("name_oz")
	private String nameOz;

	@JsonProperty("termination_year")
	private Integer terminationYear;

	@JsonProperty("institution_id")
	private Integer institutionId;

	@JsonProperty("name_en")
	private Object nameEn;

	@JsonProperty("begin_year")
	private Integer beginYear;
}
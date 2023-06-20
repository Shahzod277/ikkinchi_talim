package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.formEdu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FormEduDataItem {

	@JsonProperty("status_id")
	private Integer statusId;

	@JsonProperty("degree_id")
	private Integer degreeId;

	@JsonProperty("name_ru")
	private String nameRu;

	@JsonProperty("min_duration")
	private Integer minDuration;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name_uz")
	private String nameUz;

	@JsonProperty("name_oz")
	private String nameOz;

	@JsonProperty("max_duration")
	private Integer maxDuration;

	@JsonProperty("name_en")
	private String nameEn;
}
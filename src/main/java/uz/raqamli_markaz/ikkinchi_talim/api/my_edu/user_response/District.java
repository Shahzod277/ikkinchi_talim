package uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class District{

	@JsonProperty("sequence")
	private Integer sequence;

	@JsonProperty("code")
	private String code;

	@JsonProperty("name_ru")
	private String nameRu;

	@JsonProperty("id")
	private String id;

	@JsonProperty("name_uz")
	private String nameUz;

	@JsonProperty("region")
	private Region region;

	@JsonProperty("name_en")
	private String nameEn;
}
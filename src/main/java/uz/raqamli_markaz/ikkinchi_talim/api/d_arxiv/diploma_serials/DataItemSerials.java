package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataItemSerials {

	@JsonProperty("status_id")
	private Integer statusId;

	@JsonProperty("degree_id")
	private Integer degreeId;

	@JsonProperty("serial")
	private String serial;

	@JsonProperty("creator_id")
	private Integer creatorId;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("end_year")
	private Integer endYear;

	@JsonProperty("begin_year")
	private Integer beginYear;
}
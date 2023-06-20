package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DiplomaSerials{

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("data")
	private List<DataItemSerials> data;
}
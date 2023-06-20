package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.specialities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Specialities{

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("data")
	private List<SpecialityDataItem> data;
}
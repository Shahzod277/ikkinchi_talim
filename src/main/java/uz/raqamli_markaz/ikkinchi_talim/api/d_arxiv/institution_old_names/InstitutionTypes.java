package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstitutionTypes{

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("data")
	private List<InstitutionOldDataItem> data;
}
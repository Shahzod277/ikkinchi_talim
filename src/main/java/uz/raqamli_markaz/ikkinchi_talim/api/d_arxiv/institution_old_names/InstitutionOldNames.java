package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstitutionOldNames{

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("data")
	private List<InstitutionOldDataItem> data;
}
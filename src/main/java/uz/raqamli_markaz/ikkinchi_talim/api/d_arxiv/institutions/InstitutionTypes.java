package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InstitutionTypes{

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("data")
	private List<InstitutionDataItem> data;
}
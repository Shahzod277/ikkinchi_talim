package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.formEdu;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EduFormApi {

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("data")
	private List<FormEduDataItem> data;
}
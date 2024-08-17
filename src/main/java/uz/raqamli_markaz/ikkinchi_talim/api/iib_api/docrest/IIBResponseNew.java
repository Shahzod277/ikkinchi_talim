package uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IIBResponseNew {

	@JsonProperty("result")
	private String result;

	@JsonProperty("data")
	private List<DataItem> data;

	@JsonProperty("comments")
	private String comments;
}
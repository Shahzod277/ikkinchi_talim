package uz.raqamli_markaz.ikkinchi_talim.model.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TestResponse{

	@JsonProperty("data")
	private List<TestResponseItem> testResponse;
}
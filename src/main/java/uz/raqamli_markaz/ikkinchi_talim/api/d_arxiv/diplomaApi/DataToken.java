package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.NewToken;

@Getter
public class DataToken {

	@JsonProperty("new_token")
	private NewToken newToken;

}
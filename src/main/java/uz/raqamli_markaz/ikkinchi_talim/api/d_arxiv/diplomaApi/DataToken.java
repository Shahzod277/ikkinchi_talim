package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.NewToken;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataToken {

	@JsonProperty("new_token")
	private NewToken newToken;

}
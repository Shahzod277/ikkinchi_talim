package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DataSerials {

	@JsonProperty("diploma_serials")
	private DiplomaSerials diplomaSerials;
}
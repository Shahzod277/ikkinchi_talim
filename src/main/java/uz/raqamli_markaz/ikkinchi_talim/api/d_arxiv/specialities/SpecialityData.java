package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.specialities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SpecialityData {

	@JsonProperty("specialities")
	private Specialities specialities;
}
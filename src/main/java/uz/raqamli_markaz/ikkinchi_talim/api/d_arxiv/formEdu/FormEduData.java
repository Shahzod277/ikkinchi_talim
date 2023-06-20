package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.formEdu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FormEduData {

	@JsonProperty("specialities")
	private Specialities specialities;
}
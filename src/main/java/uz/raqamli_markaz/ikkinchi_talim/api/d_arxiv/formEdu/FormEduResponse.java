package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.formEdu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FormEduResponse{

	@JsonProperty("data")
	private FormEduData formEduData;

	@JsonProperty("success")
	private Boolean success;

	@JsonProperty("message")
	private String message;
}
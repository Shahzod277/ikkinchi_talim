package uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentsItem{

	@JsonProperty("docgiveplace")
	private String docgiveplace;
	@JsonProperty("datebegin")
	private String datebegin;

	@JsonProperty("document")
	private String document;

	@JsonProperty("dateend")
	private String dateend;

	@JsonProperty("type")
	private String type;

	@JsonProperty("docgiveplaceid")
	private int docgiveplaceid;

	@JsonProperty("status")
	private int status;

}
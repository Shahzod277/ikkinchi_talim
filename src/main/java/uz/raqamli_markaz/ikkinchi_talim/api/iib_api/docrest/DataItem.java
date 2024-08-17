package uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataItem{

	@JsonProperty("transaction_id")
	private int transactionId;

	@JsonProperty("engname")
	private String engname;

	@JsonProperty("birthcountry")
	private String birthcountry;

	@JsonProperty("pinpps")
	private List<String> pinpps;

	@JsonProperty("documents")
	private List<DocumentsItem> documents;

	@JsonProperty("birthcountryid")
	private int birthcountryid;

	@JsonProperty("livestatus")
	private int livestatus;

	@JsonProperty("birth_date")
	private String birthDate;

	@JsonProperty("nationalityid")
	private int nationalityid;

	@JsonProperty("citizenship")
	private String citizenship;

	@JsonProperty("sex")
	private int sex;

	@JsonProperty("surnamelat")
	private String surnamelat;

	@JsonProperty("photo")
	private String photo;

	@JsonProperty("surnamecyr")
	private String surnamecyr;

	@JsonProperty("citizenshipid")
	private int citizenshipid;

	@JsonProperty("current_document")
	private String currentDocument;

	@JsonProperty("patronymlat")
	private String patronymlat;

	@JsonProperty("namecyr")
	private String namecyr;

	@JsonProperty("birthplace")
	private String birthplace;

	@JsonProperty("nationality")
	private String nationality;

	@JsonProperty("patronymcyr")
	private String patronymcyr;

	@JsonProperty("namelat")
	private String namelat;

	@JsonProperty("engsurname")
	private String engsurname;

	@JsonProperty("current_pinpp")
	private String currentPinpp;
}
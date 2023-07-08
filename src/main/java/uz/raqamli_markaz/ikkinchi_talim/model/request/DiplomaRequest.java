package uz.raqamli_markaz.ikkinchi_talim.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.DiplomaRequestApi;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiplomaRequest {
    @JsonProperty("country_id")
    private Integer countryId; // Umumiy o'zgaruvchi

    @JsonProperty("diploma_serial")
    private String diplomaSerial;


    @JsonProperty("diploma_number")
    private Integer diplomaNumber;

    @JsonProperty("diploma_given_date")
    private String diplomaGivenDate;

    @JsonProperty("edu_starting_date")
    private String eduStartingDate;

    @JsonProperty("degree_name")
    private String degreeName="Bakalavr";

    @JsonProperty("edu_finishing_date")
    private String eduFinishingDate;

    @JsonProperty("speciality_custom_name")
    private String speciality_custom_name; // Umumiy o'zgaruvchi

    @JsonProperty("diploma_url")
    private String diplomaUrl;

    @JsonProperty("ilova_url")
    private String ilovaUrl;
    @JsonProperty("diploma_serial_id")
    private Integer diplomaSerialId;

    @JsonProperty("speciality_id")
    private Integer specialityId;

    //talim shakli kunduzgi
    @JsonProperty("edu_form_id")
    private Integer eduFormId;

    @JsonProperty("edu_form_name")
    private String eduFormName;

    @JsonProperty("institution_id")
    private Integer institutionId;
    @JsonProperty("foreign_otm_name")
    private String foreignOtmName;


    @JsonProperty("edu_duration_id")
    private Integer eduDurationId;

//    private DiplomaRequestApi diplomaRequestApi; //D_ARXIV Api uchun
}

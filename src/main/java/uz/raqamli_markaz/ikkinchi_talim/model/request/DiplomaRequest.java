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

    private Integer id;

    @JsonProperty("country_id")
    private Integer countryId; // Umumiy o'zgaruvchi

    @JsonProperty("diploma_serial")
    private String diplomaSerial;

    @JsonProperty("foreign_institution_name")
    private String foreignInstitutionName;

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

    private DiplomaRequestApi diplomaRequestApi; //D_ARXIV Api uchun
}

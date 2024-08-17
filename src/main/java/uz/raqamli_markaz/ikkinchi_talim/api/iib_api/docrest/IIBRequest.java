package uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IIBRequest {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String transaction_id = "3";

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String is_consent = "Y";

    //misol: 1-rus tili, 2-o'zbek kirill, 3-o'zbek lotin
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer langId = 3;

    //misol: "AD*******"
    private String document;

    //misol: "1996-03-31"
    private String birth_date;

    //pinfl yoki birthdate parametrlari bilan ishlatiladi. Y ha, N yoq
    private String is_photo = "Y";

    private String pinpp;

}

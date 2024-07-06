package uz.raqamli_markaz.ikkinchi_talim.api.my_edu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppRequestMyEdu {

    @JsonProperty("data")
    private Object data;

    @JsonProperty("service")
    private String service = "8c04bc53-78cf-4c82-b75f-34b5f72a0d5d";

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("status")
    private String status;


}
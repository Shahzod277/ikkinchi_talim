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
    private String service = "18ffa877-987f-4d3d-871f-03cb7e0f4638";

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("status")
    private String status;


}
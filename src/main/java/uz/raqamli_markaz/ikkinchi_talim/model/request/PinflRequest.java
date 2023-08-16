package uz.raqamli_markaz.ikkinchi_talim.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PinflRequest {
    @JsonProperty("pinfls")
    List<String> pinfls = new ArrayList<>();
}

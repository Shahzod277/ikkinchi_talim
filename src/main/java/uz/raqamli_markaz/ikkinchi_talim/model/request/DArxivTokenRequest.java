package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DArxivTokenRequest {

    private String action = "GetNewToken";
    private Credits credits = new Credits();
}

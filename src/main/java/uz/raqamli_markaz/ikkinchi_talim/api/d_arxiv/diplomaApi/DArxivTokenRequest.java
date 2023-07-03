package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DArxivTokenRequest {

    private String action = "GetNewToken";
    private Credits credits = new Credits();
}

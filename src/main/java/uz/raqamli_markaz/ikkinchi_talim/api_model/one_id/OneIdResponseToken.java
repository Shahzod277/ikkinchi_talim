package uz.raqamli_markaz.ikkinchi_talim.api_model.one_id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OneIdResponseToken {

    private String token_type;
    private long expires_in;
    private String access_token;
    private String refresh_token;
    private String scope;
}

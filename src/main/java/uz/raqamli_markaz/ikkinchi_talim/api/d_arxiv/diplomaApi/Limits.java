package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Limits{

    @JsonProperty("max_active_tokens")
    private Integer maxActiveTokens;

    @JsonProperty("current_active_tokens")
    private Integer currentActiveTokens;

    @JsonProperty("tokens_remaining")
    private Integer tokensRemaining;
}
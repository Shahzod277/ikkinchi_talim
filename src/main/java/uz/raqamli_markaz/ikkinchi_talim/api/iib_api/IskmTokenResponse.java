package uz.raqamli_markaz.ikkinchi_talim.api.iib_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.TokenEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IskmTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private Integer expiresIn;
	public IskmTokenResponse(TokenEntity tokenEntity) {
		this.accessToken = tokenEntity.getToken();
	}
}
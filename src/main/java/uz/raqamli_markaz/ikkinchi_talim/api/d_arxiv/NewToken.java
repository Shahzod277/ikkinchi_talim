package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewToken{

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("requests_limit_per_day")
	private Integer requestsLimitPerDay;

	@JsonProperty("started_at")
	private String startedAt;

	@JsonProperty("expired_at")
	private String expiredAt;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("requests_limit_per_second")
	private Integer requestsLimitPerSecond;

	@JsonProperty("requests_limit_per_minute")
	private Integer requestsLimitPerMinute;

	@JsonProperty("expires_in")
	private Integer expiresIn;

	@JsonProperty("requests_limit_per_hour")
	private Integer requestsLimitPerHour;
}
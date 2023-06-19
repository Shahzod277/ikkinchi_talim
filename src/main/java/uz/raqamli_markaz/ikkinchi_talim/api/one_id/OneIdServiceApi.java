package uz.raqamli_markaz.ikkinchi_talim.api.one_id;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class OneIdServiceApi {

    private final String client_id = "ikkilamchitalim_edu_uz";
    private final String scope = "test.edu.uz";
    private final String redirect_uri = "https://localhost:8090/auth/code";
    private final String client_secret = "58T0vHQnAfB0XN2eLIyPzy9U";
    public static final String ONE_ID_LOGIN = "khm_qabul";
    public static final String ONE_ID_PASSWORD = "pmQgROZaRRaNHqdY";
    private final WebClient webClient;

    public URI redirectOneIdUrl() {
        String state = "testState";
        String response_type = "one_code";
        String oneIdUrl = "https://sso.egov.uz/sso/oauth/Authorization.do?" +
                "state=" + state +
                "&response_type=" + response_type +
                "&client_id=" + client_id +
                "&scope=" + scope +
                "&redirect_uri=" + redirect_uri;
        return URI.create(oneIdUrl);
    }

    public OneIdResponseToken getAccessAndRefreshToken(String code) {
        String grant_type = "one_authorization_code";
        return webClient.post()
                .uri("https://sso.egov.uz/sso/oauth/Authorization.do?" +
                        "grant_type=" + grant_type +
                        "&client_id=" + client_id +
                        "&client_secret=" + client_secret +
                        "&code=" + code +
                        "&redirect_uri=" + redirect_uri)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(ONE_ID_LOGIN, ONE_ID_PASSWORD))
                .retrieve()
                .bodyToMono(OneIdResponseToken.class)
                .block();
    }

    public OneIdResponseUserInfo getUserInfo(String accessToken) {
        String grant_type = "one_access_token_identify";
        return webClient.post()
                .uri("https://sso.egov.uz/sso/oauth/Authorization.do?" +
                        "grant_type=" + grant_type +
                        "&client_id=" + client_id +
                        "&client_secret=" + client_secret +
                        "&access_token=" + accessToken +
                        "&scope=" + scope)
                .headers(httpHeaders -> httpHeaders.setBasicAuth(ONE_ID_LOGIN, ONE_ID_PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OneIdResponseUserInfo.class)
                .block();
    }


}

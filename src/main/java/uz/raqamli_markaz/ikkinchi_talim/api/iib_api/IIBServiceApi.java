package uz.raqamli_markaz.ikkinchi_talim.api.iib_api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest.CheckUserRequest;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest.IIBRequest;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest.IIBResponseNew;
import uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest.IIBWithBirthDateRequest;
import uz.raqamli_markaz.ikkinchi_talim.domain.TokenEntity;
import uz.raqamli_markaz.ikkinchi_talim.repository.TokenEntityRepository;

import java.util.Optional;

@Service
public class IIBServiceApi {

    private final WebClient webClient;
    private final TokenEntityRepository tokenEntityRepository;

    public IIBServiceApi(WebClient webClient, TokenEntityRepository tokenEntityRepository) {
        this.webClient = webClient;
        this.tokenEntityRepository = tokenEntityRepository;
    }


    public IskmTokenResponse getIskmToken() {

        Optional<TokenEntity> dtmToken = tokenEntityRepository.findByOrgName("iskm");
        if (dtmToken.isPresent()) {
            if (dtmToken.get().getEndTime() < System.currentTimeMillis()) {
                IskmTokenResponse tokenResponse = getIskmTokenResponse();
                assert tokenResponse != null;
                dtmToken.get().setToken(tokenResponse.getAccessToken());
                dtmToken.get().setEndTime(System.currentTimeMillis() + 3000000);
                tokenEntityRepository.save(dtmToken.get());
                return tokenResponse;
            } else {
                return new IskmTokenResponse(dtmToken.get());
            }
        }

        IskmTokenResponse tokenResponse = getIskmTokenResponse();
        assert tokenResponse != null;
        TokenEntity tokenEntity = new TokenEntity(tokenResponse.getExpiresIn(), "iskm", tokenResponse.getAccessToken());
        TokenEntity saveTokenEntity = tokenEntityRepository.save(tokenEntity);
        return new IskmTokenResponse(saveTokenEntity);
    }

    private IskmTokenResponse getIskmTokenResponse() {

        String grant_type = "password";
        String username = "minvuz_user1";
        String password = "m1nvuz!@#";

        return webClient.post()
                .uri("https://iskm.egov.uz:9444/oauth2/token")
                .attribute("grant_type", grant_type)
                .attribute("username", username)
                .attribute("password", password)
                .headers(httpHeader -> httpHeader.setBasicAuth("lfZJDHdJm9Sw7UNsI0uUJzLZp9Ea", "s3cJ2Q3t0zswsMNXzE6nIh3KV8Ua"))
                .bodyValue(new IskmTokenRequest())
                .retrieve()
                .bodyToMono(IskmTokenResponse.class)
                .block();
    }


//    public IIBResponse iibResponse(IIBRequest iibRequest) {
//        return webClient.post()
//                .uri("http://172.18.9.169:9449/api/person-info-with-photo/")
//                .headers(httpHeaders -> httpHeaders.setBasicAuth(ApiConstant.IIB_API_LOGIN, ApiConstant.IIB_API_PASSWORD))
//                .bodyValue(iibRequest)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(IIBResponse.class)
//                .block();
//    }
//    public List<TestResponseItem> getPasportSerialAndNumber(PinflRequest request) {
//        return webClient.post()
//                .uri("http://172.18.9.169:9449/api/match-persons-passport-serial-number/")
//                .headers(httpHeaders -> httpHeaders.setBasicAuth(ApiConstant.IIB_API_LOGIN, ApiConstant.IIB_API_PASSWORD))
//                .bodyValue(request)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<TestResponseItem>>() {
//                }).block();
//    }
//    public List<PinflResponse1> getPasportSerialAndNumber1(PinflRequest request) {
//        return webClient.post()
//                .uri("http://172.18.9.169:9449/api/match-persons-passport-serial-number/")
//                .headers(httpHeaders -> httpHeaders.setBasicAuth(ApiConstant.IIB_API_LOGIN, ApiConstant.IIB_API_PASSWORD))
//                .bodyValue(request)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToFlux(PinflResponse1.class).collectList().block();
//    }


    public IIBResponseNew getIibUser(CheckUserRequest request) {
        IIBRequest iibRequest = new IIBRequest();
        iibRequest.setBirth_date(request.getBirthDate());
        iibRequest.setPinpp(request.getPinfl());
        String accessToken = getIskmToken().getAccessToken();
        IIBResponseNew response = webClient.post()
                .uri("https://apimgw.egov.uz:8243/gcp/docrest/v1")
                .headers(httpHeader -> httpHeader.setBearerAuth(accessToken))
                .bodyValue(iibRequest)
                .retrieve()
                .onStatus(HttpStatus.NO_CONTENT::equals, clientResponse -> Mono.empty())
                .bodyToMono(IIBResponseNew.class).block();
        if (response == null || response.getData() == null) {
            IIBWithBirthDateRequest iibRequest2 = new IIBWithBirthDateRequest();
            iibRequest2.setPinpp(request.getPinfl());
            iibRequest2.setBirth_date(request.getBirthDate());
            response = webClient.post()
                    .uri("https://apimgw.egov.uz:8243/gcp/docrest/v1")
                    .headers(httpHeader -> httpHeader.setBearerAuth(accessToken))
                    .bodyValue(iibRequest2)
                    .retrieve()
                    .onStatus(HttpStatus.NO_CONTENT::equals, clientResponse -> Mono.empty())
                    .bodyToMono(IIBResponseNew.class).block();
        }
        return response;
    }

}

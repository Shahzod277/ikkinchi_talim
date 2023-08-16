package uz.raqamli_markaz.ikkinchi_talim.api.iib_api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.model.request.IIBRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.request.PinflRequest;
import uz.raqamli_markaz.ikkinchi_talim.model.response.PinflResponse1;

import java.util.List;

@Service
public class IIBServiceApi {

    private final WebClient webClient;

    public IIBServiceApi(WebClient webClient) {
        this.webClient = webClient;
    }

    public IIBResponse iibResponse(IIBRequest iibRequest) {
        return webClient.post()
                .uri("http://172.18.9.169:9449/api/person-info-with-photo/")
                .headers(httpHeaders -> httpHeaders.setBasicAuth(ApiConstant.IIB_API_LOGIN, ApiConstant.IIB_API_PASSWORD))
                .bodyValue(iibRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(IIBResponse.class)
                .block();
    }
    public List<PinflResponse1> getPasportSerialAndNumber(PinflRequest request) {
        return webClient.post()
                .uri("http://172.18.9.169:9449/api/match-persons-passport-serial-number/")
                .headers(httpHeaders -> httpHeaders.setBasicAuth(ApiConstant.IIB_API_LOGIN, ApiConstant.IIB_API_PASSWORD))
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(PinflResponse1.class).collectList().block();
    }
}

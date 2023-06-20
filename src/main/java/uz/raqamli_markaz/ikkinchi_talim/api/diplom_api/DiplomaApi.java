package uz.raqamli_markaz.ikkinchi_talim.api.diplom_api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.api.diplom_api.formEdu.FormEduResponse;
import uz.raqamli_markaz.ikkinchi_talim.domain.TokenEntity;
import uz.raqamli_markaz.ikkinchi_talim.repository.TokenEntityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiplomaApi {

    private final WebClient webClient;
    private final TokenEntityRepository tokenEntityRepository;

    public String getToken() {
        TokenEntity tokenEntity = tokenEntityRepository.findByOrgName("d-arxiv").get();
        long now = System.currentTimeMillis();
        if (tokenEntity.getEndTime() < now) {
            DArxivTokenResponse response = this.webClient.get()
                    .uri(ApiConstant.D_ARXIV_TOKEN_API)
                    .headers(httpHeader -> httpHeader.setBasicAuth(ApiConstant.D_ARXIV_LOGIN, ApiConstant.D_ARXIV_PASSWORD))
                    .retrieve()
                    .bodyToMono(DArxivTokenResponse.class)
                    .block();
            assert response != null;
            String accessToken = response.getDataToken().getNewToken().getAccessToken();
            tokenEntity.setToken(accessToken);
            tokenEntity.setEndTime(now + 83000);
            tokenEntityRepository.save(tokenEntity);
            return accessToken;
        }
        return tokenEntity.getToken();

    }


    public List<DiplomaResponseInfo> getDiploma(String pinfl) {

        String DIPLOMA_URL = "https://d-arxiv.edu.uz/api/v2/diploma/get?pinfl=" + pinfl;
        return this.webClient.get()
                .uri(DIPLOMA_URL)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .retrieve()
                .bodyToFlux(DiplomaResponseInfo.class)
                .collectList()
                .block();
    }
    // ta'lim daraja bakalavr , magistr


    public CreateDiplomaResponse createDiploma(CreateDiplomaRequest request) {

        String url = "https://d-arxiv.edu.uz/api/v2/diploma/create";
        return this.webClient.post()
                .uri(url)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CreateDiplomaResponse.class)
                .block();
    }

    public FormEduResponse getFormEdu() {

        String url = "https://d-arxiv.edu.uz/api/v2/reference/edu-forms";
        return this.webClient.get()
                .uri(url)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .retrieve()
                .bodyToMono(FormEduResponse.class)
                .block();
    }


}

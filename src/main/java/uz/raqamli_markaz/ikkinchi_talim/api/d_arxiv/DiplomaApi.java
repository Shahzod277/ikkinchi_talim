package uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.*;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials.DiplomaSerialResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diploma_serials.DiplomaSerials;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.formEdu.FormEduResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institution_old_names.InstitutionOldNamesResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.institutions.InstitutionResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.specialities.SpecialitiesResponseApi;
import uz.raqamli_markaz.ikkinchi_talim.domain.TokenEntity;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.diplomaApi.DArxivTokenRequest;
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
        DArxivTokenRequest request = new DArxivTokenRequest();
        if (tokenEntity.getEndTime() < now) {
            DArxivTokenResponse response = webClient.post()
                    .uri(ApiConstant.D_ARXIV_TOKEN_API)
                        .bodyValue(request)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(DArxivTokenResponse.class)
                    .block();
//            ObjectMapper objectMapper = new ObjectMapper();
//            DArxivTokenResponse dArxivTokenResponse = objectMapper.convertValue(response, DArxivTokenResponse.class);
            DataToken data = response.getData();
            NewToken newToken = data.getNewToken();
            tokenEntity.setToken(newToken.getAccessToken());
            tokenEntity.setEndTime(now + 83000);
            tokenEntityRepository.save(tokenEntity);
            return newToken.getAccessToken();
        }
        return tokenEntity.getToken();

    }

    public List<DiplomaResponseApi> getDiploma(String pinfl) {

        String DIPLOMA_URL = "https://d-arxiv.edu.uz/api/v2/diploma/get?pinfl=" + pinfl;
        return this.webClient.get()
                .uri(DIPLOMA_URL)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .retrieve()
                .bodyToFlux(DiplomaResponseApi.class)
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

    public SpecialitiesResponseApi getSpecialities() {

        String url = "https://d-arxiv.edu.uz/api/v2/reference/specialities";
        return this.webClient.get()
                .uri(url)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .retrieve()
                .bodyToMono(SpecialitiesResponseApi.class)
                .block();
    }

    public InstitutionResponse getInstitutions() {
        String url = "https://d-arxiv.edu.uz/api/v2/reference/institutions";
        return this.webClient.get()
                .uri(url)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .retrieve()
                .bodyToMono(InstitutionResponse.class)
                .block();
    }
    public InstitutionOldNamesResponse getInstitutionsOldNames() {
        String url = "https://d-arxiv.edu.uz/api/v2/reference/institution-old-names";
        return this.webClient.get()
                .uri(url)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .retrieve()
                .bodyToMono(InstitutionOldNamesResponse.class)
                .block();
    }
    public DiplomaSerialResponse getDiplomaSerials() {
        String url = "https://d-arxiv.edu.uz/api/v2/reference/diploma-serials";
        return this.webClient.get()
                .uri(url)
                .headers(httpHeader -> httpHeader.setBearerAuth(getToken()))
                .retrieve()
                .bodyToMono(DiplomaSerialResponse.class)
                .block();
    }

}

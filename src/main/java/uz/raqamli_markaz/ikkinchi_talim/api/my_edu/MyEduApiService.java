package uz.raqamli_markaz.ikkinchi_talim.api.my_edu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.api.d_arxiv.formEdu.FormEduResponse;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response.UserResponseMyEdu;

@Service
@RequiredArgsConstructor
public class MyEduApiService {
    private final WebClient webClient;


    public UserResponseMyEdu getUserByToken(String token) {
        return this.webClient.get()
                .uri(ApiConstant.USERS_ME_API)
                .headers(httpHeader -> httpHeader.set("Authorization", "Local " + token))
                .retrieve()
                .bodyToMono(UserResponseMyEdu.class)
                .block();
    }

    public CreateAppRequestMyEdu createApp(String token, CreateAppRequestMyEdu request) {
        return this.webClient.post()
                .uri(ApiConstant.CREATE_APP_MYEDU)
                .headers(httpHeader -> httpHeader.set("Authorization", "Local " + token))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CreateAppRequestMyEdu.class)
                .block();
    }

    public CreateAppRequestMyEdu updateApp(String token, CreateAppRequestMyEdu request) {
        return this.webClient.put()
                .uri(ApiConstant.CREATE_APP_MYEDU + request.getExternalId() + "/")
                .headers(httpHeader -> httpHeader.set("Authorization", "Local " + token))
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(CreateAppRequestMyEdu.class)
                .block();
    }

}

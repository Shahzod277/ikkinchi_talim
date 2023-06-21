
package uz.raqamli_markaz.ikkinchi_talim.api.hemis_university_speciality_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.raqamli_markaz.ikkinchi_talim.api.ApiConstant;
import uz.raqamli_markaz.ikkinchi_talim.domain.TokenEntity;
import uz.raqamli_markaz.ikkinchi_talim.repository.TokenEntityRepository;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HemisUniSpecApi {

    private static final String UNIVERSITY_URL = "https://student.hemis.uz/rest/v1/public/university-list";

    private final WebClient webClient;
    private final TokenEntityRepository tokenEntityRepository;

    public HemisTokenResponse hemisGetToken() {
        Optional<TokenEntity> hemisToken = tokenEntityRepository.findByOrgName("hemis");
        if (hemisToken.get().getEndTime() < System.currentTimeMillis()) {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "password");
            formData.add("username", "adabiyot");
            formData.add("password", "vvFVXYsE6Gs4sLQ");
            HemisTokenResponse tokenResponse = webClient.post()
                    .uri(ApiConstant.HEMIS_TOKEN_URL)
                    .headers(httpHeader -> httpHeader.set("Authorization", "Basic Y2xpZW50OnNlY3JldA=="))
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .onStatus(HttpStatus.BAD_GATEWAY::equals, response -> Mono.empty())
                    .bodyToMono(HemisTokenResponse.class)
                    .block();
            if (tokenResponse == null) {
                return null;
            }
            hemisToken.get().setToken(tokenResponse.getAccess_token());
            hemisToken.get().setEndTime(System.currentTimeMillis() + tokenResponse.getExpires_in());
            hemisToken.get().setModifiedDate(LocalDateTime.now());
            tokenEntityRepository.save(hemisToken.get());
            return tokenResponse;
        } else {
            return new HemisTokenResponse(hemisToken.get());
        }
    }
    public HemisUniversityResponse getUniversity() {
        return webClient.get()
                .uri(UNIVERSITY_URL)
                .retrieve()
                .bodyToMono(HemisUniversityResponse.class)
                .block();
    }

    @Transactional
    public HemisSpecialityResponse hemisSpecialityResponse(String universityCode, String type) {
        HemisTokenResponse hemisToken = hemisGetToken();
        return webClient.get()
                .uri("https://ministry.hemis.uz/app/rest/v2/services/speciality/get?university=" + universityCode + "&type=" + type)
                .headers(h -> h.setBearerAuth(hemisToken.getAccess_token()))
                .retrieve()
                .bodyToMono(HemisSpecialityResponse.class)
                .block();
    }

//    @Transactional
//    public FacultyHemisResponse hemisFacultyResponse(String universityCode) {
//        HemisTokenResponse hemisToken = hemisGetToken();
//        return webClient.get()
//                .uri("https://ministry.hemis.uz/app/rest/v2/services/faculty/get?university=" + universityCode)
//                .headers(h -> h.setBearerAuth(hemisToken.getAccess_token()))
//                .retrieve()
//                .bodyToMono(FacultyHemisResponse.class)
//                .block();
//    }

//    @Transactional
//    public CathedraResponseApi hemisCathedraResponse(String universityCode) {
//        HemisTokenResponse hemisToken = hemisGetToken();
//        return webClient.get()
//                .uri("https://ministry.hemis.uz/app/rest/v2/services/cathedra/get?university=" + universityCode)
//                .headers(h -> h.setBearerAuth(hemisToken.getAccess_token()))
//                .retrieve()
//                .bodyToMono(CathedraResponseApi.class)
//                .block();
//    }


//    public TeacherResponse getHemisTeacherJobsByPinfl(String pinfl) {
//        HemisTokenResponse hemisGetToken = hemisGetToken();
//        return webClient.get()
//                .uri("https://ministry.hemis.uz/app/rest/v2/services/teacher/get?pinfl=" + pinfl)
//                .headers(h -> h.setBearerAuth(hemisGetToken.getAccess_token()))
//                .retrieve()
//                .bodyToMono(TeacherResponse.class)
//                .block();
//    }

//    public StudentInfo getStudentInfo(String pinfl) {
////        String url = "https://ministry.hemis.uz/app/rest/v2/services/student/get?pinfl=" + pinfl;
//        return webClient.get().uri("https://ministry.hemis.uz/app/rest/v2/services/student/get?pinfl=" + pinfl)
//                .headers(httpHeader -> httpHeader.setBearerAuth(hemisGetToken().getAccess_token()))
//                .retrieve()
//                .bodyToMono(StudentInfo.class).block();
//
//    }

//    public StudentInfo getStudentInfoById(String id) {
//        String url = "https://ministry.hemis.uz/app/rest/v2/services/student/getById?id=" + id;
//        return webClient.get().uri(url)
//                .headers(httpHeader -> httpHeader.setBearerAuth(hemisGetToken().getAccess_token()))
//                .retrieve()
//                .bodyToMono(StudentInfo.class).block();
//    }
//
//    public TeacherResponse getTeacherJobsById(String id) {
//        String url = "https://ministry.hemis.uz/app/rest/v2/services/teacher/getById?id=" + id;
//        return webClient.get().uri(url)
//                .headers(httpHeader -> httpHeader.setBearerAuth(hemisGetToken().getAccess_token()))
//                .retrieve()
//                .bodyToMono(TeacherResponse.class).block();
//    }
}
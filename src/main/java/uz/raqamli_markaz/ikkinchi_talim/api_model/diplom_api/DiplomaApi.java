package uz.raqamli_markaz.ikkinchi_talim.api_model.diplom_api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiplomaApi {

    private final WebClient webClient;

    String token = "jfqubdpWtR8qAOiUrFq5iPRlU8j1uOEaVWqHw6iLLgdV5Q_0-MBWEm3du3GjsZ726RFyuFSB_qRZrDlQj4kBAf3cBDxlpMwr_Ezp8LzbumTMIe5jTFsVCMuD7O3QjJtBWInZvjyzIs_8nEzFRVEBKsK4MJDSbEz56v58fAoRxk6HQpu9uuXUpgHcQaoTTtmuY21-Rtc";

    public List<DiplomaResponseInfo> getDiploma(String pinfl) {

        String DIPLOMA_URL = "http://172.18.10.10/api/v2/diploma/get?pinfl=" + pinfl;
        return this.webClient.get()
                .uri(DIPLOMA_URL)
                .headers(httpHeader -> httpHeader.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(DiplomaResponseInfo.class)
                .collectList()
                .block();
    }

}

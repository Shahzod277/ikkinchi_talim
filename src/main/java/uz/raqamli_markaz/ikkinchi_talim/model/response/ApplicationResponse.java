package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.api.my_edu.user_response.UserResponseMyEdu;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.Kvota;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private Integer id;
    private String status;
    private String applicationMessage;
    private String diplomaMessage;
    private Kvota kvota;
    private DiplomaResponse diplomaResponse;

    //Page uchun

}

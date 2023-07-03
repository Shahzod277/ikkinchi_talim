package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private String status;
    private Boolean diplomaStatus;
    private String message;
    private String diplomaMessage;
    private String language;
    private Integer diplomaInstitutionId;
    private String diplomaInstitutionName;

    public ApplicationResponse(Application application) {
        this.status = application.getStatus();
        this.diplomaStatus = application.getDiplomaStatus();
        this.message = application.getMessage();
        this.diplomaMessage = application.getDiplomaMessage();
    }
}

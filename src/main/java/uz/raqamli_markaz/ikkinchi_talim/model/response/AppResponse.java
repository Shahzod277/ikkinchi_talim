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
public class AppResponse {

    private DiplomaResponse diplomaResponse;
    private StoryM storyM;

    Integer id;
    private Integer tilId;
    private String tilName;
    private Integer shaklId;
    private String shaklName;
    private Integer directionId;
    private String directionName;
    private Integer futureInstitutionId;
    private String futureInstitutionName;
    private String appStatus;
    private String diplomaStatus;
    private String createdDate;

    public AppResponse(Application application) {
        this.id = application.getId();
        this.appStatus = application.getApplicationStatus();
        this.diplomaStatus = String.valueOf(application.getDiplomaStatus());
        this.createdDate = application.getCreatedDate().toString();
    }
}

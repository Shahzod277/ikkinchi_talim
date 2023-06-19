package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiplomaStatusRequest {

    private Integer applicationId;
    private Boolean diplomaStatus;
    private String message;
}

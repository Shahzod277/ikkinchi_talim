package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDiplomaStatus {
    private Boolean diplomStatus;
    private String diplomMessage;
}

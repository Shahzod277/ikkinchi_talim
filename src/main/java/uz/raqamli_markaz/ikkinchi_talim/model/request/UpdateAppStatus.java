package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAppStatus {
    private String AppStatus;
    private String AppMessage;
}

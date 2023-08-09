package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmAppRequest {
    private Integer applicationId;
    private Integer isConfirm;//1 tasdiqlangan ,0 rad etilgan,2 diplom rad etish
    private String message;

}

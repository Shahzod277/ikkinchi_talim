package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmDiplomaRequest {

    private Integer diplomaId;
    private Integer isConfirm;//1 tasdiqlangan ,0 rad etilgan
    private String message;
    private Integer isNational;  // 1 davlat ,0 xorijiy

}

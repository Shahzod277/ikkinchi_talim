package uz.raqamli_markaz.ikkinchi_talim.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptAndRejectAndCheckDiploma {

    private Integer acceptAppCount;
    private Integer rejectAppCount;
    private Integer checkDiplomaCount;
    private Integer AcceptDiplomaCount;
    private String futureInstName;
    private Integer allAppCount;

}

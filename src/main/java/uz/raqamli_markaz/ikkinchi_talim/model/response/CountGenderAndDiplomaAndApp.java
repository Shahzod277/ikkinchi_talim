package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CountGenderAndDiplomaAndApp {

    List<GetAppByGender> diplomaGenderCount;
    List<GetCountAppallDate> diplomaCountToday;
    List<GetAppByGender> ForeigndiplomaGenderCount;
    List<GetCountAppallDate> ForeigndiplomaCountToday;
}

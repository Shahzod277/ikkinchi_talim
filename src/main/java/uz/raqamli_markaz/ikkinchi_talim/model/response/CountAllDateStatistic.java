package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CountAllDateStatistic {

    Map<String,List<GetCountAppallDate>> statisticByDate   =new HashMap();
    Map<String,List<GetAppByGender>> statisticByGender   =new HashMap();
}

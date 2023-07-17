package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class CountAllDateStatistic {
    List<GetCountAppallDate> nationalDiploma=new ArrayList<>();
    List<GetCountAppallDate> foreignDiploma=new ArrayList<>();
    List<GetCountAppallDate> applicationDiploma=new ArrayList<>();

}

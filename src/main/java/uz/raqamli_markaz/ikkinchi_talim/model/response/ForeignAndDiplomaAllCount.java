package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ForeignAndDiplomaAllCount {

    private Integer countDiploma;
    private Integer countForeign;
    private List<CountApp> diploma;
    private List<CountApp> foreign;

}

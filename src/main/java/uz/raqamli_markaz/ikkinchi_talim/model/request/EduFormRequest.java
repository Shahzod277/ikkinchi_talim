package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EduFormRequest {

    private String name;
    private Integer directionId;
    private List<LanguageRequest> languages;
}

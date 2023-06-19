package uz.raqamli_markaz.ikkinchi_talim.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageRequest {

    private Integer id;
    private String name;
    private Integer kvota;
}

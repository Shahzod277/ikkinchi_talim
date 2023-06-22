package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Language;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageResponse {

    private Integer id;
    private String name;
    private Integer kvota;

    public LanguageResponse(Language language) {
        this.id = language.getId();
        this.name = language.getName();
        this.kvota = language.getKvotaSoni();
    }
}

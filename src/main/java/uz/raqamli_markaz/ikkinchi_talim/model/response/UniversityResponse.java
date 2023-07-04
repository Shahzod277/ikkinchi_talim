package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityResponse {

    private Integer id;
    private String code;
    private String nameUz;
    private String nameOz;
    private String nameEn;
    private String nameRu;
}

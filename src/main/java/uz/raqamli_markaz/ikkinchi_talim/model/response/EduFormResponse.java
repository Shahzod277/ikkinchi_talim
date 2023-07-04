package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.EduForm;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EduFormResponse {

    private Integer id;
    private Integer code;
    private String nameOz;

    public EduFormResponse(EduForm eduForm) {
        this.id = eduForm.getId();
        this.code = eduForm.getCode();
        this.nameOz = eduForm.getNameOz();
    }
}

package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.EduForm;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EduFormResponse {

    private Integer id;
    private String name;
    private Integer directionId;
    private String directionName;
    private Integer futureInsId;
    private String futureInstName;
    private List<LanguageResponse> languages;

    public EduFormResponse(EduForm eduForm, List<LanguageResponse> languages) {
        this.id = eduForm.getId();
        this.name = eduForm.getName();
        this.directionId = eduForm.getDirection().getId();
        this.directionName = eduForm.getDirection().getName();
        this.futureInsId = eduForm.getDirection().getDiplomaInstitution().getId();
        this.futureInstName = eduForm.getDirection().getDiplomaInstitution().getName();
        this.languages = languages;
    }

    public EduFormResponse(EduForm eduForm) {
        this.id = eduForm.getId();
        this.name = eduForm.getName();
        this.directionId = eduForm.getDirection().getId();
        this.directionName = eduForm.getDirection().getName();
        this.futureInsId = eduForm.getDirection().getDiplomaInstitution().getId();
        this.futureInstName = eduForm.getDirection().getDiplomaInstitution().getName();
    }
}

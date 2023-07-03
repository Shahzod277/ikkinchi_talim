package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiplomaSpecialityResponse {

    private Integer specialityId;
    private Integer institutionId; //clasifikator idga teng
    private String nameOz;

    public DiplomaSpecialityResponse(DiplomaSpeciality diplomaSpeciality) {

        this.specialityId = diplomaSpeciality.getSpecialitiesId();
        this.institutionId = diplomaSpeciality.getInstitutionId();
        this.nameOz = diplomaSpeciality.getNameOz();
    }
}

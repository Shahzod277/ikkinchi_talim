package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.Getter;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.AdminEntity;


@Getter
@Setter
public class UAdminInfoResponse {

    private Integer id;
    private String firstname;
    private String lastname;
    private Integer futureInstitutionId;
    private String futureInstitutionName;


    public UAdminInfoResponse(AdminEntity adminEntity) {
        this.id = adminEntity.getId();
        this.firstname = adminEntity.getFistName();
        this.lastname = adminEntity.getLastname();
        if (adminEntity.getDiplomaInstitution() != null) {
            this.futureInstitutionId = adminEntity.getDiplomaInstitution().getId();
            this.futureInstitutionName = adminEntity.getDiplomaInstitution().getInstitutionNameOz();

        }
    }
}

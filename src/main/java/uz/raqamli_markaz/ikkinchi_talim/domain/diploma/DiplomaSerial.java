package uz.raqamli_markaz.ikkinchi_talim.domain.diploma;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.AbstractEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiplomaSerial extends AbstractEntity {

    private Integer statusId;
    private Integer degreeId;
    private String serial;
    private Integer creatorId;
    private String createdAt;
    private Integer id;
    private Object endYear;
    private Integer beginYear;
}

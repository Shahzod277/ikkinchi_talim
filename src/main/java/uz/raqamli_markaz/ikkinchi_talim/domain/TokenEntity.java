package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "token_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenEntity extends AbstractEntity {

    private String token;
    private String orgName;
    private Long endTime;
}

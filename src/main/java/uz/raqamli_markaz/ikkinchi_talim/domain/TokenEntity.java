package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "token_entity", indexes = {
        @Index(name = "idx_token_entity_org_name", columnList = "orgName")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenEntity extends AbstractEntity {

    @Column(length = 2048)
    private String token;

    @Column(length = 2048)
    private String orgName;

    private long endTime;

    public TokenEntity(long endTime, String orgName) {
        this.endTime = endTime;
        this.orgName = orgName;
    }

    public TokenEntity(long endTime, String orgName, String token) {
        this.endTime = endTime;
        this.orgName = orgName;
        this.token = token;
    }
}

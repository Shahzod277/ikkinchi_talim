package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application extends AbstractEntity {

    private String applicationStatus;
    private String applicationMessage;
    private Boolean diplomaStatus;
    private String diplomaMessage;

    @ManyToOne
    private Kvota kvota;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}

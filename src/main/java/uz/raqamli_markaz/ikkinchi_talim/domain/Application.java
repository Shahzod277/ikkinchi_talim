package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.*;
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
    @Column(columnDefinition = "TEXT")
    private String applicationMessage;
    private String diplomaMessage;

    @ManyToOne
    private Kvota kvota;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}

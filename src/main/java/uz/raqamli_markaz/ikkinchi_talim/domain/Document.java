package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends AbstractEntity {

    @Column(length = 4096)
    private String fileName;

    @Column(length = 4096)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Diploma diploma;
}

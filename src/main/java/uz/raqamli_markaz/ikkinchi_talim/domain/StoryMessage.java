package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class StoryMessage extends AbstractEntity {

    private String status;
    private String message;
    private String pinfl;
    private String firstname;
    private String lastname;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;
}

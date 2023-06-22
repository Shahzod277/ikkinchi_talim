package uz.raqamli_markaz.ikkinchi_talim.domain.diploma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "university", indexes = @Index(name = "university_code", columnList = "code"))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class University extends AbstractEntity {

    private String code;
    private String name;



}

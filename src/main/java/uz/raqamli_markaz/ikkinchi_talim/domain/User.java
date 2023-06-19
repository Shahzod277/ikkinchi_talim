package uz.raqamli_markaz.ikkinchi_talim.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = @Index(columnList = "phoneNumber"))
public class User extends AbstractEntity {

    private String phoneNumber;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
}

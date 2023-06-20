package uz.raqamli_markaz.ikkinchi_talim.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.Role;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = @Index(columnList = "phoneNumber"))
public class User extends AbstractEntity {

    private String password;
    private String citizenship;
    private String firstname;
    private String middleName;
    private String lastname;
    private String dateOfBirth;
    private String gender;
    private String passportSerialAndNumber;
    private String passportGivenDate;
    private String pinfl;
    private String phoneNumber;
    private String nationality;
    private String permanentRegion;
    private String permanentDistrict;
    private String permanentAddress;
    private String photo;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "users_role",
            joinColumns = @JoinColumn(name = "user_id",
                    foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER")),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    foreignKey = @ForeignKey(name = "FK_USER_ROLE_ROLE")))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(password, user.password) && Objects.equals(citizenship, user.citizenship) && Objects.equals(firstname, user.firstname) && Objects.equals(middleName, user.middleName) && Objects.equals(lastname, user.lastname) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(gender, user.gender) && Objects.equals(passportSerialAndNumber, user.passportSerialAndNumber) && Objects.equals(passportGivenDate, user.passportGivenDate) && Objects.equals(pinfl, user.pinfl) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(nationality, user.nationality) && Objects.equals(permanentRegion, user.permanentRegion) && Objects.equals(permanentDistrict, user.permanentDistrict) && Objects.equals(permanentAddress, user.permanentAddress) && Objects.equals(photo, user.photo) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, password, citizenship, firstname, middleName, lastname, dateOfBirth, gender, passportSerialAndNumber, passportGivenDate, pinfl, phoneNumber, nationality, permanentRegion, permanentDistrict, permanentAddress, photo, roles);
    }
}

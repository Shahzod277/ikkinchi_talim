package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.phoneNumber = ?1 ")
    @EntityGraph(attributePaths = "role")
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("select u from User u where u.pinfl= ?1 ")
    @EntityGraph(attributePaths = "role")
    Optional<User> findUserByPinfl(String pinfl);

    @Query(value = "select u from User u where u.role.id=1")
    List<User> findAllByUadmin();

    @Query( value = " select u from Application a inner join User u on u.id = a.user.id " +
            " where u.myEduId is not null and u.passportSerial is null  and a.applicationStatus='Ariza tasdiqlandi' ")
    List<User> findAllByRoleIsNull();

}

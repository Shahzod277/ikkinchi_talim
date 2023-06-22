package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiplomaRepository extends JpaRepository<Diploma, Integer> {

    @Query("select d from Diploma d where d.user.pinfl = ?1 ")
    List<Diploma> findAllDiplomaByUser(String pinfl);

    @Query("select d from Diploma d where d.id =?1 and d.user.pinfl =?2 ")
    Optional<Diploma> findDiplomaByDiplomaIdAndUser(Integer id, String pinfl);

    @Query("select (count(d) > 0) from Diploma d where d.isActive = true and d.user.pinfl=?1")
    Boolean existsDiplomaByIsActiveCount(String pinfl);

    @Query("select d from Diploma d where d.diplomaId =?1 ")
    Optional<Diploma> findDiplomaByDiplomaId(Integer diplomaId);

    @Query(nativeQuery = true, value = "select * from diploma d " +
            "join enrollee_info ei on ei.id = d.enrollee_info_id " +
            "join users u on ei.user_id = u.id where u.phone_number = ?1 and d.is_active = true ")
    Optional<Diploma> getDiplomaProfile(String phoneNumber);

    @Query(nativeQuery = true, value = "select * from diploma d " +
            "join enrollee_info ei on ei.id = d.enrollee_info_id " +
            "join users u on ei.user_id = u.id where u.phone_number = ?1 ")
    List<Diploma> findAllDiplomaByEnrollee(String phoneNumber);

}

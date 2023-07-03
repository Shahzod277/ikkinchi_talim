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

}

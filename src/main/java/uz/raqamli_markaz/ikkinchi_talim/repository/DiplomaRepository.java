package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiplomaRepository extends JpaRepository<Diploma, Integer> {

    @Query("select d from Diploma d where d.user.id = ?1 ")
    List<Diploma> findAllDiplomaByUser(Integer id);

    @Query("select d from Diploma d where d.institutionIdDb = ?1 and d.id= ?2 ")
    Optional<Diploma> findDiplomaByInstitution(Integer institutionId, Integer diplomaId);

    @Query("select d from Diploma d where d.institutionIdDb = ?1 ")
    Page<Diploma> findAllDiplomaByInstitution(Integer institutionId, Pageable pageable);

    @Query("select d from Diploma d where d.id =?1 and d.user.id =?2 ")
    Optional<Diploma> findDiplomaByDiplomaIdAndUser(Integer id, Integer userId);

    @Query("select (count(d) > 0) from Diploma d where d.isActive = true and d.user.id=?1")
    Boolean existsDiplomaByIsActiveCount(Integer id);

    @Query("select d from Diploma d where d.diplomaId =?1 and d.isActive = true")
    Optional<Diploma> findDiplomaByDiplomaId(Integer diplomaId);
    @Query("select d from Diploma d where d.user.id =?1 and d.isActive = true")
    Optional<Diploma> findActiveDiplomaByUser(Integer id);


}

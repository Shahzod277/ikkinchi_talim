package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiplomaOldInstitutionRepository extends JpaRepository<DiplomaOldInstitution, Integer> {

    @Query("select d from DiplomaOldInstitution d where d.classificatorId = ?1 and d.institutionOldId=?2")
    Optional<DiplomaOldInstitution> findDiplomaOldInstitutionByOldId(Integer id,Integer oldId);
    @Query(value = "select distinct d.classificatorId from DiplomaOldInstitution d ")
    List<Integer> getAllDiplomaInst();

}

package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaOldInstitution;

import java.util.Optional;

@Repository
public interface DiplomaOldInstitutionRepository extends JpaRepository<DiplomaOldInstitution, Integer> {

    @Query("select d from DiplomaOldInstitution d where d.classificatorId = ?1 and ")
    Optional<DiplomaOldInstitution> findDiplomaOldInstitutionByOldId(Integer id);
}

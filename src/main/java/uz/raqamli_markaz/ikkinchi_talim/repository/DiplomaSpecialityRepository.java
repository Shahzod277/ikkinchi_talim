package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiplomaSpecialityRepository extends JpaRepository<DiplomaSpeciality, Integer> {
    @Query("select d from DiplomaSpeciality d where d.institutionId = ?1")
    List<DiplomaSpeciality> findDiplomaSpecialitiesByInstitutionId(Integer institutionId);

    @Query("select d from DiplomaSpeciality d where d.specialitiesId = ?1")
    Optional<DiplomaSpeciality> findDiplomaSpecialitiesById(Integer specialitiesId);

}

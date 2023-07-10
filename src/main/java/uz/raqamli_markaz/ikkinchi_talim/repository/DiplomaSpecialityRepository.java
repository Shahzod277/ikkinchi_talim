package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSpeciality;
import uz.raqamli_markaz.ikkinchi_talim.model.response.SpecialityProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiplomaSpecialityRepository extends JpaRepository<DiplomaSpeciality, Integer> {
    @Query(nativeQuery = true, value = "select ds.id specialityId ,ds.name_oz specialityName ,d.id institutionId  from diploma_speciality ds inner join diploma_institution d on ds.institution_id = d.classificator_id where d.id=?1 ")
    List<SpecialityProjection> findDiplomaSpecialitiesByInstitutionId(Integer id);

    @Query("select d from DiplomaSpeciality d where d.id = ?1")
    Optional<DiplomaSpeciality> findDiplomaSpecialitiesById(Integer specialitiesId);

}

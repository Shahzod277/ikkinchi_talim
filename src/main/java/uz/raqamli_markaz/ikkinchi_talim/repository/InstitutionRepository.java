package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.University;
import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<University, Integer> {

    List<University> findAllByInstitutionTypeIdBetween(Integer institutionTypeId, Integer institutionTypeId2);

}

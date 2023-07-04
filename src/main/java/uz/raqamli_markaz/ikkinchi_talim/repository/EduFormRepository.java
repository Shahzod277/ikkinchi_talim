package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.EduForm;

@Repository
public interface EduFormRepository extends JpaRepository<EduForm, Integer> {

}

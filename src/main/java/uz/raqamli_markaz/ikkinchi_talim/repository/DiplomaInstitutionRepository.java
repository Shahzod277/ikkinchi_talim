package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaInstitution;

@Repository
public interface DiplomaInstitutionRepository extends JpaRepository<DiplomaInstitution, Integer> {

    @Query("select di from DiplomaInstitution di where di.institutionName like %?1%")
    Page<DiplomaInstitution> searchDiplomaInstitution(String text, Pageable pageable);
}

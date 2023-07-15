package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.University;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
    @Query("select u from University u where u.code = ?1")
    Optional<University> findByCode(String code);
}

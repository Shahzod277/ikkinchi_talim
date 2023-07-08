package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query("select a from Application a where a.kvota.universityCode= ?1 ")
    Page<Application> findAllApplicationByUniversity(String universityCode, Pageable pageable);

    @Query("select a from Application a where a.kvota.universityCode= ?1 and a.id= ?2")
    Optional<Application> findApplicationByUniversityAndId(String universityCode, Integer applicationId);
}

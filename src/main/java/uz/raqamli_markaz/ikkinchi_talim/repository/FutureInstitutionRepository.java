package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.classificator.FutureInstitution;

@Repository
public interface FutureInstitutionRepository extends JpaRepository<FutureInstitution, Integer> {

    @Query("select f from FutureInstitution as f where f.name  like %?1%")
    Page<FutureInstitution> searchFuturuInst(String text, Pageable pageable);
}

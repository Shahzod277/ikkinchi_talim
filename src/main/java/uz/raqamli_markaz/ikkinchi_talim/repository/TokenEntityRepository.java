package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.TokenEntity;

import java.util.Optional;

@Repository
public interface TokenEntityRepository extends JpaRepository<TokenEntity,Integer> {
    @Query("select t from TokenEntity t where t.orgName = ?1 ")
    Optional<TokenEntity> findByOrgName(String name);
}

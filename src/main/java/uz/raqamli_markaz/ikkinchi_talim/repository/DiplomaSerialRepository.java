package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.DiplomaSerial;

import java.util.Optional;

@Repository
public interface DiplomaSerialRepository extends JpaRepository<DiplomaSerial, Integer> {
    @Query("select d from DiplomaSerial d where d.serialId = ?1")
    Optional<DiplomaSerial> findDiplomaSerialBySerialId(Integer serialId);
}

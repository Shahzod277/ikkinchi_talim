package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import second.education.domain.classificator.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}

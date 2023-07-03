package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.EduForm;
import uz.raqamli_markaz.ikkinchi_talim.model.response.StatisEduFormResponse;
import java.util.List;
import java.util.Optional;

@Repository
public interface EduFormRepository extends JpaRepository<EduForm, Integer> {

    @Query(nativeQuery = true, value = "select * from edu_form ef where ef.direction_id =?1")
    List<EduForm> findAllByDirectionIdPage(Integer direction_id);

    Optional<EduForm> findByName(String name);

    @Query(nativeQuery = true, value = "select ef.id as eduFormId, ef.name as eduFormName from edu_form ef where ef.direction_id =?1 ")
    List<StatisEduFormResponse> findAllByDirectionId(Integer direction_id);
}

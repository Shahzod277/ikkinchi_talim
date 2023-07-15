package uz.raqamli_markaz.ikkinchi_talim.repository;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.Application;
import uz.raqamli_markaz.ikkinchi_talim.domain.User;
import uz.raqamli_markaz.ikkinchi_talim.model.response.AppResponseProjection;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaStatisticProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query(value = " select a.id id ,u.full_name fullName,k.speciality_name speciality,k.university_name university," +
            " a.created_date createDate from application a inner join kvota k on k.id = a.kvota_id inner join users u on u.id = a.user_id " +
            " where k.university_code=?1 and a.application_status=?2 ",nativeQuery = true)
    Page<AppResponseProjection> findAllApplicationByUniversity(String universityCode, String status, Pageable pageable);
    @Query(value = " select a.id id ,u.full_name fullName,k.speciality_name speciality,k.university_name university," +
            " a.created_date createDate from application a inner join kvota k on k.id = a.kvota_id inner join users u on u.id = a.user_id " +
          " where k.university_code=?1 and a.application_status=?2 and (CAST(a.id  AS varchar(255)) ilike %?3% or u.full_name ilike %?3%) ",nativeQuery = true)
    Page<AppResponseProjection> findAllSearchApplicationByUniversity(String universityCode, String status,String search, Pageable pageable);

    @Query("select a from Application a where a.kvota.universityCode= ?1 and a.id= ?2")
    Optional<Application> findApplicationByUniversityAndId(String universityCode, Integer applicationId);

    @Query("select a from Application a where a.user.id = ?1")
    Optional<Application> findByUserId(Integer user_id);
    @Query(nativeQuery = true,value = "select count(d.id),a.application_status from application a inner join users u on u.id = a.user_id " +
            "    inner join kvota k on k.id = a.kvota_id inner join diploma d on u.id = d.user_id " +
            " where d.is_active=true and k.university_code=?1 group by a.application_status order by a.application_status ")
    List<DiplomaStatisticProjection> appStatisticCount(String code);
}

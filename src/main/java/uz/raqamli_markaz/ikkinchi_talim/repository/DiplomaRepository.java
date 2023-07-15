package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.diploma.Diploma;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaResponseProjection;
import uz.raqamli_markaz.ikkinchi_talim.model.response.DiplomaStatisticProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiplomaRepository extends JpaRepository<Diploma, Integer> {
    @Query(nativeQuery = true, value = "select  d.id id ,d.speciality_name speciality, concat(d.diploma_serial,d.diploma_number) diplomaAndSerial ," +
            "u.full_name fullName,d.institution_old_name institutionName from application a inner join users u on u.id = a.user_id " +
            "inner join diploma d on u.id = d.user_id where d.country_id=1 and d.is_active=true and d.institution_id=?1 and a.application_status=?2")
    Page<DiplomaResponseProjection> getAllDiplomaByStatus(Integer instId, String status, Pageable pageable);

    @Query(nativeQuery = true, value = "select  d.id id ,d.speciality_name speciality, concat(d.diploma_serial,d.diploma_number) diplomaAndSerial ," +
            " u.full_name fullName,d.institution_old_name institutionName from application a inner join users u on u.id = a.user_id " +
            " inner join diploma d on u.id = d.user_id where d.country_id=1 and d.is_active=true and d.institution_id=?1 and a.application_status=?2 and " +
            " (u.full_name ilike %?3% or CAST(d.id  AS varchar(255)) ilike %?3% or concat(d.diploma_serial,d.diploma_number) ilike %?3%) ")
    Page<DiplomaResponseProjection> getAllDiplomaSearch(Integer instId, String status, String search, Pageable pageable);

    @Query(nativeQuery = true, value = "select  d.id id ,d.speciality_custom_name speciality, concat(d.diploma_serial,d.diploma_number) diplomaAndSerial ," +
            " u.full_name fullName,d.institution_old_name institutionName from application a inner join users u on u.id = a.user_id " +
            " inner join diploma d on u.id = d.user_id  inner join kvota k on k.id = a.kvota_id " +
            " where d.country_id!=1 and d.is_active=true and k.university_code=?1 and a.application_status=?2 ")
    Page<DiplomaResponseProjection> getAllForeignDiplomaByStatus(String instId, String status, Pageable pageable);

    @Query(nativeQuery = true, value = "select  d.id id ,d.speciality_custom_name speciality, concat(d.diploma_serial,d.diploma_number) diplomaAndSerial ," +
            " u.full_name fullName,d.institution_old_name institutionName from application a inner join users u on u.id = a.user_id " +
            " inner join diploma d on u.id = d.user_id  inner join kvota k on k.id = a.kvota_id " +
            " where d.country_id!=1 and d.is_active=true and k.university_code=?1 and a.application_status=?2 and " +
            " (u.full_name ilike %?3% or CAST(d.id  AS varchar(255)) ilike %?3% or concat(d.diploma_serial,d.diploma_number) ilike %?3%) ")
    Page<DiplomaResponseProjection> getAllForeignDiplomaSearch(String instId, String status, String search, Pageable pageable);

    @Query("select d from Diploma d where d.user.id = ?1 ")
    List<Diploma> findAllDiplomaByUser(Integer id);

    @Query("select d from Diploma d where d.institutionId = ?1 and d.id= ?2 and d.isActive=true ")
    Optional<Diploma> findDiplomaByInstitutionAndId(Integer institutionId, Integer diplomaId);

    @Query("select d from Diploma d inner  join User u  on d.user.id=u.id " +
            "inner join Application a on a.user.id=u.id inner join Kvota k on a.kvota.id=k.id" +
            " where k.universityCode = ?1 and d.id= ?2 ")
    Optional<Diploma> findDiplomaBykvotaUniverCodeAndId(String univerCode, Integer diplomaId);

    @Query("select d from Diploma d where d.institutionId = ?1 and d.statusName= ?2")
    Page<Diploma> findAllDiplomaByInstitution(Integer institutionId, String status, Pageable pageable);

    @Query("select d from Diploma d where d.id =?1 and d.user.id =?2 ")
    Optional<Diploma> findDiplomaByDiplomaIdAndUser(Integer id, Integer userId);

    @Query("select (count(d) > 0) from Diploma d where d.isActive = true and d.user.id=?1")
    Boolean existsDiplomaByIsActiveCount(Integer id);

    @Query("select d from Diploma d where d.diplomaId =?1 and d.isActive = true")
    Optional<Diploma> findDiplomaByDiplomaId(Integer diplomaId);

    @Query("select d from Diploma d where d.user.id =?1 and d.isActive = true")
    Optional<Diploma> findActiveDiplomaByUser(Integer id);
    //statistic

    @Query(nativeQuery = true, value = "select count(d.id) count ,a.application_status status from application a inner join users u on u.id = a.user_id " +
            " inner join diploma d on u.id = d.user_id where d.country_id=1 and d.is_active=true and d.institution_id=?1 group by a.application_status order by a.application_status ")
    List<DiplomaStatisticProjection> diplomaStatisticCount(Integer id);
    @Query(nativeQuery = true, value = "select count(d.id) count ,a.application_status status from application a inner join users u on u.id = a.user_id " +
            " inner join kvota k on k.id = a.kvota_id inner join diploma d on u.id = d.user_id " +
            " where d.country_id!=1 and d.is_active=true and k.university_code=?1 group by a.application_status order by a.application_status ")
    List<DiplomaStatisticProjection> diplomaForeignStatisticCount(String count);


}

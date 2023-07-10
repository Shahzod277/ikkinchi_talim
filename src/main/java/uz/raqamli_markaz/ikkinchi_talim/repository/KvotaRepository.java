package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.raqamli_markaz.ikkinchi_talim.domain.Kvota;
import uz.raqamli_markaz.ikkinchi_talim.model.response.OtmProjection;

import java.util.List;
import java.util.Map;

public interface KvotaRepository extends JpaRepository<Kvota, Integer> {
    @Query(nativeQuery = true, value = "select distinct(k.university_code) otmCode ,k.university_name otmName  from kvota  k order by k.university_name ")
    List<OtmProjection> getOtmByKvota();

    @Query(nativeQuery = true, value = "select distinct(k.edu_form_code) eduFormCode ,k.edu_form_name eduFormName,k.university_code otmCode  from kvota k where k.university_code=?1")
   List <OtmProjection> getEduFormByOtmCode(String code);
    @Query(nativeQuery = true, value = "select distinct(k.language_code) languageCode ,k.language_name languageName ,k.edu_form_code eduFormCode, k.university_code otmCode from kvota k where k.edu_form_code=?1 and  k.university_code=?2")
   List <OtmProjection> getLanguageByOtmCodeAndEduFormCode(String code,String otmCode);
    @Query(nativeQuery = true, value = "select k.id id, k.speciality_code specialityCode ,k.speciality_name specialityName ,k.language_code languageCode ,k.language_name languageName ,k.edu_form_code eduFormCode, k.university_code otmCode  from kvota k where k.language_code=?1 and k.edu_form_code=?2 and  k.university_code=?3")
    List <OtmProjection> getSpeciality(String languageCode,String code,String otmCode);
}

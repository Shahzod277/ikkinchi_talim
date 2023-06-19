package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.Document;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findAllByDiplomaId(Integer diploma_id);

    @Query("select d from Document as d where d.diploma.enrolleeInfo.id=?1")
    List<Document> getAllDocumentByEnrollId(Integer enrollId);
}

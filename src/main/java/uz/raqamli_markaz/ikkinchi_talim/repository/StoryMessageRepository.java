package uz.raqamli_markaz.ikkinchi_talim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.raqamli_markaz.ikkinchi_talim.domain.StoryMessage;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoryMessageRepository extends JpaRepository<StoryMessage, Integer> {

}

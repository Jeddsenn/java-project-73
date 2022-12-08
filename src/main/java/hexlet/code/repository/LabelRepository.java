package hexlet.code.repository;

import hexlet.code.model.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, Long> {

    Optional<LabelEntity> findByName(String name);

}

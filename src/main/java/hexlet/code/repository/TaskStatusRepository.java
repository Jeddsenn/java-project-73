package hexlet.code.repository;

import hexlet.code.model.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusEntity, Long> {
    Optional<TaskStatusEntity> findByName(String str);
}

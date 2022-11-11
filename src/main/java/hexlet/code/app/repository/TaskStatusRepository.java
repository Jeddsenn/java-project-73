package hexlet.code.app.repository;

import hexlet.code.app.model.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskStatusRepository extends CrudRepository<TaskStatus, Long> {
    @Override
    List<TaskStatus> findAll();

    Optional<TaskStatus> findByName(String str);
}

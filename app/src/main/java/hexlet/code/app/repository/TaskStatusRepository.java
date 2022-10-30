package hexlet.code.app.repository;

import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskStatusRepository extends CrudRepository<TaskStatus, Long> {



    @Override
    List<TaskStatus> findAll();
}

package hexlet.code.service;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatusEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface TaskStatusService {

    TaskStatusEntity createTaskStatus(TaskStatusDto taskStatusDto);
    TaskStatusEntity updateTaskStatus(TaskStatusDto taskStatusDto, long id);
    Optional<TaskStatusEntity> getTaskStatus(long id);
    List<TaskStatusEntity> getAll();
    void deleteStatus(long id);

}

package hexlet.code.service;

import hexlet.code.dto.request.ReqTaskStatusDto;
import hexlet.code.model.TaskStatusEntity;

import java.util.List;
import java.util.Optional;

public interface TaskStatusService {

    TaskStatusEntity createTaskStatus(ReqTaskStatusDto taskStatusDto);
    TaskStatusEntity updateTaskStatus(ReqTaskStatusDto taskStatusDto, long id);
    Optional<TaskStatusEntity> getTaskStatus(long id);
    List<TaskStatusEntity> getAll();
    void deleteStatus(long id);

}

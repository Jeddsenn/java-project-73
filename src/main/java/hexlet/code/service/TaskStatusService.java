package hexlet.code.service;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatusEntity;

public interface TaskStatusService {

    TaskStatusEntity createTaskStatus(TaskStatusDto taskStatusDto);
    TaskStatusEntity updateTaskStatus(TaskStatusDto taskStatusDto, long id);

}

package hexlet.code.service;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;

public interface TaskStatusService {

    TaskStatus createTaskStatus(TaskStatusDto taskStatusDto);
    TaskStatus updateTaskStatus(TaskStatusDto taskStatusDto, long id);

}

package hexlet.code.service;


import hexlet.code.dto.TaskDto;
import hexlet.code.model.TaskEntity;

public interface TaskService {

    TaskEntity createNewTask(TaskDto taskDto);

    TaskEntity updateTask(TaskDto taskDto, long id);
}

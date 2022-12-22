package hexlet.code.service;


import com.querydsl.core.types.Predicate;
import hexlet.code.dto.request.TaskReq;
import hexlet.code.model.TaskEntity;

public interface TaskService {

    TaskEntity createNewTask(TaskReq taskDto);

    TaskEntity updateTask(TaskReq taskDto, long id);
    TaskEntity getTask(long id);
    Iterable<TaskEntity> getAllTasks(Predicate predicate);
    void deleteTask(long id);
}

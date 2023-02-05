package hexlet.code.service;


import com.querydsl.core.types.Predicate;
import hexlet.code.dto.request.TaskReq;
import hexlet.code.dto.response.TaskRes;
import hexlet.code.model.TaskEntity;

public interface TaskService {

    TaskRes createNewTask(TaskReq taskDto);

    TaskRes updateTask(TaskReq taskDto, long id);
    TaskRes getTask(long id);
    Iterable<TaskEntity> getAllTasks(Predicate predicate);
    void deleteTask(long id);
}

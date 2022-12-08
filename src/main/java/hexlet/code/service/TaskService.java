package hexlet.code.service;


import com.querydsl.core.types.Predicate;
import hexlet.code.dto.TaskDto;
import hexlet.code.model.TaskEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface TaskService {

    TaskEntity createNewTask(TaskDto taskDto);

    TaskEntity updateTask(TaskDto taskDto, long id);
    TaskEntity getTask(long id);
    Iterable<TaskEntity> getAllTasks(Predicate predicate);
    void deleteTask(long id);
}

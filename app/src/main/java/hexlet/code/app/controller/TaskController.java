package hexlet.code.app.controller;


import com.querydsl.core.types.Predicate;
import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static hexlet.code.app.controller.TaskController.TASK_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;


@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_CONTROLLER_PATH)
public class TaskController {
    TaskRepository taskRepository;
    TaskService taskService;

    public static final String TASK_CONTROLLER_PATH = "/tasks";
    public static final String ID = "/{id}";

    @GetMapping(ID)
    public Task getTask(@PathVariable long id){
        return taskRepository.findById(id).get();
    }

    @GetMapping("")
    public List<Task> getAllTasks(@QuerydslPredicate final Predicate predicate) {
        return predicate == null ? taskRepository.findAll() : taskRepository.findAll(predicate);
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public Task createTask(@RequestBody TaskDto taskDto){
        return taskService.createNewTask(taskDto);
    }

    @PutMapping(ID)
    public Task updateTask(@RequestBody TaskDto taskDto, @PathVariable long id){
        return taskService.updateTask(taskDto, id);
    }

    @DeleteMapping(ID)
    public void deleteTask(@PathVariable long id){
        taskRepository.deleteById(id);
    }
}

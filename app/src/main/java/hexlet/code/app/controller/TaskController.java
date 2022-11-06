package hexlet.code.app.controller;


import hexlet.code.app.dto.TaskDto;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
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

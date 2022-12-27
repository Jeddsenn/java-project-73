package hexlet.code.controller;


import com.querydsl.core.types.Predicate;
import hexlet.code.dto.request.TaskReq;
import hexlet.code.dto.response.TaskRes;
import hexlet.code.model.TaskEntity;
import hexlet.code.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import static org.springframework.http.HttpStatus.CREATED;


@AllArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private TaskService taskService;


    private static final String ONLY_TASK_OWNER =
            "@taskRepository.findById(#id).get().getAuthor().getEmail() == authentication.getName()";


    @Operation(summary = "Get a task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task was found"),
            @ApiResponse(responseCode = "404", description = "Task with this id wasn`t found")
    })
    @GetMapping("/{id}")
    public TaskEntity getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }


    /// СПРОСИТЬ ЛУЧШЕ ИТЕРЕЙБЛ ИЛИ ЛИСТ ЭНД УАЙ СОУ/ и там дальеш про контейнера тто же вопрос
    @Operation(summary = "Get all tasks if no filtration is set."
            + " Else Retrieves all the elements that match the conditions defined by the specified predicate ")
    @ApiResponses(@ApiResponse(responseCode = "200"))
    @GetMapping
    public Iterable<TaskEntity> getAllTasks(@QuerydslPredicate final Predicate predicate) {
        return taskService.getAllTasks(predicate);
    }

    @Operation(summary = "Create a new task")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Task was created"))
    @PostMapping
    @ResponseStatus(CREATED)
    public TaskRes createTask(@RequestBody @Valid TaskReq taskDto) {
        return taskService.createNewTask(taskDto);
    }

    @Operation(summary = "Update a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task has been updated"),
            @ApiResponse(responseCode = "404", description = "Task with this id wasn`t found")
    })
    @PreAuthorize(ONLY_TASK_OWNER)
    @PutMapping("/{id}")
    public TaskRes updateTask(@RequestBody @Valid TaskReq taskDto, @PathVariable long id) {
        return taskService.updateTask(taskDto, id);
    }

    @Operation(summary = "Delete task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task has been deleted"),
            @ApiResponse(responseCode = "404", description = "Task with this id wasn`t found")
    })
    @PreAuthorize(ONLY_TASK_OWNER)
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);
    }
}

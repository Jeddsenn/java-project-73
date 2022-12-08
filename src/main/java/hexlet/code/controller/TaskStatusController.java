package hexlet.code.controller;


import hexlet.code.model.TaskStatusEntity;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.TaskStatusService;
import hexlet.code.dto.TaskStatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
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
import java.util.List;
import java.util.Optional;
import static org.springframework.http.HttpStatus.CREATED;


@AllArgsConstructor
@RestController
@RequestMapping("/api" + "/statuses")
public class TaskStatusController {

    private TaskStatusRepository taskStatusRepository;
    private TaskStatusService taskStatusService;



    @Operation(summary = "Get a task status by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status was found"),
            @ApiResponse(responseCode = "404", description = "Task status with this id wasn`t found")
    })
    @GetMapping("/{id}")
    public Optional<TaskStatusEntity> getTaskStatus(@PathVariable long id) {
        return taskStatusService.getTaskStatus(id);
    }

    @Operation(summary = "Get all statuses")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public List<TaskStatusEntity> getAll() {
        return taskStatusService.getAll();
    }

    @Operation(summary = "Create a new task status")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(CREATED)
    @PostMapping
    public TaskStatusEntity createStatus(@RequestBody @Valid TaskStatusDto dto) {
        return taskStatusService.createTaskStatus(dto);
    }

    @Operation(summary = "Update task status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task status has been updated"),
            @ApiResponse(responseCode = "404", description = "Task status with this id wasn`t found")
    })
    @PutMapping("/{id}")
    public TaskStatusEntity updateStatus(@RequestBody @Valid TaskStatusDto dto,
                                         @PathVariable long id) {
        return taskStatusService.updateTaskStatus(dto, id);
    }

    @Operation(summary = "Delete a task status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task status has been deleted"),
            @ApiResponse(responseCode = "404", description = "Task status with this id wasn`t found")
    })
    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable long id) {
        taskStatusService.deleteStatus(id);
    }
}

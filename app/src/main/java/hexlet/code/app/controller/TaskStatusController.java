package hexlet.code.app.controller;


import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.service.TaskStatusService;
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

import static hexlet.code.app.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;


@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_STATUS_CONTROLLER_PATH)
public class TaskStatusController {

    private TaskStatusRepository taskStatusRepository;
    private TaskStatusService taskStatusService;

    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";
    public static final String ID = "/{id}";


    @GetMapping(ID)
    public Optional<TaskStatus> getTaskStatus(@PathVariable long id) {
        return taskStatusRepository.findById(id);
    }

    @GetMapping("")
    public List<TaskStatus> getAll() {
        return taskStatusRepository
                .findAll()
                .stream()
                .toList();
    }

    @ResponseStatus(CREATED)
    @PostMapping("")
    public TaskStatus createStatus(@RequestBody TaskStatusDto dto) {
        return taskStatusService.createTaskStatus(dto);
    }

    @PutMapping(ID)
    public TaskStatus updateStatus(@RequestBody @Valid TaskStatusDto dto,
                                   @PathVariable long id) {
        return taskStatusService.updateTaskStatus(dto, id);
    }

    @DeleteMapping(ID)
    public void deleteStatus(@PathVariable long id) {
        taskStatusRepository.deleteById(id);
    }

}

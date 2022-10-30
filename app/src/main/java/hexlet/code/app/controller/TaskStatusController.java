package hexlet.code.app.controller;


import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.service.TaskStatusService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static hexlet.code.app.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;


@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + TASK_STATUS_CONTROLLER_PATH)
public class TaskStatusController {

    TaskStatusRepository taskStatusRepository;
    TaskStatusService taskStatusService;

    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";
    public static final String ID = "/{id}";


    @GetMapping(ID)
    public Optional<TaskStatus> getTaskStatus(@PathVariable long id){
        return taskStatusRepository.findById(id);
    }

    @GetMapping("")
    public List<TaskStatus> getAll(){
        return taskStatusRepository
                .findAll()
                .stream()
                .toList();
    }

    @PostMapping("")
    public TaskStatus createStatus(TaskStatusDto dto){
        return taskStatusService.createTaskStatus(dto);
    }

    @PutMapping(ID)
    public TaskStatus updateStatus(TaskStatusDto dto, @PathVariable long id){
        return taskStatusService.updateTaskStatus(dto, id);
    }

    @DeleteMapping(ID)
    public void deleteStatus(@PathVariable long id){
        taskStatusRepository.deleteById(id);
    }

}

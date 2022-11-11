package hexlet.code.service;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public Task createNewTask(TaskDto taskDto) {
        final Task newTask = buildTaskFromDto(taskDto);
        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTask(TaskDto taskDto, long id) {
        final Task taskToUpdate = buildTaskFromDto(taskDto);
        taskToUpdate.setId(id);
        return taskRepository.save(taskToUpdate);
    }

    private Task buildTaskFromDto(final TaskDto taskDto) {
        User author = userService.getCurrentUser();
        User executor = Optional.ofNullable(taskDto.getExecutorId())
                .map(User::new)
                .orElse(null);
        TaskStatus taskStatus = Optional.ofNullable(taskDto.getTaskStatusId())
                .map(TaskStatus::new)
                .orElse(null);

        return Task.builder()
                .author(author)
                .executor(executor)
                .taskStatus(taskStatus)
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .build();
    }
}

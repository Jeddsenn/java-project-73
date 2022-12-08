package hexlet.code.service;

import hexlet.code.model.LabelEntity;
import hexlet.code.model.TaskEntity;
import hexlet.code.model.TaskStatusEntity;
import hexlet.code.model.UserEntity;
import hexlet.code.dto.TaskDto;
import hexlet.code.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskEntity createNewTask(TaskDto taskDto) {
        final TaskEntity newTask = fromDto(taskDto);
        return taskRepository.save(newTask);
    }

    @Override
    public TaskEntity updateTask(TaskDto taskDto, long id) {
        final TaskEntity taskToUpdate = fromDto(taskDto);
        taskToUpdate.setId(id);
        return taskRepository.save(taskToUpdate);
    }

    private TaskEntity fromDto(final TaskDto dto) {
        final UserEntity author = userService.getCurrentUser();
        final UserEntity executor = Optional.ofNullable(dto.executorId())
                .map(UserEntity::new)
                .orElse(null);
        final TaskStatusEntity taskStatus = Optional.ofNullable(dto.taskStatusId())
                .map(TaskStatusEntity::new)
                .orElse(null);
        final Set<LabelEntity> labels = Optional.ofNullable(dto.labelIds())
                .orElse(Set.of())
                .stream()
                .filter(Objects::nonNull)
                .map(LabelEntity::new)
                .collect(Collectors.toSet());

        return TaskEntity.builder()
                .author(author)
                .executor(executor)
                .taskStatus(taskStatus)
                .labels(labels)
                .name(dto.name())
                .description(dto.description())
                .build();
    }
}

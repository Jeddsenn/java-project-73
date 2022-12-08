package hexlet.code.service;

import com.querydsl.core.types.Predicate;
import hexlet.code.model.LabelEntity;
import hexlet.code.model.TaskEntity;
import hexlet.code.model.TaskStatusEntity;
import hexlet.code.model.UserEntity;
import hexlet.code.dto.request.ReqTaskDto;
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
    public TaskEntity createNewTask(ReqTaskDto taskDto) {
        final TaskEntity newTask = toUserDto(taskDto);
        return taskRepository.save(newTask);
    }

    @Override
    public TaskEntity updateTask(ReqTaskDto taskDto, long id) {
        final TaskEntity taskToUpdate = toUserDto(taskDto);
        taskToUpdate.setId(id);
        return taskRepository.save(taskToUpdate);
    }

    @Override
    public TaskEntity getTask(long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Iterable<TaskEntity> getAllTasks(Predicate predicate) {
        return predicate == null ? taskRepository.findAll() : taskRepository.findAll(predicate);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }


    private TaskEntity toUserDto(final ReqTaskDto dto) {
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

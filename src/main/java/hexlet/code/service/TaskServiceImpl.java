package hexlet.code.service;

import com.querydsl.core.types.Predicate;
import hexlet.code.dto.response.TaskRes;
import hexlet.code.model.LabelEntity;
import hexlet.code.model.TaskEntity;
import hexlet.code.model.TaskStatusEntity;
import hexlet.code.model.UserEntity;
import hexlet.code.dto.request.TaskReq;
import hexlet.code.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
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
    public TaskRes createNewTask(TaskReq taskDto) {
        final TaskEntity newTask = toUserDto(taskDto);
        taskRepository.save(newTask);

        return new TaskRes(newTask.getId(), newTask.getName(), newTask.getDescription(),
                newTask.getTaskStatus().getId(), newTask.getExecutor().getId(), newTask.getLabels());
    }

    @Override
    public TaskRes updateTask(TaskReq taskDto, long id) {
        final TaskEntity taskToUpdate = toUserDto(taskDto);
        taskToUpdate.setId(id);
        taskRepository.save(taskToUpdate);
        return new TaskRes(taskToUpdate.getId(), taskToUpdate.getName(), taskToUpdate.getDescription(),
                taskToUpdate.getTaskStatus().getId(), taskToUpdate.getExecutor().getId(), taskToUpdate.getLabels());

    }

    public TaskRes getTask(long id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow();
        return new TaskRes(task.getId(), task.getName(), task.getDescription(),
                task.getTaskStatus().getId(), task.getExecutor().getId(), new HashSet<>(task.getLabels()));
    }

    @Override
    public Iterable<TaskEntity> getAllTasks(Predicate predicate) {
        if (predicate == null) {
            return taskRepository.findAll();
        } else {
            return taskRepository.findAll(predicate);
        }
    }

    /*@Override
    public Iterable<TaskRes> getAllTasks(Predicate predicate) {
        if (predicate == null) {
            return taskRepository.findAll().stream()
                    .map(taskEntity -> new TaskRes(taskEntity.getId(), taskEntity.getName(),
                            taskEntity.getDescription(), taskEntity.getTaskStatus().getId(),
                            taskEntity.getExecutor().getId(), new HashSet<>(taskEntity.getLabels())))
                    .collect(Collectors.toList());
        } else {
            List<TaskEntity> taskEntities = StreamSupport.stream(taskRepository.findAll(predicate).spliterator(), false)
                    .collect(Collectors.toList());
            return taskEntities.stream().map(taskEntity -> new TaskRes(taskEntity.getId(), taskEntity.getName(),
                            taskEntity.getDescription(), taskEntity.getTaskStatus().getId(),
                            taskEntity.getExecutor().getId(), new HashSet<>(taskEntity.getLabels())))
                    .collect(Collectors.toList());
        }
    }*/

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }


    private TaskEntity toUserDto(final TaskReq dto) {
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

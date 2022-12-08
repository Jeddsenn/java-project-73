package hexlet.code.service;

import hexlet.code.model.TaskStatusEntity;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.dto.TaskStatusDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {
    private TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatusEntity createTaskStatus(final TaskStatusDto dto) {
        final TaskStatusEntity taskStatus = new TaskStatusEntity();
        taskStatus.setName(dto.name());
        return taskStatusRepository.save(taskStatus);
    }

    @Override
    public TaskStatusEntity updateTaskStatus(TaskStatusDto taskStatusDto, long id) {
        TaskStatusEntity statusDto = taskStatusRepository.findById(id).get();
        statusDto.setName(taskStatusDto.name());
        return taskStatusRepository.save(statusDto);
    }

    @Override
    public Optional<TaskStatusEntity> getTaskStatus(long id) {
        return taskStatusRepository.findById(id);
    }

    @Override
    public List<TaskStatusEntity> getAll() {
        return taskStatusRepository
                .findAll()
                .stream()
                .toList();
    }

    @Override
    public void deleteStatus(long id) {
        taskStatusRepository.deleteById(id);
    }
}

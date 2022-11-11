package hexlet.code.service;

import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {
    private TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatus createTaskStatus(final TaskStatusDto dto) {
        final TaskStatus taskStatus = new TaskStatus();
        taskStatus.setName(dto.getName());
        return taskStatusRepository.save(taskStatus);
    }

    @Override
    public TaskStatus updateTaskStatus(TaskStatusDto taskStatusDto, long id) {
        TaskStatus statusDto = taskStatusRepository.findById(id).get();
        statusDto.setName(taskStatusDto.getName());
        return taskStatusRepository.save(statusDto);
    }
}

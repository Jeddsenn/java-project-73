package hexlet.code.service;

import hexlet.code.dto.response.TaskStatusRes;
import hexlet.code.model.TaskStatusEntity;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.dto.request.TaskStatusReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {
    private TaskStatusRepository taskStatusRepository;

    @Override
    public TaskStatusRes createTaskStatus(final TaskStatusReq dto) {
        final TaskStatusEntity taskStatus = new TaskStatusEntity();
        taskStatus.setName(dto.name());
        TaskStatusEntity savedTaskStatus = taskStatusRepository.save(taskStatus);

        return new TaskStatusRes(savedTaskStatus.getName());
    }

    @Override
    public TaskStatusRes updateTaskStatus(TaskStatusReq taskStatusDto, long id) {
        TaskStatusEntity statusDto = taskStatusRepository.findById(id).orElseThrow();
        statusDto.setName(taskStatusDto.name());
        final TaskStatusEntity updatedStatus = taskStatusRepository.save(statusDto);
        return new TaskStatusRes(updatedStatus.getName());
    }


    @Override
    public TaskStatusRes getTaskStatus(long id) {
        TaskStatusEntity taskStatus = taskStatusRepository.findById(id).orElseThrow();
        return new TaskStatusRes(taskStatus.getName());
    }

    public List<TaskStatusRes> getAll() {
        return taskStatusRepository
                .findAll()
                .stream()
                .map(status -> new TaskStatusRes(status.getName()))
                .toList();
    }


    @Override
    public void deleteStatus(long id) {
        taskStatusRepository.deleteById(id);
    }
}

package hexlet.code.service;

import hexlet.code.dto.request.TaskStatusReq;
import hexlet.code.dto.response.TaskStatusRes;
import java.util.List;

public interface TaskStatusService {

    TaskStatusRes createTaskStatus(TaskStatusReq taskStatusDto);
    TaskStatusRes updateTaskStatus(TaskStatusReq taskStatusDto, long id);
    TaskStatusRes getTaskStatus(long id);
    List<TaskStatusRes> getAll();
    void deleteStatus(long id);

}

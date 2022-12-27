package hexlet.code.dto.response;


import hexlet.code.model.LabelEntity;
import java.util.Set;


public record TaskRes(
        Long id,
        String name,
        String description,
        Long taskStatusId,
        Long executorId,
        Set<LabelEntity> labelIds) {
}

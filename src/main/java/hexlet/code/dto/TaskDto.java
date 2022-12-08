package hexlet.code.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


public record
TaskDto (
        @NotBlank
        String name,
        String description,
        @NotNull
        Long taskStatusId,
        Long executorId,
        Set<Long> labelIds) {

}
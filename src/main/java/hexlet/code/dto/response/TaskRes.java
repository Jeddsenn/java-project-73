package hexlet.code.dto.response;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


public record TaskRes(
        @NotBlank
        String name,
        String description,
        @NotNull
        Long taskStatusId,
        Long executorId,
        Set<Long> labelIds) {
}

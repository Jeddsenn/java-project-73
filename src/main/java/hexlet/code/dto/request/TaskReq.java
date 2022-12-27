package hexlet.code.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


public record TaskReq(
        @NotBlank
        String name,
        String description,
        @NotNull
        Long taskStatusId,
        Long executorId,
        Set<Long> labelIds) {
}

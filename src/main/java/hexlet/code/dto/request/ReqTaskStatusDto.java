package hexlet.code.dto.request;

import javax.validation.constraints.NotBlank;


public record
ReqTaskStatusDto(
        @NotBlank
        String name) {
}

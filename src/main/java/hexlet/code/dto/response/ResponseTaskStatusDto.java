package hexlet.code.dto.response;

import javax.validation.constraints.NotBlank;


public record
ResponseTaskStatusDto(
        @NotBlank
        String name) {
}

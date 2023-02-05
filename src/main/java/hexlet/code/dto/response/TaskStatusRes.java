package hexlet.code.dto.response;

import javax.validation.constraints.NotBlank;


public record TaskStatusRes(
        @NotBlank
        String name) {
}

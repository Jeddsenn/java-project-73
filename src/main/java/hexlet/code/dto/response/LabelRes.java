package hexlet.code.dto.response;

import javax.validation.constraints.NotBlank;


public record LabelRes(
        Long id,
        @NotBlank
        String name) {
}


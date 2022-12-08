package hexlet.code.dto.response;

import javax.validation.constraints.NotBlank;


public record
ResponseLabelDto(
        @NotBlank
        String name) {
}


package hexlet.code.dto.request;

import javax.validation.constraints.NotBlank;


public record
ReqLabelDto(
        @NotBlank
        String name) {
}


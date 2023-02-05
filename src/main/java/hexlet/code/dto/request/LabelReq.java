package hexlet.code.dto.request;

import javax.validation.constraints.NotBlank;


public record LabelReq(
        @NotBlank
        String name) {
}


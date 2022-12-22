package hexlet.code.dto.request;

import javax.validation.constraints.NotBlank;


public record TaskStatusReq(
        @NotBlank
        String name) {
}

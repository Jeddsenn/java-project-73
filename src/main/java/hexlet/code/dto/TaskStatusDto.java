package hexlet.code.dto;

import javax.validation.constraints.NotBlank;


public record
TaskStatusDto (
        @NotBlank
        String name) {

}

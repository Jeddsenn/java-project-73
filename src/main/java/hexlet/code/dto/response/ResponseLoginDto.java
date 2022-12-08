package hexlet.code.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public record
ResponseLoginDto(
        String firstName,

        String lastName,
        @Email
        @NotBlank
        String name,
        @NotBlank
        @Size(min = 3, max = 100)
        String password) {
}

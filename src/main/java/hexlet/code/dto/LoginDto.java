package hexlet.code.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public record
LoginDto (
        String firstName,

        String lastName,
        @Email
        @NotBlank
        String name,
        @NotBlank
        @Size(min = 3, max = 100)
        String password) {

}
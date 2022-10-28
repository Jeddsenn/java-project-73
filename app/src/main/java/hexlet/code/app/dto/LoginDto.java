package hexlet.code.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 3, max = 100)
    private String password;

}

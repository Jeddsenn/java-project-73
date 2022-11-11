package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelDto {

    @NotBlank
    private String name;

}

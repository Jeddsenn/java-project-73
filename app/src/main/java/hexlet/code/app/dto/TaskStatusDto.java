package hexlet.code.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private Date createdAt;


}

package hexlet.code.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {


    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long taskStatusId;

    private Long executorId;

    private Set<Long> labelIds;
}

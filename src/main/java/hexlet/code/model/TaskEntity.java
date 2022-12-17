package hexlet.code.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    private String description;


    @ManyToOne
    @JoinColumn(name = "task_status_id")
    private TaskStatusEntity taskStatus;


    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull
    private UserEntity author;


    @ManyToOne
    @JoinColumn(name = "executor_id")
    private UserEntity executor;

    @CreationTimestamp
    private Instant createdAt;

    @ManyToMany
    private Set<LabelEntity> labels;

    public TaskEntity(Long id) {
        this.id = id;
    }
}

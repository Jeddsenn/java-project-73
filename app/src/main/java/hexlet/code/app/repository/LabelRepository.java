package hexlet.code.app.repository;

import hexlet.code.app.model.Label;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {

    Optional<Label> findByName(String name);
    List<Label> findAll();
}

package hexlet.code.app.repository;

import hexlet.code.app.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);
    Iterable<User> findAll();

    Optional<User> findByEmail(String email);


}

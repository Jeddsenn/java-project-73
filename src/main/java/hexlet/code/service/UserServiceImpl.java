package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.model.UserEntity;
import hexlet.code.repository.UserRepository;
import hexlet.code.config.SecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity createNewUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        return userRepository.save(user);
    }

    @Override
    public UserEntity updateUser(long id, UserDto userDto) {
        final UserEntity userToUpdate = userRepository.findById(id).get();

        userToUpdate.setFirstName(userDto.firstName());
        userToUpdate.setLastName(userDto.lastName());
        userToUpdate.setEmail(userDto.email());
        userToUpdate.setPassword(passwordEncoder.encode(userDto.password()));
        return userRepository.save(userToUpdate);
    }

    @Override
    public Optional<UserEntity> getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll()
                .stream()
                .toList();    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);

    }

    @Override
    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public UserEntity getCurrentUser() {
        return userRepository.findByEmail(getCurrentUserName()).get();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::buildSpringUser)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with 'username': " + username));
    }

    private UserDetails buildSpringUser(final UserEntity user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                SecurityConfig.DEFAULT_AUTHORITIES
        );
    }

}


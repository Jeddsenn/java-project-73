package hexlet.code.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.app.dto.LoginDto;
import hexlet.code.app.dto.UserDto;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.security.SecurityConfig;
import hexlet.code.app.config.SpringConfigForIT;
import hexlet.code.app.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static hexlet.code.app.utils.TestUtils.BASE_URL;
import static hexlet.code.app.utils.TestUtils.TEST_USERNAME;
import static hexlet.code.app.utils.TestUtils.TEST_USERNAME1;
import static hexlet.code.app.utils.TestUtils.asJson;
import static hexlet.code.app.utils.TestUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(SpringConfigForIT.TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public final class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestUtils utils;

    @BeforeEach
    public void clear() {
        utils.tearDown();
    }

    @Test
    public void registration() throws Exception {
        assertEquals(0, userRepository.count());
        utils.regDefaultUser().andExpect(status().isCreated());
        assertEquals(1, userRepository.count());
    }

    @Test
    public void getUserById() throws Exception {
        utils.regDefaultUser();
        final User expectedUser = userRepository.findAll().get(0);
        final var response = utils.perform(
                        MockMvcRequestBuilders
                                .get(BASE_URL + UserController.USER_CONTROLLER_PATH + UserController.ID,
                                        expectedUser.getId()),
                        expectedUser.getEmail()
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final User user = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
        assertEquals(expectedUser.getLastName(), user.getLastName());
    }

    @Disabled("For now active only positive tests")
    @Test
    public void getUserByIdFails() throws Exception {
        utils.regDefaultUser();
        final User expectedUser = userRepository.findAll().get(0);
        utils.perform(MockMvcRequestBuilders
                        .get(BASE_URL + UserController.USER_CONTROLLER_PATH + UserController.ID,
                                expectedUser.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllUsers() throws Exception {
        utils.regDefaultUser();
        final var response
                = utils.perform(MockMvcRequestBuilders
                        .get(BASE_URL + UserController.USER_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<User> users = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(users).hasSize(1);
    }

    @Test
    public void twiceRegTheSameUserFail() throws Exception {
        utils.regDefaultUser().andExpect(status().isCreated());
        utils.regDefaultUser().andExpect(status().isUnprocessableEntity());

        assertEquals(1, userRepository.count());
    }

    @Test
    public void login() throws Exception {
        utils.regDefaultUser();
        final LoginDto loginDto = new LoginDto(
                utils.getTestRegistrationDto().getFirstName(),
                utils.getTestRegistrationDto().getLastName(),
                utils.getTestRegistrationDto().getEmail(),
                utils.getTestRegistrationDto().getPassword()
        );
        final var loginRequest =
                MockMvcRequestBuilders.post(BASE_URL + SecurityConfig.LOGIN).content(asJson(loginDto))
                        .contentType(APPLICATION_JSON);
        utils.perform(loginRequest).andExpect(status().isOk());
    }

    @Test
    public void loginFail() throws Exception {
        final LoginDto loginDto = new LoginDto(
                utils.getTestRegistrationDto().getFirstName(),
                utils.getTestRegistrationDto().getLastName(),
                utils.getTestRegistrationDto().getEmail(),
                utils.getTestRegistrationDto().getPassword()
        );
        final var loginRequest =
                MockMvcRequestBuilders.post(BASE_URL + SecurityConfig.LOGIN).content(asJson(loginDto))
                        .contentType(APPLICATION_JSON);
        utils.perform(loginRequest).andExpect(status().isUnauthorized());
    }

    @Test
    public void updateUser() throws Exception {
        utils.regDefaultUser();
        final Long userId = userRepository.findByEmail(TEST_USERNAME).get().getId();
        final var userDto = new UserDto(
                TEST_USERNAME1, "new name", "new last name", "new pwd");
        final var updateRequest =
                MockMvcRequestBuilders
                        .put(BASE_URL + UserController.USER_CONTROLLER_PATH + UserController.ID,
                                userId)
                        .content(asJson(userDto))
                        .contentType(APPLICATION_JSON);

        utils.perform(updateRequest, TEST_USERNAME).andExpect(status().isOk());
        assertTrue(userRepository.existsById(userId));
        assertNull(userRepository.findByEmail(TEST_USERNAME).orElse(null));
        assertNotNull(userRepository.findByEmail(TEST_USERNAME1).orElse(null));
    }

    @Test
    public void deleteUser() throws Exception {
        utils.regDefaultUser();
        final Long userId = userRepository.findByEmail(TEST_USERNAME).get().getId();

        utils.perform(delete(BASE_URL + UserController.USER_CONTROLLER_PATH + UserController.ID, userId),
                        TEST_USERNAME)
                .andExpect(status().isOk());
        assertEquals(0, userRepository.count());
    }

    @Test
    public void deleteUserFails() throws Exception {
        utils.regDefaultUser();
        utils.regUser(new UserDto(
                TEST_USERNAME1,
                "fname",
                "lname",
                "pwd"
        ));
        final Long userId = userRepository.findByEmail(TEST_USERNAME).get().getId();

        utils
                .perform(MockMvcRequestBuilders
                        .delete(BASE_URL + UserController.USER_CONTROLLER_PATH + UserController.ID, userId),
                        TEST_USERNAME1)
                .andExpect(status().isForbidden());
        assertEquals(2, userRepository.count());
    }

}


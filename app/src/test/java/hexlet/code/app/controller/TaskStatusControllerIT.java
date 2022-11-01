package hexlet.code.app.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.app.config.SpringConfigForIT;
import hexlet.code.app.dto.TaskStatusDto;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static hexlet.code.app.controller.TaskStatusController.ID;
import static hexlet.code.app.controller.TaskStatusController.TASK_STATUS_CONTROLLER_PATH;
import static hexlet.code.app.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(SpringConfigForIT.TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public class TaskStatusControllerIT {


    @Autowired
    private TaskStatusRepository taskStatusRepository;

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
        assertEquals(0, taskStatusRepository.count());
        utils.regDefaultUser();
        utils.regDefaultStatus(TEST_USERNAME).andExpect(status().isCreated());
        assertEquals(1, taskStatusRepository.count());
    }

    @Test
    public void deleteStatus() throws Exception {
        utils.regDefaultUser();
        utils.regDefaultStatus(TEST_USERNAME);
        final Long statusId = taskStatusRepository.findAll().get(0).getId();
        assertEquals(1, taskStatusRepository.count());

        utils.perform(delete(BASE_URL + TASK_STATUS_CONTROLLER_PATH + ID, statusId), TEST_USERNAME)
                .andExpect(status().isOk());

        assertEquals(0, taskStatusRepository.count());
    }

    @Test
    public void getTaskStatusById() throws Exception {
        utils.regDefaultUser();
        utils.regDefaultStatus(TEST_USERNAME).andExpect(status().isCreated());
        assertEquals(1, taskStatusRepository.count());

        final TaskStatus expectedStatus = taskStatusRepository.findAll().get(0);

        final var response = utils
                .perform(get(BASE_URL + TASK_STATUS_CONTROLLER_PATH + ID, expectedStatus.getId()), TEST_USERNAME)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        final TaskStatus status = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedStatus.getId(), status.getId());
        assertEquals(expectedStatus.getName(), status.getName());
    }

    @Test
    public void getAllTaskStatuses() throws Exception {
        utils.regDefaultUser();
        utils.regDefaultStatus(TEST_USERNAME).andExpect(status().isCreated());
        utils.regDefaultStatus(TEST_USERNAME).andExpect(status().isCreated());
        assertEquals(2, taskStatusRepository.count());

        final var response = utils
                .perform(get(BASE_URL + TASK_STATUS_CONTROLLER_PATH), TEST_USERNAME)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        final List<TaskStatus> taskStatuses = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(taskStatuses).hasSize(2);
    }

    @Test
    public void updateStatusById() throws Exception {
        utils.regDefaultUser();
        utils.regDefaultStatus(TEST_USERNAME);

        final long statusID = taskStatusRepository.findAll().get(0).getId();

        TaskStatusDto taskStatusDto = new TaskStatusDto(TEST_STATUS_NAME1);

        final var updateRequest =
                put(BASE_URL + TASK_STATUS_CONTROLLER_PATH + ID, statusID)
                        .content(asJson(taskStatusDto))
                        .contentType(APPLICATION_JSON);

        utils.perform(updateRequest, TEST_USERNAME1).andExpect(status().isOk());

        assertTrue(taskStatusRepository.existsById(statusID));
        assertNull(taskStatusRepository.findByName(TEST_USERNAME).orElse(null));
        assertNotNull(taskStatusRepository.findByName(TEST_USERNAME1).orElse(null));
    }
}

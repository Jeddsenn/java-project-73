package hexlet.code.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.security.JWTHelper;
import hexlet.code.dto.request.TaskReq;
import hexlet.code.dto.request.UserReq;
import hexlet.code.model.LabelEntity;
import hexlet.code.model.TaskStatusEntity;
import hexlet.code.model.UserEntity;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.dto.request.LabelReq;
import hexlet.code.dto.request.TaskStatusReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Map;
import java.util.Set;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {
    public static final String BASE_URL = "/api";
    public static final String TEST_USERNAME = "email@email.com";
    public static final String TEST_USERNAME1 = "email1@email.com";
    public static final String TEST_STATUS_NAME = "STATUSCREATED";
    public static final String TEST_STATUS_NAME1 = "STATUSCREATEDTWICE";
    public static final String TEST_LABELNAME_1 = "label1";
    public static final String TEST_LABELNAME_2 = "label2";

    public static final String LABEL_CONTROLLER_PATH = "/labels";

    public static final String TASK_CONTROLLER_PATH = "/tasks";
    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";

    public static final String USER_CONTROLLER_PATH = "/users";
    public static final String ID = "/{id}";

    private final UserReq testRegistrationDto = new UserReq(
            TEST_USERNAME,
            "fname",
            "lname",
            "pwd"
    );
    public static final LabelReq LABEL_DTO_1 = new LabelReq(TEST_LABELNAME_1);
    public static final LabelReq LABEL_DTO_2 = new LabelReq(TEST_LABELNAME_2);

    private final TaskStatusReq testStatusDto = new TaskStatusReq(
            TEST_STATUS_NAME
    );

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private JWTHelper jwtHelper;

    public void tearDown() {
        taskRepository.deleteAll();
        taskStatusRepository.deleteAll();
        labelRepository.deleteAll();
        userRepository.deleteAll();
    }


    public UserReq getTestRegistrationDto() {
        return testRegistrationDto;
    }

    public UserEntity getUserByEmail(final String email) {
        return userRepository.findByEmail(email).get();
    }

    public ResultActions regDefaultUser() throws Exception {
        return regUser(testRegistrationDto);
    }

    public ResultActions regDefaultStatus(final String byUser) throws Exception {
        return regStatus(testStatusDto, byUser);
    }

    public ResultActions regUser(final UserReq dto) throws Exception {
        final var request = MockMvcRequestBuilders.post(BASE_URL + USER_CONTROLLER_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);

        return perform(request);
    }

    public ResultActions regStatus(final TaskStatusReq dto, final String byUser) throws Exception {
        final var request = post(BASE_URL + TASK_STATUS_CONTROLLER_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);

        return perform(request, byUser);
    }

    public ResultActions regDefaultTask(final String byUser) throws Exception {
        regDefaultUser();
        regDefaultLabel(TEST_USERNAME);
        regDefaultStatus(TEST_USERNAME);
        final UserEntity user = userRepository.findAll().get(0);
        final TaskStatusEntity taskStatus = taskStatusRepository.findAll().get(0);
        final LabelEntity label = labelRepository.findAll().get(0);
        final TaskReq testRegTaskDto = new TaskReq(
                "task",
                "description",
                taskStatus.getId(),
                user.getId(),
                Set.of(label.getId())
        );
        return regTask(testRegTaskDto, byUser);
    }

    public ResultActions regTask(final TaskReq dto, final String byUser) throws Exception {
        final var request = MockMvcRequestBuilders.post(BASE_URL + TASK_CONTROLLER_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);

        return perform(request, byUser);
    }

    public ResultActions regDefaultLabel(final String byUser) throws Exception {
        return regLabel(LABEL_DTO_1, byUser);
    }

    public ResultActions regLabel(final LabelReq labelDto, final String byUser) throws  Exception {
        final var request
                = MockMvcRequestBuilders.post(BASE_URL + LABEL_CONTROLLER_PATH)
                .content((asJson(labelDto)))
                .contentType(APPLICATION_JSON);
        return perform(request, byUser);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request, final String byUser) throws Exception {
        final String token = jwtHelper.expiring(Map.of("username", byUser));
        request.header(AUTHORIZATION, token);

        return perform(request);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, final TypeReference<T> to) throws JsonProcessingException {
        return MAPPER.readValue(json, to);
    }
}

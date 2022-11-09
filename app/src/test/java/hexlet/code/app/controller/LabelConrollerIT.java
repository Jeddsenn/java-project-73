package hexlet.code.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.app.config.SpringConfigForIT;
import hexlet.code.app.model.Label;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.LabelRepository;
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
import static hexlet.code.app.utils.TestUtils.*;
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
class LabelConrollerIT {

    @Autowired
    TestUtils utils;
    @Autowired
    LabelRepository labelRepository;

    public static final String LABEL_CONTROLLER_PATH = "/labels";
    public static final String ID = "/{id}";


    @BeforeEach
    public void clear() {
        utils.tearDown();
    }

    @Test
    void registration() throws Exception {
        assertEquals(0, labelRepository.count());
        utils.regDefaultUser();
        utils.regDefaultLabel(TEST_USERNAME).andExpect(status().isCreated());
        assertEquals(1, labelRepository.count());
    }

    @Test
    void deleteLabel() throws Exception {
        assertEquals(0, labelRepository.count());
        utils.regDefaultUser();
        utils.regDefaultLabel(TEST_USERNAME);

        final Long LabelID = labelRepository.findByName("label1").get().getId();
        utils.perform(delete(BASE_URL + LABEL_CONTROLLER_PATH + ID, LabelID), TEST_USERNAME)
                .andExpect(status().isOk());
        assertEquals(0, labelRepository.count());
    }


    @Test
    void getById() throws Exception {
        utils.regDefaultUser();
        utils.regDefaultLabel(TEST_USERNAME);
        final var expectedLabel = labelRepository.findAll().get(0);

        var response = utils.perform(get
                        (BASE_URL + LABEL_CONTROLLER_PATH + ID, expectedLabel.getId()), TEST_USERNAME)
                .andExpect(status().isOk()).andReturn().getResponse();

        final Label label = fromJson(response.getContentAsString(), new TypeReference<>() {
        });


        assertEquals(expectedLabel.getId(), label.getId());
        assertEquals(expectedLabel.getName(), label.getName());
    }

    @Test
    void getAll() throws Exception {
        utils.regDefaultUser();
        utils.regDefaultLabel(TEST_USERNAME);
        utils.regLabel(TestUtils.labelDto2 ,TEST_USERNAME);

        var response = utils.perform(get
                        (BASE_URL + LABEL_CONTROLLER_PATH), TEST_USERNAME)
                .andExpect(status().isOk()).andReturn().getResponse();

        List<Label> list = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertEquals(2, list.size());

    }

    @Test
    void update() throws Exception {
        utils.regDefaultUser();
        utils.regDefaultLabel(TEST_USERNAME);

        final var label = labelRepository.findAll().get(0);

        final var updateRequest = put(BASE_URL + LABEL_CONTROLLER_PATH + ID,
                label.getId())
                .content(asJson(labelDto2))
                .contentType(APPLICATION_JSON);

        utils.perform(updateRequest, TEST_USERNAME);

        assertTrue(labelRepository.existsById(label.getId()));
        assertNotNull(labelRepository.findByName(labelDto2.getName()).orElse(null));
        assertNull(labelRepository.findByName(labelDto.getName()).orElse(null));
    }

}

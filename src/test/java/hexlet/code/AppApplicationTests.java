package hexlet.code;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureMockMvc
@SpringBootTest
class AppApplicationTests {

    @Test
    void testInit() {
        assertThat(true).isTrue();
    }
}

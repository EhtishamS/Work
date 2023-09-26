import com.example.testing.api.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonSerializationTest {
    private static final ObjectMapper MAPPER = newObjectMapper();
    
    @Test
    void seralizesToJSON() throws Exception {
        final Person person = new Person("Luther Blissett", "lb@example.com");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(getClass().getResource("/fixtures/person.json"), Person.class));

        assertThat(MAPPER.writeValueAsString(person)).isEqualTo(expected);
    }
}


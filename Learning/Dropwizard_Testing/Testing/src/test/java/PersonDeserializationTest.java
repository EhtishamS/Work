import com.example.testing.api.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonDeserializationTest {
    private static final ObjectMapper MAPPER = newObjectMapper();

    @Test
    public void deserializesFromJSON() throws Exception {
        final Person person = new Person("Luther Blissett", "lb@example.com");
        assertThat(MAPPER.readValue(getClass().getResource("/fixtures/person.json"), Person.class))
                .isEqualTo(person);
    }
}

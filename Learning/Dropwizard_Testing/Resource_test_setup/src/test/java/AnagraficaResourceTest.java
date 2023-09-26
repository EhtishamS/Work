import com.example.dropwizard_first_setup.api.Anagrafica;
import com.example.dropwizard_first_setup.db.MyDAO;
import com.example.dropwizard_first_setup.resources.first_projectResources;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.*;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AnagraficaResourceTest {
    private static final MyDAO DAO = mock(MyDAO.class); // the purpose of the mock is to isolate the class to focus on testint the class rather than on the external dependencies.
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new first_projectResources(DAO))
            .build();
    private Anagrafica anagrafica;
    
    @BeforeEach // before every test it's going to execute the setup
    void setup(){
        anagrafica = new Anagrafica();
        anagrafica.setID(0);
    }
    
    @AfterEach // after eevery test is going to reset the dao
    void tearDown(){
        reset(DAO);
    }
    
    @Test
    void getAnagraficaSuccess(){
        when(DAO.getAnagraficaById(0)).thenReturn(anagrafica);
        
        //System.out.println("\n\nIL TARGET EXE è :" + EXT.target("/anagrafica/0") + "\n\n\n");
       
        Anagrafica found = EXT.target("/anagrafica/0").request().get(Anagrafica.class);
        
        assertThat(found.getID()).isEqualTo(anagrafica.getID());
        
        // We can use Mockito verify methods at the end of the testing method code 
        // to make sure that specified methods are called
        
        verify(DAO).getAnagraficaById(0);
    }
    
    
    @Test
    void getAnangraficaNotFound(){
        when(DAO.getAnagraficaById(1)).thenReturn(null);
        
        final Response response = EXT.target("/anagrafica/1").request().get();
        
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        
        verify(DAO).getAnagraficaById(1);
    }
}

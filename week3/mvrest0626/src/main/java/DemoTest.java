import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/test")
public class DemoTest {

  @GET
  @Produces(MediaType.TEXT_PLAIN+";charset=UTF-8")
  public String test() {
     return "my test string";	  
  }
}

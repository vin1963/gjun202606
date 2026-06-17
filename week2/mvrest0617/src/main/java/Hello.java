import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.User;

import java.util.*;
@Path("/hello")
public class Hello {
	@GET
	@Produces(MediaType.TEXT_PLAIN+";charset=UTF-8")
	public String sayHello() {
		return "大家晚安";
	}
	
	@GET
	@Path("/html")
	@Produces(MediaType.TEXT_HTML+";charset=UTF-8")
	public String sayHello2() {
		return "<h2 style='color:red'>大家晚安</h2>";
	}
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
	public User user() {
		User user = new User();
		user.setName("小明");
		user.setAge(18);
		return user;
	}
}

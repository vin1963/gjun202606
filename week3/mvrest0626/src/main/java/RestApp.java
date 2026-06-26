import java.util.*;

import controller.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApp extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		return Set.of(ParamsController.class, EmployeeController.class);
	}

	
}

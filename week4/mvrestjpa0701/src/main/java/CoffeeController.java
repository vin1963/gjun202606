import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.*;
import model.*;

@Path("/coffees")
public class CoffeeController {
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<Coffee> getCoffees() {
	   EntityManagerFactory factory=Persistence.createEntityManagerFactory("mvrestjpa0701");
	   EntityManager mgr=factory.createEntityManager();
	   TypedQuery<Coffee> query=mgr.createNamedQuery("Coffee.findAll", Coffee.class);
	   List<Coffee> cofs= query.getResultList();
	   return cofs;
   }
}

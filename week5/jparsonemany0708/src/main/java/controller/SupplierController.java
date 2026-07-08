package controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.*;
import java.util.*;
@Path("/suppliers")
public class SupplierController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Supplier> getAllSuppliers() {
		return SupplierDAO.getAllSuppliers();
	}
}

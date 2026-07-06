package controller;

import dao.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import model.Employee;

import java.net.URI;
import java.util.*;
@Path("/employees")           // API 路徑：/api/employees
@Produces(MediaType.APPLICATION_JSON)   // 回傳 JSON
@Consumes(MediaType.APPLICATION_JSON)   // 接收 JSON
public class EmployeeController {
    private final EmployeeDao<Employee, Long> repo = new EmployeeDaoImpl();
    @POST
    public Response create(Employee emp, @Context UriInfo uriInfo) {
        if (emp.getName() == null || emp.getName().isBlank()) {
            return Response.status(400).entity(apiError("Name is required")).build();
        }
        Employee created =repo.save(emp);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(apiOk(created)).build();
    }    
    
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") long id) {
        return repo.findById(id)
            .map(emp -> Response.ok(apiOk(emp)).build())
            .orElse(Response.status(404).entity(apiError("Not found: " + id)).build());
    }
    
    private Map<String, Object> apiOk(Object data) {
        return Map.of("success", true, "data", data);
    }

    private Map<String, Object> apiError(String msg) {
        return Map.of("success", false, "error", msg);
    }
}

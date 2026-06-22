package controller;

import model.Employee;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;

/**
 * Employee REST Resource
 * 路徑：/api/employees
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)    // 預設回應 JSON
@Consumes(MediaType.APPLICATION_JSON)   // 預設接受 JSON
public class EmployeeController {

    // 暫時用記憶體模擬資料庫（Day 4 換成 JPA）
    private static final Map<Integer, Employee> DB = new HashMap<>();
    private static int nextId = 1;

    // 靜態初始化測試資料
    static {
        DB.put(1, new Employee(nextId++, "Alice Chen",  "Engineering", 85000));
        DB.put(2, new Employee(nextId++, "Bob Wang",    "Marketing",   72000));
        DB.put(3, new Employee(nextId++, "Carol Liu",   "Engineering", 90000));
    }

    /**
     * 取得所有員工
     * GET /api/employees
     */
    @GET
    public Response getAllEmployees(@QueryParam("id") @DefaultValue("-1") int id) {
    	if(id != -1) {
    		Employee emp = DB.get(id);
			if (emp == null) {
				return Response.status(Response.Status.NOT_FOUND)
							   .entity("{\"message\":\"Employee not found: " + id + "\"}")
							   .build();
			}
			return Response.ok(emp).build();
    	}
        List<Employee> employees = new ArrayList<>(DB.values());
        if (employees.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
        System.out.println("getAllEmployees: " + employees);
        return Response.ok(employees).build();
    }

    /**
     * 依 ID 取得員工
     * GET /api/employees/{id}
     */
    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") int id) {
        Employee emp = DB.get(id);
        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"message\":\"Employee not found: " + id + "\"}")
                           .build();
        }
        return Response.ok(emp).build();
    }
    @POST
    public Response addEmployee(Employee emp) {
		if (emp.getName() == null || emp.getDepartment() == null) {
			return Response.status(Response.Status.BAD_REQUEST)
						   .entity("{\"message\":\"Name and Department are required\"}")
						   .build();
		}
		emp.setId(nextId++);
		DB.put(emp.getId(), emp);
		return Response.status(Response.Status.CREATED).entity(emp).build();
	}
}

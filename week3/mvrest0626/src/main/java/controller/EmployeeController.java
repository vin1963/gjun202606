package controller;

import model.Employee;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
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
    @GET
    @Path("/create/{id}")
    public Response getEmployeeById2(@PathParam("id") int id) {
        Employee emp = DB.get(id);
        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"message\":\"Employee not found: " + id + "\"}")
                           .build();
        }
        return Response.ok(emp).build();
    }
    @POST
    @Path("/create")
    public Response create(Employee emp, @Context UriInfo uriInfo) {
        if (emp.getName() == null || emp.getName().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Name is required")
                    .build();
        }

        int id = nextId++;
        emp.setId(id);
        DB.put(id, emp);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(id))
                .build();
        System.out.println("Created Employee: " + emp + ", Location: " + location);
        return Response.created(location)
                .entity(emp)
                .build();
    }
    
 // ── @PUT + @Path + @PathParam ────────────────────────────
    // 對應 PUT /api/employees/{id}
    // 完整取代：客戶端需提供所有欄位
    // 成功回傳 200 OK，不存在回傳 404，驗證失敗回傳 400
    //────────────────────────────────────────────────────────────
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Employee updated) {
        if (!DB.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found: " + id)
                    .build();
        }
        if (updated.getName() == null || updated.getName().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Name is required")
                    .build();
        }

        updated.setId(id);
        DB.put(id, updated);
        return Response.ok(updated).build();
    }
    // ── @PATCH + @Path + @PathParam + Map<String, Object> ────
    // 對應 PATCH /api/employees/{id}
    // 只更新請求中有提供的欄位，其餘保持不變
    // 數值型別轉換需用 ((Number) value).doubleValue()
    // 成功回傳 200 OK，不存在回傳 404
    //────────────────────────────────────────────────────────────
    @PATCH
    @Path("/{id}")
    public Response partialUpdate(@PathParam("id") int id,
                                  Map<String, Object> fields) {
        Employee emp = DB.get(id);
        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found: " + id)
                    .build();
        }

        fields.forEach((key, value) -> {
            switch (key) {
                case "name"       -> emp.setName((String) value);
                case "department" -> emp.setDepartment((String) value);
                case "salary"     -> emp.setSalary(
                                        ((Number) value).doubleValue());
            }
        });

        return Response.ok(emp).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (!DB.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found: " + id)
                    .build();
        }
        Employee e1=DB.remove(id);
        //return Response.noContent().build();
        return Response.ok(e1).build();
    }

}

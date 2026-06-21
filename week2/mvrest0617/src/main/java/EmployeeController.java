
import model.Employee;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Path("/employees")                    // @Path：類別層級 URI 路徑
@Produces(MediaType.APPLICATION_JSON)  // @Produces：預設回傳 JSON
@Consumes(MediaType.APPLICATION_JSON)  // @Consumes：預設接受 JSON
public class EmployeeController {

    private static final Map<Integer, Employee> DB = new HashMap<>();
    private static int nextId = 4;

    static {
        DB.put(1, new Employee(1, "Alice Chen", "Engineering", 85000));
        DB.put(2, new Employee(2, "Bob Wang", "Marketing", 72000));
        DB.put(3, new Employee(3, "Carol Liu", "Engineering", 90000));
    }

    // ── @GET + @QueryParam + @DefaultValue ──────────────────────
    // 對應 GET /api/employees
    // @QueryParam 從查詢字串取值，@DefaultValue 設定分頁預設值
    // 成功回傳 200 OK，Response Header 帶 X-Total-Count
    //────────────────────────────────────────────────────────────
    @GET
    public Response getAll(
            @QueryParam("dept") String dept,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size) {

        List<Employee> list = new ArrayList<>(DB.values());

        if (dept != null && !dept.isBlank()) {
            list.removeIf(e -> !e.getDepartment().equalsIgnoreCase(dept));
        }

        int total = list.size();
        int from = Math.min((page - 1) * size, list.size());
        int to = Math.min(from + size, list.size());
        List<Employee> paged = list.subList(from, to);

        return Response.ok(Response.ok(paged))
                .header("X-Total-Count", total)
                .build();
    }

    // ── @GET + @Path + @PathParam ─────────────────────────────
    // 對應 GET /api/employees/{id}
    // @Path("/{id}") 定義路徑範本，@PathParam 取出 {id} 值
    // 成功回傳 200 OK，不存在回傳 404 Not Found
    //────────────────────────────────────────────────────────────
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Employee emp = DB.get(id);
        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found: " + id)
                    .build();
        }
        return Response.ok(Response.ok(emp)).build();
    }

    // ── @POST + @Context UriInfo ──────────────────────────────
    // 對應 POST /api/employees
    // @Context UriInfo 注入請求 URI 資訊，用來建構 Location Header
    // 成功回傳 201 Created，含 Location Header 指向新資源
    // 必填欄位不合法回傳 400 Bad Request
    //────────────────────────────────────────────────────────────
    @POST
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

        return Response.created(location)
                .entity(Response.ok(emp))
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

    // ── @DELETE + @Path + @PathParam ──────────────────────────
    // 對應 DELETE /api/employees/{id}
    // 成功回傳 204 No Content（不傳 Body）
    // 不存在回傳 404 Not Found
    //────────────────────────────────────────────────────────────
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (!DB.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Employee not found: " + id)
                    .build();
        }
        DB.remove(id);
        return Response.noContent().build();
    }
}

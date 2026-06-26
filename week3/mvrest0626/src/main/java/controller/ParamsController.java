package controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;
import model.Item;


@Path("/params")
@Produces(MediaType.APPLICATION_JSON)
public class ParamsController {
	// 靜態測試資料庫 — 12 筆商品
	private static final List<Item> ITEMS = List.of(
	    new Item(1, "Java 入門", "圖書", 580),
	    new Item(2, "Spring Boot 實戰", "圖書", 720),
	    new Item(3, "JAX-RS 指南", "圖書", 450),
	    new Item(4, "JavaScript 大全", "圖書", 890),
	    new Item(5, "Python 自動化", "圖書", 620),
	    new Item(6, "機械式鍵盤", "3C", 2500),
	    new Item(7, "無線滑鼠", "3C", 890),
	    new Item(8, "USB-C Hub", "3C", 1200),
	    new Item(9, "27吋螢幕", "3C", 8800),
	    new Item(10, "咖啡豆 500g", "食品", 350),
	    new Item(11, "濾掛咖啡 24入", "食品", 280),
	    new Item(12, "保溫瓶 750ml", "日用", 650)
	);
	@POST	
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(
	        @FormParam("username") String username,
	        @FormParam("password") String password) {
		
		String message = String.format("{\"username\": %s, \"password\": %s}"
				                       , username,password);
		return Response.ok(message).build();
	}
	@GET @Path("/greeting")	
	public Response greeting(
	        @HeaderParam("Accept-Language") @DefaultValue("zh-TW") String lang) {
	    String msg = switch (lang) {
	        case "en" -> "Hello";
	        case "ja" -> "こんにちは";
	        default  -> "你好";
	    };
	    return Response.ok(Map.of("message", msg))
	            .header("Content-Language", lang)
	            .build();
	}
	@GET @Path("/orders/{id}")
	public Response getOrder(
	        @PathParam("id") int id,
	        @HeaderParam("X-Request-ID") String traceId) {
	    if (traceId == null) traceId = UUID.randomUUID().toString();
	    System.out.println("trace=" + traceId + " getOrder id=" + id);    
	    return Response.ok(Map.of("orderId", id))
	            .header("X-Request-ID", traceId)
	            .build();
	}
	@GET @Path("/search")
	public Response search(
	        @QueryParam("q") String query,
	        @HeaderParam("X-Page") @DefaultValue("1") int page,
	        @HeaderParam("X-Per-Page") @DefaultValue("20") int size) {
	    List<Item> results = searchItems(query, page, size);
	    
	    return Response.ok(results)
	            .header("X-Page", page)
	            .header("X-Per-Page", size)
	            .header("X-Total", countItems(query))
	            .build();
	}
	private List<Item> searchItems(String query, int page, int size) {
	    // 1. 關鍵字過濾（name / category 不分大小寫）
	    Stream<Item> stream = ITEMS.stream();
	    if (query != null && !query.isBlank()) {
	        String q = query.toLowerCase();
	        stream = stream.filter(i ->
	            i.getName().toLowerCase().contains(q) ||
	            i.getCategory().toLowerCase().contains(q));
	    }

	    List<Item> filtered = stream.collect(Collectors.toList());

	    // 2. 計算分頁邊界
	    int from = (page - 1) * size;
	    if (from >= filtered.size()) return List.of();
	    int to = Math.min(from + size, filtered.size());

	    return filtered.subList(from, to);
	}

	private int countItems(String query) {
	    if (query == null || query.isBlank()) return ITEMS.size();
	    String q = query.toLowerCase();
	    return (int) ITEMS.stream()
	            .filter(i -> i.getName().toLowerCase().contains(q) ||
	                         i.getCategory().toLowerCase().contains(q))
	            .count();
	}
}

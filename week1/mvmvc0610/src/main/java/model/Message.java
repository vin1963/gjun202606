package model;

public class Message {
   Map<String,String> greet=new HashMap<>();
   public Message() {
	   greet.put("Danny", "Hello");
	   greet.put("Mary", "Good Evening");
	   greet.put("Tony", "Good Day");	   
   }
   
   public String says(String name) {
	   return name+"said:"+greet.get(name);
   }
}

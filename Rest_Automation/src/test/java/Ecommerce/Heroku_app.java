package Ecommerce;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class Heroku_app {
	public static String baseurl = "https://ecommerceservice.herokuapp.com/";
    public String accessToken;
	public int count;
    public String id;
    public String product_name;
    public String update;
    public String delete;

	@Test(priority = 0)
	public void login() {
		
		RestAssured.baseURI = baseurl;
		String requestbody ="{\r\n"
        		+ "	\"email\": \"pqr176@gmail.com\",\r\n"
        		+ "	\"password\": \"pqr@123\"\r\n"
        		+ "}\r\n"
        		+ "";
		
		Response response = given().
				header("content-Type","application/json")
				.body(requestbody)
				
				.when()
				.post("/user/login")
				
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		JsonPath data = new JsonPath(body);
		
		accessToken = data.get("accessToken");
		System.out.println(accessToken);
	}
	
	@Test(priority = 1)
public void get_all_orders() {
		
		RestAssured.baseURI = baseurl;
		
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				
				.when()
				.get("/orders")
				
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				
				.extract().response();
	
		String body = response.asString();
		JsonPath data = new JsonPath(body);
		
		Object id = data.get("orders._id");
		System.out.println(id);
		Object name = data.get("name");
		System.out.println(name);
		//System.out.println(body);
		/*JsonPath data =  new JsonPath(body);
		Object count = data.get("count");
		System.out.println(count);
		System.out.println(data);*/
	}
	@Test(priority = 2)
	public void Create_Order() {
		
		RestAssured.baseURI = baseurl;
		
			String requestbody = "{\r\n"
					+ "	\"product\": \"621729c27a9fc400174c28a2\",\r\n"
					+ "	\"quantity\": 2\r\n"
					+ "}";
			Response response = given().
					header("content-Type","application/json")
					 .header("Authorization","bearer "+accessToken)
					 .body(requestbody)
					
					.when()
					.post("/orders")
					
					.then()
					.assertThat().statusCode(201).and().contentType(ContentType.JSON)
					
					.extract().response();
			String body = response.asString();
			System.out.println(body);
			JsonPath data =  new JsonPath(body);
			id = data.get("order._id");
			System.out.println(id);
		
	}
	
	@Test(priority = 3)
	public void get_order_by_id() {
		
		RestAssured.baseURI = baseurl;
		
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				
				 .when()
					.get("/orders/"+id)
					
					.then()
					.assertThat().statusCode(200).and().contentType(ContentType.JSON)
					
					.extract().response();
			String body = response.asString();
			System.out.println(body);
			/*JsonPath data =  new JsonPath(body);
			Object order_name = data.get("name");
			System.out.println(order_name);*/
	}
	
	@Test(priority = 4)
	public void update_order_by_id() {
		
		RestAssured.baseURI = baseurl;
		
		String requestbody ="{\r\n"
				+ "	\"product\": \"621729c27a9fc400174c28a2\",\r\n"
				+ "	\"quantity\": 20\r\n"
				+ "}";

		
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				 .body(requestbody)
				
				.when()
				.patch("/orders/"+id)
				
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		//System.out.println(body);
		JsonPath data =  new JsonPath(body);
		update = data.get("message");
		System.out.println(update);
	}
	
	@Test(priority = 5)
	public void delete_order_by_id() {
		
		RestAssured.baseURI = baseurl;
		
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				
				.when()
				.delete("/orders/"+id)
				
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		//System.out.println(body);
		JsonPath data =  new JsonPath(body);
		Object delete = data.get("message");
		System.out.println(delete);
	}

		
		}
package handlers;

import static io.restassured.RestAssured.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class RequestHandler {
	private static final Logger logger=LoggerFactory.getLogger(RequestHandler.class);
	
	//To bypass strict SSL/TLS validation
	public RequestHandler() {
		useRelaxedHTTPSValidation();
	}
	
	//Method to do GET Request
	public Response sendGetRequest(String endPoint, String apiKey) {
		try{
			logger.info("Performing GET request to: " + endPoint);
			return given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + apiKey)
				.when()
				.get(endPoint)
				.then()
				.extract()
				.response();
			}
		catch(Exception e) {
			logger.error("Error during GET reqeust:" + e.getMessage());
			throw e;
		}
	}
	
	//Method to do POST Request
	public Response sendPostRequest(String endPoint, String payload, String apiKey) {
		try{
			logger.info("Performing POST request to: " + endPoint + "\nwith end point: " + payload);
			return given()
					.contentType(ContentType.JSON)
					.header("Authorization","Bearer " + apiKey)
					.body(payload)
					.when()
					.post(endPoint)
					.then()
					.extract()
					.response();
			}
		catch(Exception e) {
			logger.error("Error during POST reqeust:" + e.getMessage());
			throw e;
		}
	}
	
	//Method to do PUT Request
	public Response sendPutRequest(String endPoint, String payload, String apiKey) {
		try{
			logger.info("Performing PUT request to: " + endPoint + "\nwith end point: " + payload);
			return given()
					.contentType(ContentType.JSON)
					.header("Authorization","Bearer " + apiKey)
					.body(payload)
					.when()
					.put(endPoint)
					.then()
					.extract()
					.response();
			}
		catch(Exception e) {
			logger.error("Error during PUT reqeust:" + e.getMessage());
			throw e;
		}
	}
	
	//Method to do DELETE Request
	public Response sendDeleteRequest(String endPoint, String apiKey) {
		try{
			logger.info("Performing DELETE request to: " + endPoint);
			return given()
					.contentType(ContentType.JSON)
					.header("Authorization", "Bearer " + apiKey)
					.when()
					.delete(endPoint)
					.then()
					.extract()
					.response();
			}
		catch(Exception e) {
			logger.error("Error during DELETE reqeust:" + e.getMessage());
			throw e;
		}
		
	}
}

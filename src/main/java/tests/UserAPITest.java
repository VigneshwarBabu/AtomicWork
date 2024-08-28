package tests;

import java.util.List;
import org.testng.annotations.Test;
import data.TestData;
import io.restassured.response.Response;
import utils.CSVReaderUtil;

public class UserAPITest extends BaseTest {


	//Create User API
	@Test(description="Verify POST users api - success validation", groups= {"CreateUser"})
	public void testPostUserDetails() {
		// Reading the CSV file from the resources folder
		String fileName = "userData.csv";
		List<TestData> users = CSVReaderUtil.readUsersFromCSV(fileName);
		String endPoint=TestData.baseURL+TestData.usersAPI;

		for (TestData user : users) {
			String jsonPayload = TestData.userAPIPayload(user.getId(), user.getName(), user.getEmailId());
			Response response=requestHandler.sendPostRequest(endPoint, jsonPayload,TestData.API_KEY);
			assertions.assertStatusCode(response, 201);
			assertions.assertPostResponse(response, user);			
		}
	}

	@Test(description="Verify POST users api is failing without authentication", groups= {"CreateUser"})
	public void testPostUserAuthenticationFailure() {
		String endPoint=TestData.baseURL+TestData.usersAPI;
		String jsonPayload = TestData.userAPIPayload(1001,"test user","abc@example.com");
		Response response=requestHandler.sendPostRequest(endPoint, jsonPayload, "testAPIKey");
		assertions.assertStatusCode(response, 401);	
	}

	//Other POST request test cases
	//1. Validate POST request without mandatory params
	//2. Validate POST request with invalid data in the params
	//3. Validate POST request payload schemas

	//GET User API
	@Test(description="Verify GET users api response", groups= {"GetUser"})
	public void verifyGetUsersSuccess() {
		String endPoint=TestData.baseURL+TestData.usersAPI;
		Response response=requestHandler.sendGetRequest(endPoint,TestData.API_KEY);
		assertions.assertStatusCode(response, 200);
		assertions.assertGetUsersResponse(response);
	}

	@Test(description="Verify GET users api retriving created users data", groups= {"GetUser"})
	public void verifyGetUsersSuccessWithData() {
		String endPoint=TestData.baseURL+TestData.usersAPI;
		TestData user=new TestData(1001,"test user","abc@example.com");
		String jsonPayload = TestData.userAPIPayload(user.getId(), user.getName(), user.getEmailId());
		Response postResponse=requestHandler.sendPostRequest(endPoint, jsonPayload, TestData.API_KEY);
		assertions.assertStatusCode(postResponse, 201);
		Response getResponse=requestHandler.sendGetRequest(endPoint,TestData.API_KEY);
		assertions.assertStatusCode(getResponse, 200);
		assertions.assertGetUsersResponseWithData(getResponse,user);
	}

	@Test(description="Verify GET users failure without authentication", groups= {"GetUser"})
	public void verifyGetUsersResponseData() {
		String endPoint=TestData.baseURL+TestData.usersAPI;
		Response response=requestHandler.sendGetRequest(endPoint, "testAPIKey");
		assertions.assertStatusCode(response, 401);	
	}

	//Other GET request test cases
	//1. Validate GET request when no user is created
	//2. Validate GET request with filters (if anything is applicable in the path or query params)

	//PUT User API
	@Test(description="Verify PUT users api updating the existing users data", groups= {"UpdateUser"})
	public void verifyPutUsersSuccessWithData() {
		int id=22334;
		String endPoint=TestData.baseURL+TestData.usersAPI;
		TestData user=new TestData(id,"test user","abc@example.com");
		String jsonPayload = TestData.userAPIPayload(user.getId(), user.getName(), user.getEmailId());
		Response postResponse=requestHandler.sendPostRequest(endPoint, jsonPayload, TestData.API_KEY);
		assertions.assertStatusCode(postResponse, 201);
		endPoint=TestData.baseURL+TestData.usersAPI+id;
		user=new TestData(12345,"updated user","updatedemail@example.com");
		jsonPayload = TestData.userAPIPayload(user.getId(), user.getName(), user.getEmailId());
		Response putResponse=requestHandler.sendPutRequest(endPoint,jsonPayload,TestData.API_KEY);
		assertions.assertStatusCode(putResponse, 200);
		assertions.assertPutResponse(putResponse,user);
	}

	//Other PUT request test cases
	//1. Validate PUT request without mandatory params in the payload
	//2. Validate PUT request with invalid data in the params
	//3. Validate PUT request payload schemas

	//DELETE User API
	@Test(description="Verify DELETE users api", groups= {"DeleteUser"})
	public void verifyDeleteUsers() {
		int id=11223;
		String endPoint=TestData.baseURL+TestData.usersAPI;
		TestData user=new TestData(id,"test user","abc@example.com");
		String jsonPayload = TestData.userAPIPayload(user.getId(), user.getName(), user.getEmailId());
		Response postResponse=requestHandler.sendPostRequest(endPoint, jsonPayload, TestData.API_KEY);
		assertions.assertStatusCode(postResponse, 201);
		endPoint=TestData.baseURL+TestData.usersAPI+id;
		Response deleteResponse=requestHandler.sendDeleteRequest(endPoint,TestData.API_KEY);
		assertions.assertStatusCode(deleteResponse, 204);
		assertions.assertDeleteResponse(deleteResponse);
		endPoint=TestData.baseURL+TestData.usersAPI;
		Response getResponse=requestHandler.sendGetRequest(endPoint, TestData.API_KEY);
		assertions.assertSuccessfulUserDeletion(getResponse,user);
	}

	@Test(description="Verify not able to DELETE the already deleted user", groups= {"DeleteUser"})
	public void verifyAlreadyDeletedUser() {
		int id=11224;
		String endPoint=TestData.baseURL+TestData.usersAPI;
		TestData user=new TestData(id,"test user","abc@example.com");
		String jsonPayload = TestData.userAPIPayload(user.getId(), user.getName(), user.getEmailId());
		Response postResponse=requestHandler.sendPostRequest(endPoint, jsonPayload, TestData.API_KEY);
		assertions.assertStatusCode(postResponse, 201);
		endPoint=TestData.baseURL+TestData.usersAPI+id;
		Response deleteResponse=requestHandler.sendDeleteRequest(endPoint,TestData.API_KEY);
		assertions.assertStatusCode(deleteResponse, 204);
		deleteResponse=requestHandler.sendDeleteRequest(endPoint,TestData.API_KEY);
		assertions.assertStatusCode(deleteResponse, 400);
	}

	//Other DELETE request test cases
	//1. Validate DELETE request with invalid id in path param
	//2. Validate DELETE request without authentication or api key
	//3. Validate DELETE request payload schemas
}

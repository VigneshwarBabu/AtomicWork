package assertions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import data.TestData;
import io.restassured.response.Response;

public class Assertions {

	public void assertStatusCode(Response response, int expectedStatusCode) {
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode,"Expected status code is " + expectedStatusCode + ", but received " +actualStatusCode);
	}

	public void assertGetUsersResponse(Response response) {
		Assert.assertTrue(response.body().jsonPath().getList("items").size()>0, "Users list cannot be empty");		
	}

	public void assertPostResponse(Response response, TestData user) {
		String description="User created successfully";
		Assert.assertEquals(response.getBody().jsonPath().getString("description"),description, "Expected description is " + description);
		Assert.assertEquals(response.getBody().jsonPath().getString("User.id"), user.getId(), "Expected id is " + user.getId());
		Assert.assertEquals(response.getBody().jsonPath().getString("User.name"), user.getName(), "Expected user name is " + user.getName());
		Assert.assertEquals(response.getBody().jsonPath().getString("User.email"), user.getEmailId(), "Expected email id is " + user.getEmailId());
	}

	public void assertGetUsersResponseWithData(Response response, TestData user) {
		String description="Successful response";
		Assert.assertEquals(response.getBody().jsonPath().getString("description"),description, "Expected description is " + description);
		Assert.assertEquals(response.getBody().jsonPath().getString("items.User[0].id"), user.getId(), "Expected id is " + user.getId());
		Assert.assertEquals(response.getBody().jsonPath().getString("items.User[0].name"), user.getName(), "Expected user name is " + user.getName());
		Assert.assertEquals(response.getBody().jsonPath().getString("items.User[0].email"), user.getEmailId(), "Expected email id is " + user.getEmailId());
	}

	public void assertPutResponse(Response response, TestData user) {
		String description="User updated successfully";
		Assert.assertEquals(response.getBody().jsonPath().getString("description"),description, "Expected description is " + description);
		Assert.assertEquals(response.getBody().jsonPath().getString("User.id"), user.getId(), "Expected id is " + user.getId());
		Assert.assertEquals(response.getBody().jsonPath().getString("User.name"), user.getName(), "Expected user name is " + user.getName());
		Assert.assertEquals(response.getBody().jsonPath().getString("User.email"), user.getEmailId(), "Expected email id is " + user.getEmailId());
	}
	
	public void assertDeleteResponse(Response response) {
		String description="User deleted successfully";
		Assert.assertEquals(response.getBody().jsonPath().getString("description"),description, "Expected description is " + description);
	}

	public void assertSuccessfulUserDeletion(Response response, TestData user) {
		List<Map<String, Object>> usersList = response.jsonPath().getList("items.User");

        boolean userFound = false;
        for (Map<String, Object> singleUser : usersList) {
            int id = (int) singleUser.get("id");
            String name = (String) singleUser.get("name");
            String emailId = (String) singleUser.get("emailId");

            if (id == user.getId() && name.equals(user.getName()) && emailId.equals(user.getEmailId())) {
                userFound = true;
                break;
            }
        }

        Assert.assertFalse(userFound, "User successfully deleted");
		
	}

}

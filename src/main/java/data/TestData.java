package data;

public class TestData {

	//base URL's
	public static String stageURL="https://api.example.stage";
	public static String preprodURL="https://api.example.preprod";
	public static String env="STAGE";
	public static String baseURL;

	public static void setBaseURL() {
		switch(env) {
		case "STAGE": 
			baseURL=stageURL;
			break;
		case "PREPROD": 
			baseURL=preprodURL;
			break;
		}
	}

	//endpoints
	public static String usersAPI="/users";
	public static String updateUsersAPI="/users/";
	public static final String API_KEY = null;
	
	//testdata
	public int id;
	public String name;
	public String emailId;
	

    public TestData(int id, String name, String emailId) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }



	//payloads
	public static String userAPIPayload(int id, String name, String emailID) {
		String userPayload="{\"id\": \""+id+"\",\"name\": \""+name+"\",\"email\": \""+emailID+"\"}";
		return userPayload;
		
	}

}
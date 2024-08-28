package tests;

import org.testng.annotations.*;

import assertions.Assertions;
import handlers.RequestHandler;

public class BaseTest {
	protected RequestHandler requestHandler;
	protected Assertions assertions;

@BeforeTest
public void setUp(){
	requestHandler=new RequestHandler();
	System.out.println("Test Execution Started");
}

@AfterTest
public void endTest() {
	System.out.println("Test Execution Ended");
}
	
}

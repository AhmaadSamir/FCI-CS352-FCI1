package com.FCI.SWE.Services;

import static org.testng.Assert.assertEquals;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.Page;
import com.FCI.SWE.Models.UserEntity;
import com.FCI.SWE.Services.Service;

public class ServiceTest {
	Service serv = new Service();
	  /*** Passed ***/
  @Test
  public void addAllFriendRequestsService() throws ParseException {
	  
	  String test = serv.addAllFriendRequestsService("b");
	  JSONParser parser = new JSONParser();
		Object obj = parser.parse(test);
		JSONObject object = (JSONObject) obj;
		boolean ok = false;
		String requestResponse = "All users those sent you request became friends with you now\n"
				+ " congrats ya user ya zeft 7abibi :D \n "
				+ "in-shaa-el-Allah el phase gaya ha5alek te5tar el sadek ely enta bet7ebo  :DxD "; 
		
		if (object.get("requestResponse").equals(requestResponse));
			ok = true;
			
			assertEquals( ok , true);
			
			
	///Not exist user
			 test = serv.addAllFriendRequestsService("notExist");
			   parser = new JSONParser();
				 obj = parser.parse(test);
				 object = (JSONObject) obj;
				 ok = false;
				if (object.get("requestResponse").equals("Failed"));
					ok = true;
				
					assertEquals( ok , true);
   
     
  }
  
  /***Failed***/ 
  ///as not totaly implemented
  @Test
  public void createPostService() {
    
  }
  /*** Passed ***/
  @Test
  public void loginService() throws ParseException {
    
	String test = serv.loginService("a" , "WrongPass");
    JSONParser parser = new JSONParser();
	Object obj = parser.parse(test);
	JSONObject object = (JSONObject) obj;
  	assertEquals( object.get("Status") , "Failed");
  	
  	 test = serv.loginService("" , "");
     parser = new JSONParser();
	 obj = parser.parse(test);
	 object = (JSONObject) obj;
  	assertEquals( object.get("Status") , "Failed");
  	
  	test = serv.loginService("a" , "a");
    parser = new JSONParser();
	 obj = parser.parse(test);
	 object = (JSONObject) obj;
 	assertEquals( object.get("Status") , "OK");
  			
    
  }
  /*** Passed ***/
  @Test
  public void registrationService() throws ParseException {
	
	    String test = serv.registrationService("a" , "a" , "a");
	    JSONParser parser = new JSONParser();
		Object obj = parser.parse(test);
		JSONObject object = (JSONObject) obj;
	  	assertEquals( object.get("Status") , "Failed");
	  	
	  	 test = serv.registrationService("a" , "validEmail" , "a");
	     parser = new JSONParser();
		 obj = parser.parse(test);
		 object = (JSONObject) obj;
	  	assertEquals( object.get("Status") , "OK");
	  	
	  	 test = serv.registrationService("" , "" , "");
	     parser = new JSONParser();
		 obj = parser.parse(test);
		 object = (JSONObject) obj;
	  	assertEquals( object.get("Status") , "Failed");
	  	

  }
  /*** Passed ***/
  @Test
  public void sendFriendRequestService() throws ParseException {
	  
	  String isFriends = "Request denied as you are actually friends";
	  String done = "Request has been sent successfully ya user ya 7abibi ^__^ :D "; 
	  String notFound = "Request denied as this user not found ."; 
	  String addUrSelf = "Request denied as you can`t add yourself ."; 
	  
	  
	  String test = serv.sendFriendRequestService("a" , "a");
	    JSONParser parser = new JSONParser();
		Object obj = parser.parse(test);
		JSONObject object = (JSONObject) obj;
		assertEquals( object.get("requestResponse") , addUrSelf);
		
		  test = serv.sendFriendRequestService("a" , "b");
		     parser = new JSONParser();
			 obj = parser.parse(test);
			 object = (JSONObject) obj;
			assertEquals( object.get("requestResponse") , isFriends);
			
			 test = serv.sendFriendRequestService("a" , "osama");
		     parser = new JSONParser();
			 obj = parser.parse(test);
			 object = (JSONObject) obj;
			assertEquals( object.get("requestResponse") , done);
			
			 test = serv.sendFriendRequestService("a" , "notExistUser");
		     parser = new JSONParser();
			 obj = parser.parse(test);
			 object = (JSONObject) obj;
			assertEquals( object.get("requestResponse") , notFound);
			
 	
    
  }
  /*** Passed ***/
  @Test
  public void sendNewMessageService() throws ParseException {
	 
		   String toURself = "Request denied as you can`t send message to yourself ."; 
		   String notFound = "Request denied as this user not found ."; 
		   String notFriends = "Request denied as you are not friends"; 
		   String done = "Request has been sent successfully"; 
		 

	  String test = serv.sendNewMessageService("a" , "a" , "message1");
	    JSONParser parser = new JSONParser();
		Object obj = parser.parse(test);
		JSONObject object = (JSONObject) obj;
		assertEquals( object.get("requestResponse") , toURself);
		
		  test = serv.sendNewMessageService("a" , "notExistUSer" , "message2");
	     parser = new JSONParser();
		 obj = parser.parse(test);
		 object = (JSONObject) obj;
		assertEquals( object.get("requestResponse") , notFound);
    
		 test = serv.sendNewMessageService("a" , "osama" , "message3");
	     parser = new JSONParser();
		 obj = parser.parse(test);
		 object = (JSONObject) obj;
		assertEquals( object.get("requestResponse") , notFriends);
		
		 test = serv.sendNewMessageService("a" , "b" , "message4");
	     parser = new JSONParser();
		 obj = parser.parse(test);
		 object = (JSONObject) obj;
		assertEquals( object.get("requestResponse") , done);
		
		
  }
  /*** Failed ***/
  /// not implemented yet
  @Test
  public void showUserTimelineService() {
    
  }
  
  
}

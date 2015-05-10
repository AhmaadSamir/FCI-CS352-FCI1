package com.FCI.SWE.Models;

import static org.testng.Assert.assertEquals;
import junit.framework.Assert;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

@Test
public class UserEntityTest {
	
  @BeforeTest
  public void beforeTest() {
	  
  }
  /***  Passed ***/
  @Test
  public void addAllFriendRequests() {
	  
	  //public boolean addAllFriendRequests() 
	  UserEntity user = new UserEntity();
	  user = user.getUser("a" , "a");
	  assertEquals( user.addAllFriendRequests() , true);
	  
	  user = user.getUser("b" , "b");
	  assertEquals( user.addAllFriendRequests() , true);
	  

  }
  
  /***  Passed ***/ 
  @Test
  public void addFriendRequest() {
      
	  ///public static boolean addFriendRequest(String senderEmail , String recevierEmail)
	 
	  assertEquals( UserEntity.addFriendRequest("b" , "osama") , true);
	  
  }
  /***  Passed ***/
  @Test
  public void addMessage() {
  ///public static boolean addMessage(String senderEmail , String recevierEmail , String message)
	/// hena bas bedef lel DB
	  
	  assertEquals( UserEntity.addMessage("b" , "a" , "te3raf te3mel 2mbaleh like said el hawa xD") , true);
	  
  }
  /***  Passed ***/ 
  @Test
  public void getLastFriendshipID() {
	  
	  ///public static  int getLastFriendshipID() 
	  ///hena besearch bas fe el DB
	  int last =  UserEntity.getLastFriendshipID();
	  assertEquals( last , 3); // shof fe kam el awel fe DB
    
  }
  /***  Passed ***/  
  @Test
  public void getLastUserID() {
	///public static  int getLastUserID() 
	  ///hena besearch bas fe el DB
	  int last =  UserEntity.getLastUserID();
	  assertEquals( last , 5); // shof fe kam el awel fe DB
  }
  @Test
  public void getLastmessageId() {
	///public static  int getLastmessageId() 
	  ///hena besearch bas fe el DB
	  int last =  UserEntity.getLastmessageId();
	  assertEquals( last , 2); // shof fe kam el awel fe DB
  }
  /***  Passed ***/
  @Test
  public void getUser() {
	 ///public static UserEntity getUser(String email, String pass) 
	 UserEntity user = new UserEntity();
	 assertEquals( UserEntity.getUser("wrongName","wrongPass" )  , null);
	 assertEquals( UserEntity.getUser("a","wrongPass" )  , null);
	 assertEquals( UserEntity.getUser("","" )  , null);
	 //valid
	 boolean valid = false;
	  if( UserEntity.getUser("a","a" ) != null)
		  valid = true;
	  assertEquals( valid  , true);
    
  }
  /***  Passed ***/
  @Test
  public void isFriends() {
	  
	  ///public static boolean isFriends(String senderEmail , String recevierEmail)
	  assertEquals( UserEntity.isFriends("notExist" , "a")  , false);
	  assertEquals( UserEntity.isFriends("osama" , "a")  , false);
	  assertEquals( UserEntity.isFriends("b" , "a")  , true);

  }
  /***  Passed ***/ 
  @Test
  public void saveUser() {
	  UserEntity test1 = new UserEntity("osama", "osama", "osama");
	  boolean test1Result = test1.saveUser();
	  assertEquals(true , test1Result);
  }
  /***  Passed ***/
  @Test
  public void userEmailFound() {
	  UserEntity user = new UserEntity();
	  assertEquals( user.userEmailFound("notExist")  , false);
	  assertEquals( user.userEmailFound("a")  , true);
  
  }
}

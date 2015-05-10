package com.FCI.SWE.Models;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.annotations.Test;

public class UserTimelineTest {
	/***  Passed ***/
  @Test
  public void foundUserTimeline() {
	  assertEquals( UserTimeline.foundUserTimeline(2) , true);
	  assertEquals( UserTimeline.foundUserTimeline(11) , false);
  }
  /***  Failed ***/
  @Test
  public void getUserTimeline() {
	  boolean found = false;
	  if(UserTimeline.getUserTimeline(2) != null)
		  found = true;
	  assertEquals(  found , true);
	  
	  assertEquals( UserTimeline.getUserTimeline(11) , null);
  }
  /***  Passed ***/
  @Test
  public void saveUserTimeline() {
	  
	  Post post = new Post(6, "a", "sadFa45",  "Private" , "4elt SW xD rabna yastor",
			  5, "a");
	  ArrayList<Post> posts = new ArrayList<>();
	  posts.add(post);
	  
	  UserTimeline userTimeline = new UserTimeline(3, "osama" , posts, "osama ");
	  
	  assertEquals( userTimeline.saveUserTimeline() , true);
    
  }
  
}

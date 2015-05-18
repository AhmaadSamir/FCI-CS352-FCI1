package com.FCI.SWE.Models;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.annotations.Test;

public class PageTest {
	
  /***  Failed ***/
  @Test
  public void getPage() {
	  boolean found = false;
	  if(Page.getPage(2) != null)
		  found = true;
	  assertEquals(  found , true);
	  
	  assertEquals( Page.getPage(11) , null);
    
  }
  /***  Passed ***/
  @Test
  public void savePage() {
	  
	  Post post = new Post(7, "osama", "7azenFa45",  "public" , "Koko Lolo Dodo Bobo",
			  5, "a");
	  ArrayList<Post> posts = new ArrayList<>();
	  posts.add(post);
	  
	  UserEntity a = UserEntity.getUser("a", "a");
	  UserEntity b = UserEntity.getUser("b","b");
	  ArrayList<UserEntity> users = new ArrayList<UserEntity>();
	  users.add(a);
	  users.add(b);
	  
	   Page page = new Page(4, "3abelo we edelo", users, posts, "a", "social");
	  
	  assertEquals( page.savePage() , true);
   
  }
  
  
}

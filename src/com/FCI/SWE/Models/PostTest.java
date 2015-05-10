package com.FCI.SWE.Models;

import static org.testng.Assert.assertEquals;

import javax.validation.constraints.AssertTrue;

import org.testng.annotations.Test;

public class PostTest {
	/***  Passed ***/
  @Test
  public void foundPost() {
	  
	  assertEquals( Post.foundPost(2) , true);
	  assertEquals( Post.foundPost(11) , false);
    
  }
  /***  Passed ***/
  @Test
  public void getAllPosts() {
	  
	  boolean ok = false;
	  if( Post.getAllPosts("a").size() > 0)
		  ok = true;
	  
	  assertEquals( ok , true);
	  ok = false;
	  if( Post.getAllPosts("sabry").size() > 0)
		  ok = true;
	  
	  assertEquals( ok , false);
  }
  /***  Passed ***/
  @Test
  public void getPost() {
	  
	  boolean found = false;
	  if(Post.getPost(2) != null)
		  found = true;
	  assertEquals(  found , true);
	  
	  assertEquals( Post.getPost(11) , null);
    
  }
  /***  Passed ***/
  @Test
  public void savePost() {
	  Post post = new Post(8, "b", "7aznan :D xD",  "public" , "post 7azen",
			  5, "a");
	  assertEquals( post.savePost() , true);
    
  }
  
}

package com.FCI.SWE.Models;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

public class HashtagTest {
  @BeforeTest
  public void beforeTest() {
	  
  }


  /***  Failed ***/
  @Test
  public void getHatshtag() {
	  ///public static Hashtag getHatshtag(int _hashtagId) {
	  boolean found = false;
	  if(Hashtag.getHatshtag(2) != null)
		  found = true;
	  assertEquals(  found , true);
	  
	  assertEquals( Hashtag.getHatshtag(11) , null);
    
  }

  /***  Passed ***/
  @Test
  public void saveHashtag() {
	  
	  Post post = new Post(6, "b", "7azenFa45",  "public" , "4elt SW xD rabna yastor",
			  5, "a");
	  ArrayList<Post> posts = new ArrayList<>();
	  posts.add(post);
	  
	  Hashtag hashtag = new Hashtag(3, posts, "SW");
	  
	  assertEquals( hashtag.saveHashtag() , true);
    
  }
  
}

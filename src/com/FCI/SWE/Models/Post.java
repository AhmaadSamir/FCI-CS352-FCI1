package com.FCI.SWE.Models;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.glassfish.jersey.server.JSONP;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sun.org.mozilla.javascript.internal.ast.TryStatement;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.datanucleus.annotations.Owned;

/**
 * 
 * @author sabry , Ahmed , Osama
 *
 */
public class Post {
	
	public static int postId ;
	public static String ownerEmail;
	public static String feeling ;
	public static String privacy ;
	public static String content;
	public static int nLike;
	public static String sharedFromEmail ;
	
	/**
	 * Default contractor
	 */
	public  Post() {
		postId = 0;
		ownerEmail = "";
		feeling = "non";
		privacy = "";
		content = "";
		nLike = 0;
		sharedFromEmail = "";
		
	}
	/**
	 * 
	 * @param postId
	 * @param ownerEmail
	 * @param feeling
	 * @param privacy
	 * @param content
	 * @param nLike
	 */
	
	
	public Post(int postId , String ownerEmail ,String feeling , String privacy ,  String content ,
			    int nLike , String sharedFromEmail){
		this.postId = postId;
		this.ownerEmail = ownerEmail;
		this.feeling = feeling;
		this.privacy = privacy;
		this.content = content;
		this.nLike = nLike;
		this.sharedFromEmail = sharedFromEmail ;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getPostId() {
		return postId;
	}
	/**
	 * 
	 * @param postId
	 */
	public static  void setPostId(int postId) {
		postId = postId;
	}
	/**
	 * 
	 * @return
	 */
	public static String getOwnerEmail() {
		return ownerEmail;
	}
	/**
	 * 
	 * @param ownerEmail
	 */
	public static void setOwnerEmail(String ownerEmail) {
		ownerEmail = ownerEmail;
	}
	/**
	 * 
	 * @return
	 */
	public static String getFeeling() {
		return feeling;
	}
	/**
	 * 
	 * @param feeling
	 */
	public static void setFeeling(String feeling) {
		feeling = feeling;
	}
	/**
	 * 
	 * @return
	 */
	public static String getPrivacy() {
		return privacy;
	}
	/**
	 * 
	 * @param privacy
	 */
	public static void setPrivacy(String privacy) {
		privacy = privacy;
	}
	/**
	 * 
	 * @return
	 */
	public static String  getContent() {
		return content;
	}
	/**
	 * 
	 * @param content
	 */
	public static void setContent(String content) {
		content = content;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getnLike() {
		return nLike;
	}
	/**
	 * 
	 * @param nLike
	 */
	public static void setnLike(int nLike) {
		nLike = nLike;
	}
	/**
	 * 
	 * @return
	 */
	public static String getSharedFromEmail() {
		return sharedFromEmail;
	}
	/**
	 * 
	 * @param sharedFromEmail
	 */
	public static void setSharedFromEmail(String sharedFromEmail) {
		sharedFromEmail = sharedFromEmail;
	}
	
	/**
	 * 
	 * @return last post id 
	 */
	public static int getLastPostID() {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterate over all posts
		int lastId = 1 ;
		for (Entity entity : pq.asIterable()) {
		    //System.out.print("LOOOOOOOOOOL");
			lastId = Integer.parseInt( entity.getProperty("postId").toString() );
		}
		
		return lastId ;

	}
	
	
	
	/**
	 * 
	 * @return
	 */
	
	public static  boolean savePost(){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		postId = getLastPostID() + 1 ;

		Entity post = new Entity("posts", postId);

		post.setProperty("postId", postId);
		post.setProperty("ownerEmail", ownerEmail);
		post.setProperty("feeling", feeling);
		post.setProperty("privacy", privacy);
		post.setProperty("content", content);
		post.setProperty("nLike", nLike);
		post.setProperty("sharedFromEmail" , sharedFromEmail);
		
		datastore.put(post);
        
		return true;
		
	}
	
	/**
	 * 
	 * @param _postId
	 * @return
	 */
	public static boolean foundPost(int _postId)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterat over all posts
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("postId").toString().equals(_postId) ){
				return true ;
			}
		}
		
		return false ;
	}
	
	/**
	 * 
	 * @param _postId
	 * @return
	 */
	
	public static Post getPost(int _postId) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterat over all posts
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("postId").toString().equals(_postId) ){
					 
	             String _ownerEmail = entity.getProperty("ownerEmail").toString();						             
				 String _feeling = entity.getProperty("feeling").toString();
				 String _privacy = entity.getProperty("privacy").toString();
				 String _content = entity.getProperty("content").toString();
				 int _nLike = Integer.parseInt(entity.getProperty("nLike").toString()	);
				 String _sharedFromEmail = entity.getProperty("sharedFromEmail").toString();
				 
				 Post post = new Post(_postId , _ownerEmail , _feeling , _privacy , _content , _nLike , _sharedFromEmail);
				 return post;
			}
			
		}

		return null;
	}
	
	/**
	 * 
	 * @param ownerEmail
	 * @return
	 */
	public static ArrayList<Post> getAllPosts(String ownerEmail){
		
		ArrayList<Post> allPosts = new ArrayList<Post> ();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterat over all posts
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("ownerEmail").toString().equals(ownerEmail) ){

				 int _postId = Integer.parseInt(entity.getProperty("postId").toString()	);
				 String _ownerEmail = entity.getProperty("ownerEmail").toString();						             
				 String _feeling = entity.getProperty("feeling").toString();
				 String _privacy = entity.getProperty("privacy").toString();
				 String _content = entity.getProperty("content").toString();
				 int _nLike = Integer.parseInt(entity.getProperty("nLike").toString()	);
				 String _sharedFromEmail = entity.getProperty("sharedFromEmail").toString();
				 
				 Post post = new Post(_postId , _ownerEmail , _feeling , _privacy ,
						 _content , _nLike , _sharedFromEmail);
				allPosts.add(post);
			}
		}
		
		return allPosts;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public static Post parsePostInfo(String json){
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(json);
			
			Post post = new Post();
			
			
			
			
			post.setPostId(Integer.parseInt(obj.get("postId").toString()));
			post.setnLike(Integer.parseInt(obj.get("nLike").toString()));
			post.setFeeling(obj.get("feeling").toString());
			post.setContent( obj.get("content").toString());
			post.setOwnerEmail( obj.get("ownerEmail").toString());
			post.setOwnerEmail( obj.get("ownerEmail").toString());
			post.setPrivacy( obj.get("privacy").toString());
			post.setSharedFromEmail( obj.get("sharedFromEmail").toString());
			return post ;

			
		} catch (Exception e) {
			System.out.println("something goes wrong in function parsePost check that ");
		}
		
		return null;
	}
	
	

		

	
	



}

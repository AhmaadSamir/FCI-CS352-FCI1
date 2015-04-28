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
	
	private int postId ;
	private String ownerEmail;
	private String feeling ;
	private String privacy ;
	private String content;
	private int nLike;
	private String sharedFromEmail ;
	
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
	public int getPostId() {
		return postId;
	}
	/**
	 * 
	 * @param postId
	 */
	public void setPostId(int postId) {
		this.postId = postId;
	}
	/**
	 * 
	 * @return
	 */
	public String getOwnerEmail() {
		return ownerEmail;
	}
	/**
	 * 
	 * @param ownerEmail
	 */
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	/**
	 * 
	 * @return
	 */
	public String getFeeling() {
		return feeling;
	}
	/**
	 * 
	 * @param feeling
	 */
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
	/**
	 * 
	 * @return
	 */
	public String getPrivacy() {
		return privacy;
	}
	/**
	 * 
	 * @param privacy
	 */
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	/**
	 * 
	 * @return
	 */
	public String  getContent() {
		return content;
	}
	/**
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getnLike() {
		return nLike;
	}
	/**
	 * 
	 * @param nLike
	 */
	public void setnLike(int nLike) {
		this.nLike = nLike;
	}
	/**
	 * 
	 * @return
	 */
	public String getSharedFromEmail() {
		return sharedFromEmail;
	}
	/**
	 * 
	 * @param sharedFromEmail
	 */
	public void setSharedFromEmail(String sharedFromEmail) {
		this.sharedFromEmail = sharedFromEmail;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	
	public  boolean savePost(){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		postId = getLastPostID() + 1 ;

		Entity post = new Entity("posts", postId);

		post.setProperty("postId", this.postId);
		post.setProperty("ownerEmail", this.ownerEmail);
		post.setProperty("feeling", this.feeling);
		post.setProperty("privacy", this.privacy);
		post.setProperty("content", this.content);
		post.setProperty("nLike", this.nLike);
		post.setProperty("sharedFromEmail" , this.sharedFromEmail);
		
		datastore.put(post);
        
		return true;
		
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
		
			lastId = Integer.parseInt( entity.getProperty("PostId").toString() );
		}
		
		return lastId ;

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

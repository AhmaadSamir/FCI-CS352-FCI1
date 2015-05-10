package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Hashtag {
	
	
	private int hashtagId ;
	private ArrayList<Post> posts ;
	private String name ;
	
	/**
	 * 
	 */
	public Hashtag() {
		
		this.hashtagId = 0 ;
		this.posts = new ArrayList<Post>() ;
		this.name = "";
	}
	/**
	 * 
	 * @param hashtagId
	 * @param posts
	 * @param name
	 */
	public Hashtag(int hashtagId, ArrayList<Post> posts, String name) {
		super();
		this.hashtagId = hashtagId;
		this.posts = posts;
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public int getHashtagId() {
		return hashtagId;
	}
	/**
	 * 
	 * @param hashtagId
	 */
	public void setHashtagId(int hashtagId) {
		this.hashtagId = hashtagId;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<Post> getPosts() {
		return posts;
	}
	/**
	 * 
	 * @param posts
	 */
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public int getNumberOfPosts(){
		return posts.size();
	}
	
	/**
	 * 
	 * @return
	 */
	
	public  boolean saveHashtag(){
		
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		this.hashtagId = getLastHashtagID() + 1 ;

		Entity hashtag = new Entity("hashtag", this.hashtagId);

		hashtag.setProperty("hashtagId", this.hashtagId);
		hashtag.setProperty("name", this.name);
		hashtag.setProperty("posts", this.posts.toString());

		
		
		datastore.put(hashtag);
        
		return true;
		
	}
	/**
	 * 
	 * @return last hashtag id 
	 */
	public static int getLastHashtagID() {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all hashtages
		int lastId = 1 ;
		for (Entity entity : pq.asIterable()) {
		
			lastId = Integer.parseInt( entity.getProperty("hashtagId").toString() );
		}
		
		return lastId ;

	}
	/**
	 * 
	 * @param _hashtagId
	 * @return
	 */
	public static boolean foundHashtag(int _hashtagId){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all hashtag
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("hashtagId").toString().equals(_hashtagId) ){
				return true ;
			}
		}
		
		return false ;
	}
	
	/**
	 * 
	 * @param _hashtagId
	 * @return
	 */
	public static Hashtag getHatshtag(int _hashtagId) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all hashtages
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("hashtagId").toString().equals(_hashtagId) ){
				
			    String _name = entity.getProperty("name").toString();
			    ArrayList<Post> _posts = ( ArrayList<Post> )entity.getProperty("posts");
				 
				Hashtag hashtag= new Hashtag( _hashtagId , _posts , _name );
				 return hashtag;
			}
			
		}

		return null;
	}

	
	
	
	

}

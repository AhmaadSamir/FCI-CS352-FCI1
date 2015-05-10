package com.FCI.SWE.Models;

import java.util.ArrayList;

import javax.management.loading.PrivateClassLoader;
import javax.naming.TimeLimitExceededException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class UserTimeline {
	
	private int  userTimelineId ;
	private String ownerEmail ;
	private ArrayList<Post> posts ;
	private String name ;
	
	
	/**
	 * 
	 * @param userTimelineId
	 * @param ownerEmail
	 * @param posts
	 * @param name
	 */
	public UserTimeline(int userTimelineId, String ownerEmail,
			ArrayList<Post> posts, String name) {
		super();
		this.userTimelineId = userTimelineId;
		this.ownerEmail = ownerEmail;
		this.posts = posts;
		this.name = name;
	}

	/**
	 * 
	 */
	public UserTimeline(){
		this.setName("");
		this.setOwnerEmail("");
		this.setPosts(new ArrayList<Post>());
		this.setUserTimelineId(0);
		
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
	public int getUserTimelineId() {
		return userTimelineId;
	}
	
	/**
	 * 
	 * @param userTimelineId
	 */
	public void setUserTimelineId(int userTimelineId) {
		this.userTimelineId = userTimelineId;
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
    public  boolean saveUserTimeline(){
		
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("userTimeline");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		userTimelineId = getLastUserTimelineID() + 1 ;

		Entity userTimeline = new Entity("userTimeline", userTimelineId);	

		userTimeline.setProperty("userTimelineId", this.userTimelineId);
		userTimeline.setProperty("name", this.name);
		userTimeline.setProperty("ownerEmail", this.ownerEmail);
		userTimeline.setProperty("posts", this.posts.toString());
			
		datastore.put(userTimeline);
        
		return true;
		
	}
	/**
	 * 
	 * @return last User Timeline id 
	 */
	public static int getLastUserTimelineID() {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("userTimeline");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all Users Timeline
		int lastId = 1 ;
		for (Entity entity : pq.asIterable()) {
		
			lastId = Integer.parseInt( entity.getProperty("userTimelineId").toString() );
		}
		
		return lastId ;

	}
	/**
	 * 
	 * @param _userTimelineId
	 * @return
	 */
	public static boolean foundUserTimeline(int _userTimelineId )
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("userTimeline");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all Users Timelines
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("userTimeline").toString().equals(_userTimelineId) ){
				return true ;
			}
		}
		
		return false ;
	}
	
	/**
	 * 
	 * @param _userTimelineId
	 * @return
	 */
	 
	public static UserTimeline getUserTimeline(int _userTimelineId) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("userTimeline");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all Users Timeliness
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("userTimelineId").toString().equals(_userTimelineId) ){
				
			    String _name = entity.getProperty("name").toString();
			    ArrayList<Post> _posts = ( ArrayList<Post> )entity.getProperty("posts");
			    String _pageOwnerEmail = entity.getProperty("pageOwnerEmail").toString();
			    String _ownerEmail = entity.getProperty("ownerEmail").toString();
			    
				 
				 UserTimeline userTimeline = new UserTimeline( _userTimelineId , _name , _posts  ,_ownerEmail);
				 return userTimeline;
			}
			
		}

		return null;
	}


	

	

	
	
}

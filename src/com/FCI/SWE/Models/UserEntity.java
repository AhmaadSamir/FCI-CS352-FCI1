package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;
	private int userId ;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

	}
	public UserEntity()
	{
		name= "";
		email = "";
		password = "";
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	public void  setEmail(String email){
		this.email = email ;
	}

	public String getPass() {
		return password;
	}
	

	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static UserEntity getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new UserEntity(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will search for user in datastore
	 * 
	 * @param email
	 *            user email
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String email, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
		///	System.out.println(entity.getProperty("email").toString());
			if (entity.getProperty("email").toString().equals(email)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				return returnedUser;
			}
		}

		return null;
	}
	
	
	
	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		userId = DatabaseOperations.getLastId("users", "userId") + 1 ;

		Entity employee = new Entity("users", userId);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("userId", this.userId);
		employee.setProperty("password", this.password);
		datastore.put(employee);
        
		return true;

	}
	
	/**
	 * 
	 * This static method will check if 2 users are friends or not
	 * password This method will search for user in datastore
	 * 
	 * @param senderEmail
	 *            
	 * @param receiverEmail
	 *           
	 * @return freiends or not
	 */
	public static boolean isFriends(String senderEmail , String recevierEmail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterat over all friends
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("senderEmail").toString().equals(recevierEmail) &&
				entity.getProperty("recevierEmail").toString().equals(senderEmail) &&
				entity.getProperty("status").toString().equals("YES")){
					
				return true ;
			}
			else if (entity.getProperty("senderEmail").toString().equals(senderEmail) &&
					entity.getProperty("recevierEmail").toString().equals(recevierEmail) &&
					entity.getProperty("status").toString().equals("YES")){
						
					return true ;
			}
		}
		
		return false ;
	}
	
	public static boolean addFriendRequest(String senderEmail , String recevierEmail){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
	    int newFriendshipId = DatabaseOperations.getLastId("friends" , "friendshipId") + 1 ;
		Entity newRequest = new Entity("friends", newFriendshipId);
	

		newRequest.setProperty("senderEmail", senderEmail);
		newRequest.setProperty("recevierEmail", recevierEmail);
		newRequest.setProperty("friendshipId", newFriendshipId);
		newRequest.setProperty("status", "NO");
		datastore.put(newRequest);
		
		return true ;
		
	}
	
	
	/**
	 * AddAllFriendRequests this method add all users those want to be 
	 * friends with this user 
	 * 
	 * @return user in json format
	 */
	
	public boolean addAllFriendRequests() {
		///addAllTables();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterat over all friends
		for (Entity entity : pq.asIterable()) {
		
			if ( entity.getProperty("recevierEmail").toString().equals(getEmail()) &&
				entity.getProperty("status").toString().equals("NO")){
					
				entity.setProperty("status" , "YES");
				datastore.put(entity);
			}
		}
		

		return true;

	}
	
	
	
	/**
	 * 
	 * @param senderEmail
	 * @param recevierEmail
	 * @param message
	 * @return
	 */
	
	 
	public static boolean addMessage(String senderEmail , String recevierEmail , String message){
	  
	  DatastoreService datastore = DatastoreServiceFactory
	    .getDatastoreService();

	  Query gaeQuery = new Query("message");
	  PreparedQuery pq = datastore.prepare(gaeQuery);
	  
	     int newMessage = DatabaseOperations.getLastId("message","messageID") + 1 ;
	  Entity newRequest = new Entity("message", newMessage);
	 

	  newRequest.setProperty("senderEmail", senderEmail);
	  newRequest.setProperty("recevierEmail", recevierEmail);
	  newRequest.setProperty("message", message);
	  newRequest.setProperty("messageID", newMessage);
	  datastore.put(newRequest);
	  
	  return true ;
	  
	 }
	
	public void addAllTables(){
		
		
		
		Post post = new Post(4,"a","happyFa45", "private" 
				,"ah ya user ya gamel xD (ahmed samir)" , 50000 , "None");
		
		post.savePost();
		ArrayList<Post>posts = new ArrayList<Post>();
		posts.add(post);
		
		Hashtag hashtag = new Hashtag(2,posts,"kaizen");
		hashtag.saveHashtag();
		
		 ArrayList<UserEntity> users = new ArrayList<UserEntity>();
		 UserEntity user1 = new UserEntity();
		 user1 = user1.getUser("a", "a");
		 UserEntity user2 = new UserEntity();
		 user2 = user2.getUser("b", "b");
		 users.add(user1);
		 users.add(user2);
			    
		Page page = new Page(2,"3abelo we edelo" , users , posts , "a" , "Sport");
		page.savePage();
	
		UserTimeline userTimeline = new UserTimeline(1,"a" , posts , "Kaizer");
	    userTimeline.saveUserTimeline();
	    
	
		
		}
		
	
	 
	

	
	
}

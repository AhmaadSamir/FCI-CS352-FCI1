package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.DatabaseOperations;
import com.FCI.SWE.Models.Page;
import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.search.query.ExpressionParser.negation_return;
import com.google.appengine.labs.repackaged.org.json.JSONArray;


/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	public Service(){
		
	}
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public static String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		
		
		/// user email found 
		if( DatabaseOperations.isFound("users", "email", email)){
			
			JSONObject object = new JSONObject();
			object.put("Status", "Failed");
			return object.toString();
			
		}
		
		//save user now
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param email provided user email
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public static String loginService(@FormParam("email") String email,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(email, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();
		
	

	}
	
	
	/**
	 * sendFriendRequest Rest Service, this service will be called to make friend request process
	 * also will check if two friends are friends or not 
	 * @param senderEmail
	 * @param recevierEmail
	 * @return user in json format
	 */
	@POST
	@Path("/SendFriendRequest")
	public static String sendFriendRequestService(@FormParam("senderEmail") String senderEmail,
			@FormParam("recevierEmail") String recevierEmail) {
		
		
		UserEntity user = new UserEntity();
		JSONObject object = new JSONObject();
		
		///check if requered email is the user email
		if(senderEmail.equals(recevierEmail)){
			String requestResponse = "Request denied as you can`t add yourself ."; 
			object.put("requestResponse", requestResponse);
			return object.toString() ;
		}
		
		if(! DatabaseOperations.isFound("users", "email", recevierEmail) ){
			String requestResponse = "Request denied as this user not found ."; 
			object.put("requestResponse", requestResponse);
			return object.toString() ;
		}
		
		/// check if they are friends 
		if( user.isFriends( senderEmail ,  recevierEmail) ){
			String requestResponse = "Request denied as you are actually friends"; 
			object.put("requestResponse", requestResponse);
			return object.toString() ;
		}
		
		
		/// put them in table friends with status = NO
		user.addFriendRequest(senderEmail,recevierEmail);
		
		String requestResponse = "Request has been sent successfully ya user ya 7abibi ^__^ :D "; 
		object.put("requestResponse", requestResponse);

		return object.toString();

	}
	
	/**
	 * AddAllFriendRequests this method add all users those want to be 
	 * friends with this user 
	 * 
	 * @return user in json format  
	 */
	@POST
	@Path("/addAllFriendRequests")
	public static String addAllFriendRequestsService( @FormParam("recevierEmail") String recevierEmail) {
	
		JSONObject object = new JSONObject();
		object.put("requestResponse", "Failed");
		UserEntity user = new UserEntity();
		user.setEmail(recevierEmail);
		if ( user.addAllFriendRequests()){
			
			String requestResponse = "All users those sent you request became friends with you now\n"
					+ " congrats ya user ya zeft 7abibi :D \n "
					+ "in-shaa-el-Allah el phase gaya ha5alek te5tar el sadek ely enta bet7ebo  :DxD "; 
			
			
			
			object.put("requestResponse", requestResponse);
		}
		
		return object.toString();

	}
	
	@POST
	 @Path("/SendNewMessage")
	 public static String sendNewMessageService(@FormParam("senderEmail") String senderEmail,
	   @FormParam("recevierEmail") String recevierEmail , @FormParam("message") String message) {
		
		
	  
	  
	  UserEntity user = new UserEntity();
	  JSONObject object = new JSONObject();
	  
	  ///check if requered email is the user email
	  if(senderEmail.equals(recevierEmail)){
	   String requestResponse = "Request denied as you can`t send message to yourself ."; 
	   object.put("requestResponse", requestResponse);
	   return object.toString() ;
	  }
	  
	  if(! DatabaseOperations.isFound("users", "email", recevierEmail) ){
	   String requestResponse = "Request denied as this user not found ."; 
	   object.put("requestResponse", requestResponse);
	   return object.toString() ;
	  }
	  
	  /// check if they are friends 
	  if( !user.isFriends( senderEmail ,  recevierEmail) ){
	   String requestResponse = "Request denied as you are not friends"; 
	   object.put("requestResponse", requestResponse);
	   return object.toString() ;
	  }
	  
	  
	  /// put them in table friends with status = NO
	 if( user.addMessage(senderEmail,recevierEmail,message));
	  
	  String requestResponse = "Request has been sent successfully"; 
	  object.put("requestResponse", requestResponse);
	  

	  return object.toString();
	

	 }
	
	/***************************************** Timeline *************************************/
	
	/** 
	 * don`t forgot to add hashtag part
	 * 
	 * @param ownerEmail
	 * @param feeling
	 * @param content
	 * @param privacy
	 * @param sharedFromEmail
	 * @return
	 */
	@POST
	 @Path("/CreatePost")
	 public String createPostService(@FormParam("ownerEmail")  String ownerEmail ,
			 				  @FormParam("feeling") String feeling , 
			 				  @FormParam("content") String content,
			 				  @FormParam("privacy") String privacy,
			 				  @FormParam("sharedFromEmail") String sharedFromEmail) {
		
		Post post = new Post();
		
		post.setContent(content);
		post.setFeeling(feeling);
		post.setnLike(0);
		post.setOwnerEmail(ownerEmail);
		post.setSharedFromEmail(sharedFromEmail);
		post.setPrivacy(privacy);
		
		///save first 
		post.savePost();
					
		return showUserTimelineService(ownerEmail);
		
	}
	/**
	 * 
	 * @param ownerEmail
	 * @return
	 */
	 @POST
	 @Path("/showUserTimeline")
	 public String showUserTimelineService(@FormParam("ownerEmail") String ownerEmail) {
		
		Post post = new Post();		
		ArrayList<Post> posts = post.getAllPosts(ownerEmail);
		/// joson part
		JSONArray userPosts = new JSONArray();
		
		for(Post p :posts){
			JSONObject obj = new JSONObject();
			obj.put("postId" , p.getPostId());
			obj.put("ownerEmail" , p.getOwnerEmail());
			obj.put("feeling" , p.getFeeling());
			obj.put("privacy" , p.getPrivacy());
			obj.put("content" , p.getContent());
			obj.put("nLike" , p.getnLike());
			obj.put("sharedFromEmail" , p.getSharedFromEmail());
				
			userPosts.put(obj.toJSONString());
		}
		
		
		return userPosts.toString();

	}


}
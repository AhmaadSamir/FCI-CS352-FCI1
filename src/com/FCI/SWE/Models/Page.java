package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.search.query.QueryParser.primitive_return;

public class Page  {
	
	private int pageId;
	private String name ;
	private ArrayList<UserEntity> users ;
	private ArrayList<Post> posts ;
	private String pageOwnerEmail ;
	private String category ;
	
	
	/**
	 * Default Contractor
	 */
	
	public  Page()  {
		pageId = 0;
		name = "";
		users = new ArrayList<UserEntity>();
		posts = new ArrayList<Post>();
		pageOwnerEmail  = "";
		setCategory("");
		
	}
	
	/**
	 * 
	 * @param pageId
	 * @param name
	 * @param users
	 * @param posts
	 * @param pageOwnerEmail
	 */
	public  Page(int pageId ,String name , ArrayList<UserEntity> users ,
			     ArrayList<Post> posts   , String pageOwnerEmail , String category) {
		
		pageId = this.pageId;
		name = this.name ;
		users = this.users;
		posts = this.posts;
		pageOwnerEmail  = this.pageOwnerEmail;
		this.category = category;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPageId() {
		return pageId;
	}
	/**
	 * 
	 * @param pageId
	 */
	public void setPageId(int pageId) {
		this.pageId = pageId;
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
	public ArrayList<UserEntity> getUsers() {
		return users;
	}
	/**
	 * 
	 * @param users
	 */
	public void setUsers(ArrayList<UserEntity> users) {
		this.users = users;
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
	public String getPageOwnerEmail() {
		return pageOwnerEmail;
	}
	/**
	 * 
	 * @param pageOwnerEmail
	 */
	public void setPageOwnerEmail(String pageOwnerEmail) {
		this.pageOwnerEmail = pageOwnerEmail;
	}
	/**
	 * 
	 * @return total number of users those liked this page
	 */
	public int getNumberOfusers() {
		return users.size();
	}
	/**
	 * 
	 * @return
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * 
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	

	
	/**
	 * 
	 * @return
	 */
	
	public  boolean savePage(){
		
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		pageId = getLastPageID() + 1 ;

		Entity page = new Entity("pages", pageId);

		page.setProperty("pageId", this.pageId);
		page.setProperty("name", this.name);
		page.setProperty("users", this.users);
		page.setProperty("posts", this.posts);
		page.setProperty("pageOwnerEmail", this.pageOwnerEmail);
		page.setProperty("category", this.category);
		
		
		datastore.put(page);
        
		return true;
		
	}
	/**
	 * 
	 * @return last page id 
	 */
	public static int getLastPageID() {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all pages
		int lastId = 1 ;
		for (Entity entity : pq.asIterable()) {
		
			lastId = Integer.parseInt( entity.getProperty("PageId").toString() );
		}
		
		return lastId ;

	}
	/**
	 * 
	 * @param _pageId
	 * @return
	 */
	public static boolean foundPage(int _pageId)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all pages
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("pageId").toString().equals(_pageId) ){
				return true ;
			}
		}
		
		return false ;
	}
	
	/**
	 * 
	 * @param _pageId
	 * @return
	 */
	public static Page getPage(int _pageId) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//Iterate over all pages
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty("pageId").toString().equals(_pageId) ){
				
			    String _name = entity.getProperty("name").toString();
			    ArrayList<UserEntity> _users = ( ArrayList<UserEntity> )entity.getProperty("users");
			    ArrayList<Post> _posts = ( ArrayList<Post> )entity.getProperty("posts");
			    String _pageOwnerEmail = entity.getProperty("pageOwnerEmail").toString();
			    String _category = entity.getProperty("category").toString();
				 
				 Page page = new Page( _pageId , _name , _users , _posts   ,  _pageOwnerEmail ,  _category);
				 return page;
			}
			
		}

		return null;
	}


	

	

}

package com.FCI.SWE.Models;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class DatabaseOperations {
	/***
	 * 
	 * @param tableName
	 * @param key
	 * @param id
	 * @return True if found this id in the tableName if found in database 
	 */
	public static boolean isFound(String tableName , String key , String id){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterat over all posts
		for (Entity entity : pq.asIterable()) {
		
			if (entity.getProperty(key).toString().equals(id) ){
				return true ;
			}
		}
		
		return false ;
	}
	/***
	 * 
	 * @param tableName
	 * @param key
	 * @return Last Table id found in database
	 */
	public static  int getLastId(String tableName , String key) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//iterat over all users
		int lastId = 1 ;
		for (Entity entity : pq.asIterable()) {
		
			lastId = Integer.parseInt( entity.getProperty(key).toString() );
		}
		
		return lastId ;

	}
	
	

}

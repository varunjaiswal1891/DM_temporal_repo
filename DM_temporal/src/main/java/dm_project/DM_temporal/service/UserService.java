package dm_project.DM_temporal.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import dm_project.DM_temporal.model.User;

public class UserService {

	public static boolean addUserService(User u1)
	{
		
        try 
        {
        	 MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        	 DB db = mongoClient.getDB( "varundb" );
        	 
	          System.out.println("Connect to database successfully varun");
	          DBCollection collection = db.getCollection("temporal_collection");
	          

	        		//insert new collection with new userID
	        	  JSONObject json = new JSONObject();	
	  	          json.put("username",u1.getUsername());
	  	          json.put("password",u1.getPassword());
	  	          

		          String s1=json.toString();
		          DBObject dbObject = (DBObject)JSON.parse(s1);
		          collection.insert(dbObject);
		          System.out.println("insert is done");
	  	         
		          System.out.println("After insertion");
	  	          
	  	          DBCursor cursorDocJSON = collection.find();
		          while (cursorDocJSON.hasNext()) {
	        			System.out.println(cursorDocJSON.next());
	        		}
		          cursorDocJSON.close();
	  	          
        	 return true;
        	 

        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return false;
    }//method ends here
	
	
	
	public static boolean loginUserService(User u1)
	{
		
        try 
        {
        	 MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        	 DB db = mongoClient.getDB( "varundb" );
        	 
	          System.out.println("Connect to database successfully varun");
	          DBCollection collection = db.getCollection("temporal_collection");

	          BasicDBObject andQuery = new BasicDBObject();
	          List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	          obj.add(new BasicDBObject("username",u1.getUsername()));
	          obj.add(new BasicDBObject("password",u1.getPassword()));
	          andQuery.put("$and", obj);

	          System.out.println(andQuery.toString());

	          DBCursor cursor = collection.find(andQuery);
	          while (cursor.hasNext()) {
	          	System.out.println(cursor.next());
	          	System.out.println("login success.");
	          	return true;
	          }
	          cursor.close();
        	 
        	 

        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return false;
    }//login method ends here
	

	
	
	public static String getUserNameService(String uname)
	{
		
        try 
        {
        	 MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        	 DB db = mongoClient.getDB( "varundb" );
        	 
        	  DBObject result = null;
	          System.out.println("Connect to database successfully varun");
	          DBCollection collection = db.getCollection("temporal_collection");
	          
	          BasicDBObject whereQuery = new BasicDBObject();
	          whereQuery.put("username",uname);
	          DBCursor cursor = collection.find(whereQuery);
	          while(cursor.hasNext()) {
	        	  result=cursor.next();
	              
	          }
	          
	          cursor.close();
        	 return result.toString();
        	 
        	 

        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return null;
    }//getusername method ends here
	
	
	

	public static String getCurrentStatuservice(String uname)
	{
		
        try 
        {
        	 MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        	 DB db = mongoClient.getDB( "varundb" );
        	 
        	  DBObject result = null;
	          System.out.println("Connect to database successfully varun");
	          DBCollection collection = db.getCollection("status_collection");
	          
	          BasicDBObject whereQuery = new BasicDBObject();
	          whereQuery.put("username",uname);
	          DBCursor cursor = collection.find(whereQuery);
	          while(cursor.hasNext()) {
	        	  result=cursor.next();
	        	  //System.out.println(result);
	          }
	          
	          cursor.close();
        	 return result.toString();
        	 
        	 

        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        return null;
    }//getCurrentStatus method ends here
	
	
	
	
	
}//class ends here

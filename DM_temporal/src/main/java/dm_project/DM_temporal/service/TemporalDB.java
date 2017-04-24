package dm_project.DM_temporal.service;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import org.bson.BasicBSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.util.JSON;

import dm_project.DM_temporal.model.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class TemporalDB{
	private static MongoClient mongoclient;
	private static DB db;
	private static DBCollection collection;
	private static String collectionHistory;
	private static String dbName;
	public TemporalDB(String dbName){
		TemporalDB.dbName = dbName;
	}
	

	public static boolean insertIntoDB(String collection_name,HashMap<String, String> hm){
		String s1_uname="";
		try{
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection(collection_name);
			collectionHistory = collection_name +"History";
			BasicDBObject bdb = new BasicDBObject();
			Date d = new Date();
			Iterator<Entry<String, String>> it = hm.entrySet().iterator();
	        while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        String key=pair.getKey().toString();
		        String value=pair.getValue().toString();
		        if(key.equals("username"))
		        {
		        	s1_uname=value;
		        }
				bdb.put(key,value);
		        it.remove(); // avoids a ConcurrentModificationException
	        }
	        long l1=d.getTime();
            bdb.put("validFrom",l1);
			bdb.put("validTo",null);
			
			BasicDBObject query = new BasicDBObject();
			query.append("username",s1_uname);
			collection.remove(query);
			//remove old row and insert new row in status_collection
			collection.insert(bdb);
			System.out.println("inserted into normal");
			
			
	          BasicDBObject andQuery = new BasicDBObject();
	          List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	          obj.add(new BasicDBObject("username",s1_uname));
	          obj.add(new BasicDBObject("validFrom",new BasicDBObject("$lt",d.getTime())));
	          
	          andQuery.put("$and", obj);
			
			
			int flag=0;
			
			collection = db.getCollection(collectionHistory);
	        String map = "function(){emit({username:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		    String reduce = "function(key,values){var result = previous(key,values);return result;}";
		    MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,andQuery);
		    MapReduceOutput out = collection.mapReduce(cmd);
		    DBObject newDocument = new BasicDBObject();
		    String s1_json="";
		    
		    for (DBObject o : out.results()) {
		    	newDocument=o;
		    	s1_json=o.toString();
		    	System.out.println(o.toString());
		    	flag=1;
		    }
		   collection.insert(bdb);
		   System.out.println("newDocument="+newDocument );	
		   String uname1="";
		   long timeFrom;
		
		   if(flag==1)
		   {
			
			   
			   JSONObject jsonObject = new JSONObject(s1_json);
		        JSONObject newJSON = jsonObject.getJSONObject("_id");
		       // System.out.println(newJSON.toString());
		        jsonObject = new JSONObject(newJSON.toString());
			      uname1=jsonObject.getString("username");
			      System.out.println("uname===="+uname1);
			      
			      JSONObject jsonObject2 = new JSONObject(s1_json);
			        JSONObject newJSON2 = jsonObject2.getJSONObject("value");
			       // System.out.println(newJSON.toString());
			        jsonObject2 = new JSONObject(newJSON2.toString());
				    timeFrom=jsonObject2.getLong("validFrom");  
				      System.out.println("valid from===="+timeFrom);
			     
			    
			   
			   //update old row ka validTo here
			    DBObject selectQuery = new BasicDBObject("username",uname1);
			    ((BasicDBObject) selectQuery).append("validFrom",timeFrom);
			   
			    BasicDBObject updateFields = new BasicDBObject();
			    updateFields.put("validTo",l1);
			    
			    DBObject updateQuery = new BasicDBObject();
			    updateQuery.put("$set", updateFields);
			    collection.update(selectQuery, updateQuery, true, true); 
		   }
		    System.out.println("flag="+flag);
			return true;
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	
	
    public static Message getFirst(String username)
    {
         Message m1=new Message();
         
     	try{
     		
     		System.out.println("insdod try catch");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
		   BasicDBObject whereQuery = new BasicDBObject();
           whereQuery.put("username",username);
	          
           String map = "function(){emit({userID:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		   String reduce = "function(key,values){var result = first(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,whereQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
	
	
    public static Message getLast(String username)
    {
         Message m1=new Message();
         
     	try{
     		
     		System.out.println("inside  try catch of last");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
           BasicDBObject andQuery = new BasicDBObject();
	          List<BasicDBObject> obj33 = new ArrayList<BasicDBObject>();
	          obj33.add(new BasicDBObject("username",username));
	          obj33.add(new BasicDBObject("validTo",null));
	          
	          andQuery.put("$and", obj33);
           
           
           
           //map:function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}
           String map = "function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}";
		   String reduce = "function(key,values){return values;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,andQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    final JSONObject m112 = obj.getJSONObject("_id");
		    
		    m1.setMsg(m112.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
	
    
    

    public static Message getPrevious(String username,long date)
    {
         Message m1=new Message();
         
     	try{
     		mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			BasicDBObject getQuery = new BasicDBObject();
		    getQuery.put("validFrom", new BasicDBObject("$lt",date));
			getQuery.put("username",username);
		   
           
           
           //map:function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}
           String map = "function(){emit({userID:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		   String reduce = "function(key,values){var result = previous(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,getQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
    
    public static Message getNext(String username,long date)
    {
         Message m1=new Message();
         
     	try{
     		//date=long(1491868800000);
     		System.out.println("inside  try catch of last");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			BasicDBObject getQuery = new BasicDBObject();
		    getQuery.put("validFrom", new BasicDBObject("$gt",date));
			getQuery.put("username",username);
		   
           
           
           //map:function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}
           String map = "function(){emit({userID:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		   String reduce = "function(key,values){var result = next(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,getQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
    
    
    public static Message previous_Day(String username)
    {
         Message m1=new Message();
         
     	try{
     		
     		System.out.println("insdod try catch");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
		   BasicDBObject whereQuery = new BasicDBObject();
           whereQuery.put("username",username);
	          
           String map = "function(){emit({userID:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		   String reduce = "function(key,values){var result = previousDay(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,whereQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
	
    
    
    public static Message previous_Hour(String username)
    {
         Message m1=new Message();
         
     	try{
     		
     		System.out.println("insdod try catch");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
		   BasicDBObject whereQuery = new BasicDBObject();
           whereQuery.put("username",username);
	          
           String map = "function(){emit({userID:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		   String reduce = "function(key,values){var result = previousHour(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,whereQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
    
    public static Message previous_Month(String username)
    {
         Message m1=new Message();
         
     	try{
     		
     		System.out.println("insdod try catch");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
		   BasicDBObject whereQuery = new BasicDBObject();
           whereQuery.put("username",username);
	          
           String map = "function(){emit({userID:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		   String reduce = "function(key,values){var result = previousMonth(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,whereQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
    
    
    public static ArrayList<Message> evolutionHistory(String username)
    {
    	ArrayList<Message> al=new ArrayList<Message>();
         
         
     	try{
     		
     		System.out.println("insdod try catch");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
		   BasicDBObject whereQuery = new BasicDBObject();
           whereQuery.put("username",username);
	          
           String map = "function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}";
		   String reduce = "function(key,values){return values;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,whereQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());	
		    	Message m1=new Message();
		    	final JSONObject obj = new JSONObject(json_data);
			    final JSONObject m11 = obj.getJSONObject("value");
			    final JSONObject m112 = obj.getJSONObject("_id");
			    
			    m1.setMsg(m112.getString("msg"));
			    m1.setValidFrom(m11.getLong("validFrom"));
			    al.add(m1);
		    }
		    
		    
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return al;
    }
    
    
    
    public static ArrayList<Message> evolution_column(String username,long date)
    {
         
    	ArrayList<Message> al=new ArrayList<Message>();
         
     	try{
     		//date=long(1491868800000);
     		System.out.println("inside  try catch of last");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			BasicDBObject getQuery = new BasicDBObject();
		    getQuery.put("validFrom", new BasicDBObject("$lt",date));
			getQuery.put("username",username);
		   
           
           
           //map:function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}
           String map = "function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}";
		   String reduce = "function(key,values){return values;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,getQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		    for (DBObject o : out.results()) {
			    
		    	json_data=o.toString();
		    	System.out.println(o.toString());	
		    	Message m1=new Message();
		    	final JSONObject obj = new JSONObject(json_data);
			    final JSONObject m11 = obj.getJSONObject("value");
			    final JSONObject m112 = obj.getJSONObject("_id");
			    
			    m1.setMsg(m112.getString("msg"));
			    m1.setValidFrom(m11.getLong("validFrom"));
			    al.add(m1);
		    }
     	}  
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return al; 
    }
    
    
    
    
    public static Message getEvolutionFirst(String username)
    {
         Message m1=new Message();
         
     	try{
     		
     		System.out.println("insdod try catch");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
		   BasicDBObject whereQuery = new BasicDBObject();
           whereQuery.put("username",username);
	          
           String map = "function(){emit({userID:this.username},{validFrom:this.validFrom,msg:this.msg});}";
		   String reduce = "function(key,values){var result = evolutionFirst(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,whereQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
    
    
    public static Message getEvolutionLast(String username)
    {
         Message m1=new Message();
         
     	try{
     		
     		System.out.println("insdod try catch");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			
		   BasicDBObject whereQuery = new BasicDBObject();
           whereQuery.put("username",username);
	          
           String map = "function(){emit({userID:this.username},{validFrom:this.validFrom,msg:this.msg});}";
		   String reduce = "function(key,values){var result = evolutionLast(key,values);return result;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,whereQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    
		    final JSONObject obj = new JSONObject(json_data);
		    final JSONObject m11 = obj.getJSONObject("value");
		    
		    m1.setMsg(m11.getString("msg"));
		    m1.setValidFrom(m11.getLong("validFrom"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1; 
    }
    
    
    
    
    public static ArrayList<Message> next_scale(String username,long date1,long date2)
    {
         
         ArrayList<Message> al=new ArrayList<Message>();
     	try{
     		
     		System.out.println("inside  try catch of last");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
			/*System.out.println("date1="+date1+"date2="+date2);
			BasicDBObject getQuery = new BasicDBObject();
		    getQuery.put("validFrom", new BasicDBObject("$gt",date1));
		    getQuery.put("validFrom", new BasicDBObject("$lt",date2));
		    getQuery.put("username",username);
		   */
		    BasicDBObject andQuery = new BasicDBObject();
	          List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	          obj.add(new BasicDBObject("username",username));
	          obj.add(new BasicDBObject("validFrom",new BasicDBObject("$lt",date2)));
	          obj.add(new BasicDBObject("validFrom",new BasicDBObject("$gt",date1)));
	          andQuery.put("$and", obj);
           
           
           //map:function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}
           String map = "function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}";
		   String reduce = "function(key,values){return values;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,andQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";
		   
		    for (DBObject o : out.results()) {

		    	json_data=o.toString();
		    	System.out.println(o.toString());	
		    	Message m1=new Message();
		    	final JSONObject obj1 = new JSONObject(json_data);
			    final JSONObject m11 = obj1.getJSONObject("value");
			    final JSONObject m112 = obj1.getJSONObject("_id");
			    
			    m1.setMsg(m112.getString("msg"));
			    m1.setValidFrom(m11.getLong("validFrom"));
			    al.add(m1);
		}
     	}	    
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return al;
    }
	
    
    
    public static Message column_timestamp(String username,String status)
    {
         Message m1=new Message();
         //ArrayList<Message> al=new ArrayList<Message>();
     	try{
     		
     		System.out.println("inside  try catch of last");
			mongoclient = new MongoClient("localhost",27017);
			db = mongoclient.getDB(dbName);
			collection = db.getCollection("status_collectionHistory");
			
		    BasicDBObject andQuery = new BasicDBObject();
	          List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	          obj.add(new BasicDBObject("username",username));
	          obj.add(new BasicDBObject("msg",new BasicDBObject("$regex", status)));

	          andQuery.put("$and", obj);
           
           
           //map:function(){emit({userID:this.username,msg:this.msg},{validFrom:this.validFrom});}
           String map = "function(){emit({userID:this.username,validFrom:this.validFrom,msg:this.msg},{validTo:this.validTo});}";
		   String reduce = "function(key,values){return values;}";
		   MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,andQuery);
		   MapReduceOutput out = collection.mapReduce(cmd);
			    
		    String json_data="";

		    for (DBObject o : out.results()) {
		    
		    	json_data=o.toString();
		    	System.out.println(o.toString());		    
		    }
		    final JSONObject obj1 = new JSONObject(json_data);
		    final JSONObject m11 = obj1.getJSONObject("value");
		    final JSONObject m112 = obj1.getJSONObject("_id");
		    //if(m11.getLong("validTo")==0)
		    //{
		   // System.out.println("rrrrrrrr"+m11.("validTo"));
		        m1.setValidFrom(m112.getLong("validFrom"));
			    m1.setValidTo(m11.getLong("validTo"));
			    m1.setMsg(m112.getString("msg"));
			    System.out.println("gggggggggg"+m1.getMsg());
			    	
		    
		
     	}	    
		catch(Exception e){
			System.out.println(e.getMessage());
			}    
      return m1;
    }
	
    
    
    
    
}
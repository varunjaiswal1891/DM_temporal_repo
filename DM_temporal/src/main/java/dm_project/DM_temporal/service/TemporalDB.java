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
		  //String uname1=(String) newDocument.get(s1_uname);
		   //long l2=(long) newDocument.get("validFrom");
		  // System.out.println("uname===="+uname1);
		   //System.out.println("valid from===="+l2);
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
	
};
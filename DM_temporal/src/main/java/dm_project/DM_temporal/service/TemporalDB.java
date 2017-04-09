package dm_project.DM_temporal.service;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

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
            bdb.put("validFrom",d.getTime());
			bdb.put("validTo",null);
			collection.insert(bdb);
			System.out.println("inserted into normal");
			
			
	          BasicDBObject andQuery = new BasicDBObject();
	          List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	          obj.add(new BasicDBObject("username",s1_uname));
	          obj.add(new BasicDBObject("validFrom",new BasicDBObject("$lt",d.getTime())));
	          
	          andQuery.put("$and", obj);
			
			
			
			
			collection = db.getCollection(collectionHistory);
	        String map = "function(){emit({username:this.username},{msg:this.msg,validFrom:this.validFrom});}";
		    String reduce = "function(key,values){var result = previous(key,values);return result;}";
		    MapReduceCommand cmd = new MapReduceCommand(collection,map,reduce,null,MapReduceCommand.OutputType.INLINE,andQuery);
		    MapReduceOutput out = collection.mapReduce(cmd);
		    for (DBObject o : out.results()) {
		    	System.out.println(o.toString());
		    }
		   collection.insert(bdb);
		    System.out.println("inserted into history");
			return true;
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
};
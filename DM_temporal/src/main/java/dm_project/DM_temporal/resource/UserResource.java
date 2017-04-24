package dm_project.DM_temporal.resource;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dm_project.DM_temporal.model.Message;
import dm_project.DM_temporal.model.User;
import dm_project.DM_temporal.service.UserService;
import dm_project.DM_temporal.service.TemporalDB;

@WebService()
@Path("/user")
public class UserResource {

	TemporalDB d1=new TemporalDB("varundb");
	@POST
    @Path("/signup")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String addUser(User user) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("Inside sign up resource");
			if(UserService.addUserService(user))
			{
			
	           return "sucess signup";       
				
			}

		return null;
	}//adduser method ends here
	
	@POST
    @Path("/login")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
    public String userLogin(User user) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("Inside sign up resource");
			if(UserService.loginUserService(user))
			{
			
	           return "sucess login";       
				
			}

		return null;
	}//adduser method ends here
	
	
	@GET
    @Path("/retriveName")
	@Produces({MediaType.TEXT_PLAIN})
    public String getUserName(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("Inside get username resource");
		String result=UserService.getUserNameService(uname);
			
		return result;
	}//get username method ends here
	
	
	@GET
    @Path("/currentStatus")
	@Produces({MediaType.APPLICATION_JSON})
    public String getCurrentStatus(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("Inside get username resource");
		String result=UserService.getCurrentStatuservice(uname);
		System.out.println(result);	
		return result;
	}//get username method ends here
	
	@POST
    @Path("/poststatus")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
    public String postNewStatus(String msg,@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException, ParseException{
		
		
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("msg",msg);
		hm.put("username", uname);
		
		
		System.out.println("Inside sign up resource");
			if(TemporalDB.insertIntoDB("status_collection",hm))
			{
			
	           return "sucess login";       
				
			}
		return null;
	}//poststus method ends here
	
	
	
	@GET
    @Path("/first_operator")
	@Produces({MediaType.APPLICATION_JSON})
    public Message getFirst(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.getFirst(uname); 
	}
	
	@GET
    @Path("/last_operator")
	@Produces({MediaType.APPLICATION_JSON})
    public Message getLast(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.getLast(uname); 
	}
	
	
	@GET
    @Path("/previous_operator")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
    public Message getPrevious1(@CookieParam("ID") String uname,@CookieParam("date1") long date1) throws JsonParseException, JsonMappingException, IOException, ParseException{
	
		return TemporalDB.getPrevious(uname,date1); 
	}
	
	@GET
    @Path("/Varun_operator")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
    public Message getNext1(@CookieParam("ID") String uname,@CookieParam("date1") long date1) throws JsonParseException, JsonMappingException, IOException, ParseException{
		System.out.println("here====="+uname);
		System.out.println("date ms="+date1);
		return TemporalDB.getNext(uname,date1); 
	}
	
	@GET
    @Path("/previous_Day_operator")
	@Produces({MediaType.APPLICATION_JSON})
    public Message getPreviousDay(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.previous_Day(uname); 
	}
	
	@GET
    @Path("/previous_Hour_operator")
	@Produces({MediaType.APPLICATION_JSON})
    public Message getPreviousHour(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.previous_Hour(uname); 
	}
	
	@GET
    @Path("/previous_Month_operator")
	@Produces({MediaType.APPLICATION_JSON})
    public Message getPreviousMonth(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.previous_Month(uname); 
	}
	
	@GET
    @Path("/give_evolution_history")
	@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Message> getEvolutionHistory(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.evolutionHistory(uname); 
	}
	
	
	@GET
    @Path("/get_evolution_column")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Message> getEvolution_column(@CookieParam("ID") String uname,@CookieParam("date1") long date1) throws JsonParseException, JsonMappingException, IOException, ParseException{
		System.out.println("here====="+uname);
		System.out.println("date ms="+date1);
		return TemporalDB.evolution_column(uname, date1);
	}
	
	
	
	@GET
    @Path("/get_evolution_first")
	@Produces({MediaType.APPLICATION_JSON})
    public Message getEvolutionFIRST(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.getEvolutionFirst(uname); 
	}
	
	
	@GET
    @Path("/get_evolution_last")
	@Produces({MediaType.APPLICATION_JSON})
    public Message getEvolutionLAST(@CookieParam("ID") String uname) throws JsonParseException, JsonMappingException, IOException{
		return TemporalDB.getEvolutionLast(uname);
	}
	
	
}//class ends here

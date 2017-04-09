package dm_project.DM_temporal.resource;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
	}//adduser method ends here
	
	
	
	
}//class ends here

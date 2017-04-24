package dm_project.DM_temporal.model;

public class Message {
	
	private String username;
	private String msg;
	private long validFrom;
	private long validTo;
	
	public Message()
	{
		
	}
	
	Message(String username,String msg,long validFrom,long validTo)
	{
		this.username=username;
		this.msg=msg;
		this.validFrom=validFrom;
		this.validTo=validTo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(long validFrom) {
		this.validFrom = validFrom;
	}

	public long getValidTo() {
		return validTo;
	}

	public void setValidTo(long validTo) {
		this.validTo = validTo;
	}
	

}

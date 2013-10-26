package asu.edu.sbs.domain;

public class Transaction {

	private String transactionID;
	private String description;
	private String time;

	public void setTransactionID(String id)
	{	transactionID =id;  }

	public void setDescription(String desc)
	{	description=desc;  }

	public void setTime(String time)
	{	this.time=time;  }
	
	
	public String getTransactionID()
	{	return transactionID; }
	
	public String getDescription()
	{	return description; }
	
	public String getTime()
	{	return time; }
	
		
}

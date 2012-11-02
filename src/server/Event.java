package server;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable{
	private Date StartDate;
	private Date EndDate;
	private String text;
	private String AccessCtrl;
	

	public Event(Date StartDate, Date EndDate, String text, String ac) {
		// TODO Auto-generated constructor stub
		this.StartDate = StartDate;
		this.EndDate = EndDate;
		this.text = text ;
		this.AccessCtrl =ac ;
	}
	
	


public Date getStartDate() {
	return StartDate;
}




public Date getEndDate() {
	return EndDate;
}






public String getText() {
	return text;
}





public String getAccessCtrl() {
	return AccessCtrl;
}







public static void main(){
	
}



}


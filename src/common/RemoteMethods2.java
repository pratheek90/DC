package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface RemoteMethods2 extends Remote {

	public String ScheduleEvent (Date StartDate, Date EndDate, String UserList, String text, String access_specifier) throws RemoteException;
	
	public String getname() throws RemoteException ;
	
	public String RetrieveEvents(String User, Date StartDate, Date EndDate) throws RemoteException;
	
	public Date[] listOfEvents(String text) throws RemoteException;

}

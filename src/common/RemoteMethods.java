package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMethods extends Remote {

	/** Method to create a new Calendar object. */
	public String CreateCalendar(String arg) throws RemoteException;

	public String List() throws RemoteException;
	
	public RemoteMethods2 ConnectCalendar(String string) throws RemoteException;
	
}

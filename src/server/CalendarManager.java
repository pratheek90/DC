package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import common.RemoteMethods;
import common.RemoteMethods2;


public class CalendarManager extends UnicastRemoteObject implements
		RemoteMethods {

	public CalendarManager() throws RemoteException {

	}

	public static Vector<CalendarClass> CalendarList = new Vector<CalendarClass>();
	public static Vector<String> UserList = new Vector<String>();

	// Verify if User exists, if not then create one.
	public String CreateCalendar(String name) throws RemoteException {

		if (UserList.contains(name)) {
			return "User exists!";
		} else {

			CalendarClass cal = new CalendarClass(name);
			UserList.add(name);
			CalendarList.add(cal);

			String name_set_as = cal.getname();
			return name_set_as;
		}
	}

	// Connects to Calendar Object by returning an object of class Calendar.
	public RemoteMethods2 ConnectCalendar(String user) {

		if (UserList.contains(user)) {
			int index = UserList.indexOf(user);
			return (RemoteMethods2) CalendarList.get(index);
		}

		else {
			return null;
		}
	}

	// List all the Users
	public String List() throws RemoteException {
		String users = "";

		int i = 0;

		for (i = 0; i < UserList.size(); i++) {
			users = users + "\n" + UserList.get(i);
		}
		return users;
	}

	public static void main(String[] args) {

		System.out.println("###### SERVER is now running! ######" + "\n");
		System.out
				.println("###### DISTRIBUTED CALENDAR ~ CS675 ~ PRATHEEK TOTIGER ######"
						+ "\n");
		try {
			Registry r = LocateRegistry.getRegistry();
			CalendarManager CalMgr = new CalendarManager();
			r.bind("Connect", CalMgr);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

}

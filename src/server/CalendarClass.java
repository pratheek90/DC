package server;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

import common.RemoteMethods2;


public class CalendarClass extends UnicastRemoteObject implements
RemoteMethods2 {
	String owner;
	ArrayList<Event> E = new ArrayList<Event>();

	// Constructor
	public CalendarClass(String name) throws RemoteException {
		owner = name;

	}

	// Accessors
	public String getname() {
		return owner;
	}
	
	public Date[] listOfEvents(String text){
		int userIndex = CalendarManager.UserList.indexOf(text);
		CalendarClass CalObj = CalendarManager.CalendarList.get(userIndex);
		
		Date[] allevents = new Date[CalObj.E.size()];
		
		for (int i = 0; i < CalObj.E.size(); i++) {
		Event EventIndex = CalObj.E.get(i);
		allevents[i] = EventIndex.getStartDate();
		
		}
		
		return allevents;
		

	}

	// Schedule Event function definition
	@Override
	public String ScheduleEvent(Date StartDate, Date EndDate, String userList,
			String text, String access_specifier) throws RemoteException {
		int userIndex = 0;
		int i = 0;
		String output = "";
		String[] arrayUserList = userList.split(",");

		Event NewEvent = new Event(StartDate, EndDate, text, access_specifier);

		if (userList.isEmpty()) {
			E.add(NewEvent);
			output = "Event Created for the Owner";
			
			
		} else {

			for (i = 0; i < arrayUserList.length; i++) {

				userIndex = CalendarManager.UserList.indexOf(arrayUserList[i]);

				if (userIndex >= 0) {
					CalendarClass CalObj = CalendarManager.CalendarList.get(userIndex);
					
					Boolean Status = checkAvailability(CalObj, StartDate,
							EndDate);
					if (Status == true) {
						CalObj.E.add(NewEvent);
						output = "Event Scheduled!";
						

						//*************************************************
						//******************File Write ********************
						//*************************************************
						BufferedWriter bufferedWriter = null;


						try {

							//Construct the BufferedWriter object
							bufferedWriter = new BufferedWriter(new FileWriter("/Users/Pratheek/Desktop/"+ arrayUserList[i] +".txt", true));

							//Start writing to the output stream
							bufferedWriter.write(text +" is scheduled from "+ StartDate + " to " +EndDate + "Permission:"+access_specifier);
							bufferedWriter.newLine();
							System.out.println("######### File Written and Saved to Desktop! #########");


						} catch (FileNotFoundException ex) {
							ex.printStackTrace();
						} catch (IOException ex) {
							ex.printStackTrace();
						} finally {
							//Close the BufferedWriter
							try {
								if (bufferedWriter != null) {
									bufferedWriter.flush();
									bufferedWriter.close();
								}
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}

						//*************************************************
						//*************************************************
						
						
					} else
						output = "Attention!! Time collision";
				} else {
					output = "User not found!";
				}
			}

		}
		return output;
	}

	// Retrieve Events of a particular user
	public Boolean checkAvailability(CalendarClass CalObj, Date new_s,
			Date new_e) {
		Event EventIndex = null;

		for (int i = 0; i < CalObj.E.size(); i++) {
			EventIndex = CalObj.E.get(i);

			Date s_time = EventIndex.getStartDate();
			Date e_time = EventIndex.getEndDate();

			if (new_s.before(s_time) && new_e.before(e_time) && new_e.after(s_time))
				return false;

			else if (new_s.after(s_time) && new_s.before(e_time) && new_e.after(e_time)) 
				return false;


		}
		return true;

	}

	public String RetrieveEvents(String User, Date StartDate, Date EndDate) {
		Event EventIndex;
		int UserIndex = 0;

		int i = 0;
		String EventList = "";

		// List all events immaterial of users
		/*if (User == "") {
			for (i = 0; i < E.size(); i++) {
				EventIndex = E.get(i);
				EventList = EventList + "\n" + EventIndex.getText() + "from"
						+ EventIndex.getStartDate() + "to"
						+ EventIndex.getEndDate() + " Permission:"
						+ EventIndex.getAccessCtrl() + "\n";
			}
			return EventList;
		}*/

		// Lists all events for the User passed in the argument to
		// RetrieveEvents



		UserIndex = CalendarManager.UserList.indexOf(User);
		CalendarClass CalObj = CalendarManager.CalendarList.elementAt(UserIndex);

		if(this.owner == CalObj.owner)
		{

			for (i = 0; i < CalObj.E.size(); i++) {
				EventIndex = CalObj.E.get(i);
				Date existStart = EventIndex.getStartDate();
				Date existEnd = EventIndex.getEndDate();

				if(StartDate.before(existStart) && EndDate.after(existEnd))
				{
					EventList = EventList + EventIndex.getText() + " from "
							+ EventIndex.getStartDate() + " to "
							+ EventIndex.getEndDate() + " Permission:"
							+ EventIndex.getAccessCtrl() + "\n";
				}
				else
					return "No Events Scheduled between these dates";
			}
			return EventList;
		}
		else
		{
			for (i = 0; i < CalObj.E.size(); i++) {
				EventIndex = CalObj.E.get(i);
				Date existStart = EventIndex.getStartDate();
				Date existEnd = EventIndex.getEndDate();

				if(StartDate.before(existStart) && EndDate.after(existEnd) && EventIndex.getAccessCtrl().equals("public"))
				{
					
				
						EventList = EventList + EventIndex.getText() + " from "
								+ EventIndex.getStartDate() + " to "
								+ EventIndex.getEndDate() + " Permission:"
								+ EventIndex.getAccessCtrl() + "\n";
					
				}
				else
					return "No Events Scheduled between these dates";
			
		}
		return EventList;
	}
}

}

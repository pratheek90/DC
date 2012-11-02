package client;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import common.RemoteMethods;
import common.RemoteMethods2;



public class Client {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		Date[] allevents = new Date[20];
		
		
		
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}

		String host = (args.length < 1) ? null : args[0];
		try{
			
			
			Registry regisrty_entry = LocateRegistry.getRegistry(host);
			RemoteMethods remoteConnect = (RemoteMethods)regisrty_entry.lookup("Connect");

			//Create a calendar object
			System.out.println("###### Client Activated! ######" + "\n");
			System.out.println("Enter your name:");
			String CurrentUser = input.nextLine();

			//Create a calendar for the current user
			String cal_response = remoteConnect.CreateCalendar(CurrentUser);
			System.out.println("########## Create Calendar returned:" + cal_response + "##########" + "\n");
			//Connect to Connect to the CALENDAR object from CalendarManager
			RemoteMethods2  CalObject = remoteConnect.ConnectCalendar(CurrentUser);
			//System.out.println("Connect Calendar method returned: "+ "\n" +CalObject);			
			//System.out.println(CalObject.getname());

//			allevents = CalObject.listOfEvents(CurrentUser);
//			System.out.println(allevents.length);
//			Automate alarm = new Automate(allevents);
//			alarm.start();

			//*************************************************
			//******************File Write ********************
			//*************************************************
			BufferedWriter bufferedWriter = null;


			try {

				//Construct the BufferedWriter object
				bufferedWriter = new BufferedWriter(new FileWriter("/Users/Pratheek/Desktop/"+ CalObject.getname() +".txt"));

				//Start writing to the output stream
				bufferedWriter.write(CalObject.getname());
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

			int choice = 0;
			do{


				System.out.println("Choices:"+ "\n" +"1.Schedule an Event" +"\t" + "2.View all events" +"\n"+ "3.View all Users" + "\t" + "4.Exit");
				System.out.println("Hey " +CurrentUser+"! Enter your choice of operation:");
				choice = input.nextInt();


				switch (choice){  
				case 1:
					//Schedule an event
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy/HH:mm");
					int i=0;

					System.out.println("How many users on the list?");
					int no_of_users = input.nextInt();
					String UserList = "";


					if(no_of_users > 0) 
					{
						for(i=0;i< no_of_users ; i++){
							System.out.println("Enter the user "+ i +":");
							if(i==0)
							{
								UserList = input.next();
							}
							else
							{
								UserList = UserList+","+input.next();					
							
							}
						}
							System.out.println("Enter name of the event:");
							String event_name = input.next();

							System.out.println("When does "+event_name+" begin? (MM/DD/YYYY/HH:mm) :");
							Date start_date = df.parse(input.next());

							System.out.println("When does "+event_name+" end? (MM/DD/YYYY/HH:mm):");
							Date end_date = df.parse(input.next());

							System.out.println("Your event is Private/Public/Group/Open :");
							String AccessSpecifier = input.next();

							String output = CalObject.ScheduleEvent(start_date,end_date , UserList,event_name, AccessSpecifier);
							System.out.println(output);

						}
						// Called if OWNER has nobody on list
						else
						{
							String hasNone = "";
							System.out.println("Enter name of the event:");
							String event_name = input.next();

							System.out.println("When does "+event_name+" begin? (MM/DD/YYYY/HH:mm):");
							Date start_date = df.parse(input.next());

							System.out.println("When does "+event_name+" end? (MM/DD/YYYY/HH:mm):");
							Date end_date = df.parse(input.next());
							
							System.out.println("Your event is Private/Public/Group/Open :");
							String AccessSpecifier = input.next();

							
							String output = CalObject.ScheduleEvent(start_date,end_date , hasNone ,event_name, AccessSpecifier);
							System.out.println(output);
						}
					allevents = CalObject.listOfEvents(CurrentUser);
					System.out.println(allevents.length);
					Automate alarm = new Automate(allevents);
					alarm.start();
					
						break;

					case 2:
						//View all events

						DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy/HH:mm");

						System.out.println("Enter the start date (MM/DD/YYYY/HH:mm)");
						Date s_date = df2.parse(input.next());

						System.out.println("Enter the end date (MM/DD/YYYY/HH:mm)");
						Date e_date = df2.parse(input.next());
						
						System.out.println("Whose events do you wish to see?");
						String chooseOwner = input.next();

						String EventList11 = CalObject.RetrieveEvents(chooseOwner, s_date, e_date);
						if (EventList11.equals(null)){
							System.out.println("No Events have been scheduled as yet!");				  
						}
						else
							System.out.println("Events:" + "\n" + EventList11 );
						break;

					case 3:
						//List of Users


						String List_of_users = remoteConnect.List();
						System.out.println("List of Users:" +List_of_users);
						break;

					case 4:
						//Exit			  
						break;
					default:
						System.out.println("Invalid Entry!");
					}

				}while(choice!=4);

			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
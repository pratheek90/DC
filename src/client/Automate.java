package client;

import java.util.Date;

public class Automate extends Thread {

	Date[] allevents = new Date[20];
	
	public Automate(Date[] allevents) {
		// TODO Auto-generated constructor stub
		this.allevents = allevents;
	}


	public void run() {
		while(true)
			try{
				
				
				for (int i=0; i<allevents.length ; i++){
					Date now = new Date();
					
					System.out.println("Event:"+ allevents[i]);
					if(now.getTime()> allevents[i].getTime())
						System.out.println(" ###### REMINDER!! You have an event scheduled now #####");
				}
				
				System.out.println();
			
				sleep(100000);
			}
		catch(InterruptedException a){
			a.printStackTrace();
		}
	}
}

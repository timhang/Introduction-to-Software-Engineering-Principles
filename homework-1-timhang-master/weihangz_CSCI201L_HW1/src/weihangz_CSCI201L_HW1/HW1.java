package weihangz_CSCI201L_HW1;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.Writer;
//import java.io.InputStreamReader;
//import java.io.Reader;
import java.util.Collections;

import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.text.DateFormatSymbols;


public class HW1 {

    
	/*public static UserList addUser(UserList userList) {
		Scanner
		
		return userList;
	}*/
	public static String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
	
    public static void main(String [] args) throws IOException {
    	Scanner scan = new Scanner(System.in);
    	
    	boolean fileSuccess = false;
		
		while (fileSuccess == false){
			
			System.out.print("What is the name of the input file? ");
	    	
			String inputFilename = scan.nextLine();
			Gson gson = new Gson();
			BufferedReader br = null;
			
				
		    try {
		    	br = new BufferedReader(new FileReader(inputFilename));
		    	UserList userList = gson.fromJson(br, UserList.class);
		    	//System.out.println(userList.getUsers().size());
	        	
	        	boolean a = true;
	            while (a) {
	            	fileSuccess = true;
	            	
	            	//Print Menu items
	            	System.out.println("\n\t1) Display User's Calendar");
	            	System.out.println("\t2) Add User");
	            	System.out.println("\t3) Remove User");
	            	System.out.println("\t4) Add Event");
	            	System.out.println("\t5) Delete Event");
	            	System.out.println("\t6) Sort Users");
	            	System.out.println("\t7) Write File");
	            	System.out.println("\t8) Exit\n");
	            	System.out.print("What would you like to do? ");
	            	//Scanner in = new Scanner(System.in);        
	            	
	            	//Error checking menu input
	            	while (!scan.hasNextInt()) {
	            		   
	            		   System.out.println("Please enter an integer\n");
	            		   scan.nextLine();
	            	}
	            	int userInput = scan.nextInt();
	            	scan.nextLine();
	            	
	            	//Out of Range
	            	if(userInput<=0||userInput>8) {
	            		System.out.println("This is not a valid option\n");
	            		continue;
	            	}
	            	
	            	else if(userInput == 1) {//Display User Calender
	            		System.out.print("\n");
	            		int userCount = 1;
	            		char eventCount = 'a';
	            		for (User u : userList.getUsers()) {
	            			
	            			System.out.println(userCount+") "+u.getName());
	            			if(u.getEvents()!=null) {
	            				for(Event e : u.getEvents()) {
		            				System.out.print("\t"+eventCount+". ");
		            				System.out.println(e);
		            				eventCount++;
		            			}
	            			}
	            			userCount++;
	            		}
	            	}
	            	
	            	else if(userInput == 2) {//Adding User
	            		//userList = addUser(userList);
	            		boolean flag = true;
	            		while(flag) {
	            			System.out.print("\n");
	            			System.out.print("What is the user's name? ");
	                		String inName = scan.nextLine();
	                		String trimmed = inName.trim();
	                		int length;
	                		if(trimmed.isEmpty()) {
	                			length=0;
	                		}
	                		length = trimmed.split("\\s+").length;
	                		if(length<=1) {
	                			System.out.println("Invalid, must have first and last name.\n");
	                			continue;
	                		} else {
	                			String arr[] = inName.split(" ", 2);
	                			String fName = arr[0];
	                			String lName = arr[1];
	                			Name newName = new Name();
	                			newName.setFname(fName);
	                			newName.setLname(lName);
	                			User newUser = new User();
	                			newUser.setName(newName);
	                			ArrayList<User> newUserList = new ArrayList<User>();
	                			newUserList = userList.getUsers();
	                			newUserList.add(newUser);
	                			userList.setUsers(newUserList);
	                			
	                			//System.out.println(fName+"\n"+lName);
	                			
	                			flag=false;
	                		}
	            		}
	            	}
	            	
	            	else if(userInput == 3) {//Removing User
	            		System.out.print("\n");
	            		int userCount = 0;
	            		for (User u : userList.getUsers()) {
	            			userCount++;
	            			System.out.println(userCount+") "+u.getName());
	            			
	            		}
	            		boolean deleted = false;
	            		while(deleted == false) {
	            			System.out.println("\nWhich user would you like to delete? ");
		            		while (!scan.hasNextInt()) {
			            		   
			            		   System.out.println("Please enter an integer\n");
			            		   scan.nextLine();
			            	}
			            	int userName = scan.nextInt();
			            	scan.nextLine();
			            	if(userName<=0||userName>userCount) {
			            		System.out.println("This is not a valid option\n");
			            		continue;
			            	}
			            	else {
			            		System.out.println("\n"+userList.getUsers().get(userName-1).getName().getFname()+" "+userList.getUsers().get(userName-1).getName().getLname()+" has been deleted.");
			            		ArrayList<User> temp = userList.getUsers();
			            		temp.remove(userName-1);
			            		userList.setUsers(temp);
			            		deleted = true;
			            		
			            	}
	            		}
	            		
	            		
	            	}
	            	
	            	else if(userInput == 4) {//Adding Event
	            		System.out.print("\n");
	            		int userCount = 0;
	            		for (User u : userList.getUsers()) {
	            			userCount++;
	            			System.out.println(userCount+") "+u.getName());
	            			
	            		}
	            		
	            		boolean added = false;
	            		while(added == false) {
	            			System.out.println("\nTo which user would you like to add an event?");
		            		while (!scan.hasNextInt()) {
			            		   
			            		   System.out.println("Please enter an integer\n");
			            		   scan.nextLine();
			            	}
			            	int userName = scan.nextInt();
			            	scan.nextLine();
			            	if(userName<=0||userName>userCount) {
			            		System.out.println("This is not a valid option\n");
			            		continue;
			            	}
			            	else {
			            		System.out.print("\nWhat is the title of the event? ");
			            		String eventName = scan.nextLine();
			            		System.out.print("\nWhat time is the event? ");
			            		String eventTime = scan.nextLine();
			            		int eventMonth = 0;
			            		boolean flag1 = true;
			            		while(flag1) {
			            			System.out.print("\nWhat month? ");
				            		while (!scan.hasNextInt()) {
					            		   
					            		   System.out.println("Please enter an integer\n");
					            		   scan.nextLine();
					            	}
				            		
				            		eventMonth = scan.nextInt();
				            		scan.nextLine();
				            		if(eventMonth<1||eventMonth>12) {
					            		System.out.println("This is not a valid option\n");
					            		continue;
					            	} else {
					            		flag1 = false;
					            	}
			            		}
			            		
			            		System.out.print("\nWhat day? ");
			            		while (!scan.hasNextInt()) {
				            		   
				            		   System.out.println("Please enter an integer\n");
				            		   scan.nextLine();
				            	}
			            		int eventDay = scan.nextInt();
			            		System.out.println("\nWhat year? ");
			            		while (!scan.hasNextInt()) {
				            		   
				            		   System.out.println("Please enter an integer\n");
				            		   scan.nextLine();
				            	}
			            		int eventYear = scan.nextInt();

			            		String month = getMonth(eventMonth);
			            		
			            		Event newEvent = new Event();
			            		newEvent.setTitle(eventName);
			            		newEvent.setTime(eventTime);
			            		Date newDate = new Date();
			            		newDate.setDay(eventDay);
			            		newDate.setMonth(month);
			            		newDate.setYear(eventYear);
			            		newEvent.setDate(newDate);
			            		
			            		ArrayList<User> tempList = userList.getUsers();
			            		ArrayList<Event> tempEvents = tempList.get(userName-1).getEvents();
			            		if(tempEvents==null) {
			            			tempEvents = new ArrayList<Event>();
			            			tempEvents.add(newEvent);
			            		}
			            		else {
			            			tempEvents.add(newEvent);
			            		}
			
			            		User tempUser = tempList.get(userName-1);
			            		tempUser.setEvents(tempEvents);
			            		tempList.set(userName-1, tempUser);
			            		
			            		System.out.println("Added: "+eventName+", "+eventTime+", "+newDate+" to "+tempUser.getName().getFname()+" "+tempUser.getName().getLname()+"'s calendar.");
			            		
			            		added = true;
			            		
			            	}
	            		}
	            	}
	            	
	            	else if(userInput == 5) {//Deleting Event
	            		System.out.print("\n");
	            		int userCount = 0;
	            		for (User u : userList.getUsers()) {
	            			userCount++;
	            			System.out.println(userCount+") "+u.getName());
	            			
	            		}
	            		System.out.println("\nFrom which user would you like to delete an event?");
	            		
	            		while (!scan.hasNextInt()) {
		            		   
		            		   System.out.println("Please enter an integer\n");
		            		   scan.nextLine();
		            	}
		            	int userNum = scan.nextInt();
		            	scan.nextLine();
		            	if(userNum<=0||userNum>userCount) {
		            		System.out.println("This is not a valid option\n");
		            	} else {
		            		ArrayList<Event> tempEvents = userList.getUsers().get(userNum-1).getEvents();
		            		if(tempEvents==null || tempEvents.isEmpty()) {
		            			System.out.println("\nCalendar is empty.");
		            		} else {
		            			
		            			int eventCount = 0;
		            			for(Event e: tempEvents) {
		            				eventCount++;
		            				System.out.print("\t"+eventCount+") ");
		            				System.out.println(e);
		            			}
		            			
		            			boolean deleted = false;
		            			while(deleted == false) {
		            				System.out.print("\nWhich event would you like to delete? ");
			            			while (!scan.hasNextInt()) {
					            		   
					            		   System.out.println("Please enter an integer\n");
					            		   scan.nextLine();
					            	}
					            	int eventNum = scan.nextInt();
					            	scan.nextLine();
					            	if(eventNum<=0||eventNum>eventCount) {
					            		System.out.println("This is not a valid option\n");
					            		continue;
					            	} else {
					            		System.out.println("\n"+tempEvents.get(eventNum-1).getTitle()+" has been deleted.");
					            		tempEvents.remove(eventNum-1);
					            		User tempUser = new User();
					            		tempUser.setEvents(tempEvents);
					            		tempUser.setName(userList.getUsers().get(userNum-1).getName());
					            		ArrayList<User> tempList = userList.getUsers();
					            		tempList.set(userNum-1, tempUser);
					            		userList.setUsers(tempList);
					            		deleted = true;
					            	}
		            			}
		            			
		            		}
		            	}
	            		
	            	}
	            	
	            	else if(userInput == 6) {
	            		System.out.println("\n\t1) Ascending (A-Z)");
	            		System.out.println("\t2) Descending (Z-A)");
	            		boolean flag = true;
	            		while(flag) {
	            			System.out.print("\nHow would you like to sort? ");
	            			flag=false;
		            		while (!scan.hasNextInt()) {
			            		   
			            		   System.out.println("Please enter an integer\n");
			            		   scan.nextLine();
			            	}
		            		int sortNum = scan.nextInt();
			            	scan.nextLine();
		            		if (sortNum == 1) {
		            			Collections.sort(userList.getUsers());
			            		ArrayList<User> newUsers = userList.getUsers();
			            		Collections.sort(newUsers);
			            		userList.setUsers(newUsers);
		            		} else if(sortNum == 2) {
		            			Collections.sort(userList.getUsers());
			            		ArrayList<User> newUsers = userList.getUsers();
			            		Collections.sort(newUsers, Collections.reverseOrder());
			            		userList.setUsers(newUsers);
		            		} else {
		            			System.out.println("\nThis is not a valid option\n");
		            			flag = true;
			            		continue;
		            		}
	            		}
	            		
	            		
	            		
	            	}
	            	
	            	else if(userInput == 7) {
	            		//String json = gson.toJson(userList);
	            		
	            		try (Writer file = new FileWriter("test1.json")) {
	            			//file.write(json);
	            			gson.toJson(userList, file);
	            			System.out.println("File has been saved.");
	            		} catch(IOException ioe){
	            			System.out.println("Write file failed.");
	            	    }
	            	}
	            	
	            	else if(userInput == 8) {
	            		System.out.println("\nChanges have been made since the file was last saved.");
	            		System.out.println("\t1) Yes");
	            		System.out.println("\t2) No");
	            		System.out.println("Would you like to save the file before exiting? ");
	            		boolean flag = true;
	            		while(flag) {
	            			flag=false;
		            		while (!scan.hasNextInt()) {
			            		   
			            		   System.out.println("Please enter an integer\n");
			            		   scan.nextLine();
			            	}
		            		int fileNum = scan.nextInt();
			            	scan.nextLine();
		            		if (fileNum == 1) {
		            			try (Writer file = new FileWriter("test1.json")) {
			            			gson.toJson(userList, file);
			            			System.out.println("\nFile has been saved.");
			            		} catch(IOException ioe){
			            			System.out.println("Write file failed.");
			            	    }
		            			
		            		} else if(fileNum == 2) {
		            			System.out.println("\nFile was not saved.");
		            		} else {
		            			System.out.println("\nThis is not a valid option\n");
		            			flag = true;
			            		continue;
		            		}
	            		}
	            		
	            		System.out.println("\nThank you for using my program!");
	            		a = false;
	            	}
	            	
	            	//System.out.println();
	            }
	            
		    
		    } catch(FileNotFoundException fnfe) {
		    	System.out.println("The file could not be found.\n");
		    } catch(JsonParseException jpe) {
		    	System.out.println("That file is not a well-formed JSON file.\n");
		    }
		    finally {
		    	if(br!=null) {
		    		try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    }
	    
		}
		//System.out.println(inputFilename);
    	
        
        scan.close();
        System.exit(0);
    }
}

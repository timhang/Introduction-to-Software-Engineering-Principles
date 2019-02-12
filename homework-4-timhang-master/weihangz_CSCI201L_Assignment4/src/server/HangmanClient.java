package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class HangmanClient extends Thread {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String username;
	private String password;
	private boolean inGame;
	private String gameName;
	public HangmanClient(String hostname, int port) {
		Socket s = null;
		inGame = false;
		gameName = null;
		//Scanner scan = null;
		try {
			s = new Socket(hostname, port);
			System.out.println("Connected!");
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
			
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
//	public boolean login() {
//		
//	}
	public void run() {
		Scanner scan = null;
		scan = new Scanner(System.in);
		try {
			
			int state = 2;
			while(state!=0) {
				//String username;
				//String password;
				try {
				
				//while(true) {
					
					
					System.out.print("\nEnter User Name: ");
					username = scan.nextLine();
					System.out.print("Enter password: ");
					password = scan.nextLine();
					HangmanMessage message = new HangmanMessage(username, password,"verifyLogin");
					oos.writeObject(message);
					oos.flush();
				//}
				}catch (IOException ioe) {
					System.out.println("ioe: " + ioe.getMessage());
				}
				//Retrieve Message from server
				HangmanMessage message = (HangmanMessage)ois.readObject();
				//message.incre();
				//System.out.println(message.getName() + ": " + message.getMessage());
				username = message.getName();
				password = message.getMessage();
				if(message.getState()==0) {
					System.out.println("\nGreat! You are now logged in as "+username+".");
					state = 0;
				}else if(message.getState()==1) {
					System.out.println("Wrong Password!");
					//break;
					state = 1;
				}else {
					System.out.println("No account exists with those credentials.");
					//break;
					state = 2;
					System.out.print("Would ou like to create a new account? ");
					String flag = scan.nextLine();
					
					if(flag.equals("Yes")) {
						System.out.print("Would ou like to use the username and password above? ");
						String flag1 = scan.nextLine();
						while(!flag1.equals("Yes") && !flag1.equals("No")) {
							System.out.println("Please enter Yes or No.");
							flag1 = scan.nextLine();
						}
						if(flag1.equals("Yes")) {
							HangmanMessage message1 = new HangmanMessage(username, password,"addUser");
							oos.writeObject(message1);
							oos.flush();
							username = message1.getName();
							System.out.println("Great! You are now logged in as "+username+".");
							state = 0;
						}
					}
					//"Do you want to run the "
				}
			}
			System.out.println();
			
			
			System.out.println(username + "'s Record");
			System.out.println("--------------------");
			HangmanMessage message = new HangmanMessage(username, password,"getWin");
			oos.writeObject(message);
			oos.flush();
			message = (HangmanMessage)ois.readObject();
			int win = message.getState();
			message = new HangmanMessage(username,password,"getLoss");
			oos.writeObject(message);
			oos.flush();
			message = (HangmanMessage)ois.readObject();
			int loss = message.getState();
			System.out.println("Wins - "+ win);
			System.out.println("Losses - "+ loss);
			//scan.nextLine();
			
			while(true) {// Start or Join game
				System.out.println("\n    1) Start a Game");
				System.out.println("    2) Join a Game");
				System.out.print("\nWould you like to start a game or join a game? ");
				String startJoin = scan.nextLine();
				while(!startJoin.equals("1") && !startJoin.equals("2")) {
					System.out.println("Please enter 1 or 2.");
					startJoin = scan.nextLine();
				}
				System.out.println();
				if(startJoin.equals("1")) {
					//create game
					System.out.print("What is the name of the game? ");
					gameName = scan.nextLine();
					message = new HangmanMessage(gameName,username,"checkGameName");
					oos.writeObject(message);
					oos.flush();
					message = (HangmanMessage)ois.readObject();
					while(message.getState() == 35) {
						System.out.println("\n"+gameName+" already exists.");
						System.out.print("\nWhat is the name of the game? ");
						gameName = scan.nextLine();
						message = new HangmanMessage(gameName,username,"checkGameName");
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						
					}
					System.out.println();
					System.out.print("How may users will be playing (1-4)? ");
					String numPlayer = scan.nextLine();
					while(!numPlayer.equals("1") && !numPlayer.equals("2")&& !numPlayer.equals("3")&&!numPlayer.equals("4")) {
						System.out.println("Please enter a number between 1 and 4.");
						numPlayer = scan.nextLine();
					}
					
					message = new HangmanMessage(gameName,username,"createGame");
					message.setState(Integer.parseInt(numPlayer));
					oos.writeObject(message);
					oos.flush();
					message = (HangmanMessage)ois.readObject();
					
					if(message.getState()==5) {
						System.out.println("\nAll users have joined.");
						inGame = true;
						break;
					}
					int num = Integer.parseInt(numPlayer)-1;//Number player left to join
					boolean numChanged = true;
					while(num>=1) {
						if(numChanged) {
							numChanged = false;
							System.out.println("Waiting for "+num+" other user to join...");
						}
						message = new HangmanMessage(gameName,username,"getNumPlayer");
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						if(num != message.getState()) {
							num = message.getState();
							numChanged = true;
						}
						
					}
					System.out.println("\nAll users have joined.");
					inGame = true;
					break;
				}
				if(startJoin.equals("2")) {
					//join game

					
						System.out.print("What is the name of the game? ");
						gameName = scan.nextLine();
						message = new HangmanMessage(gameName,username,"checkGameName");
						
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						
						while(message.getState() == 45) {
							System.out.println("\nThere is no game with name "+gameName+".");
							System.out.print("\nWhat is the name of the game? ");
							gameName = scan.nextLine();
							message = new HangmanMessage(gameName,username,"checkGameName");
							oos.writeObject(message);
							oos.flush();
							message = (HangmanMessage)ois.readObject();
							
						}
						message = new HangmanMessage(gameName,username,"addUsertoGame");
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						if(message.getState()==50) {
							System.out.println("\nThe game "+gameName+" does not have space for another user to join.");
						}
						if(message.getState()==55) {
							message = new HangmanMessage(gameName,username,"getNumPlayer");
							oos.writeObject(message);
							oos.flush();
							message = (HangmanMessage)ois.readObject();
							int num = message.getState();
							boolean numChanged = true;
							while(num>=1) {
								if(numChanged) {
									numChanged = false;
									System.out.println("Waiting for "+num+" other user to join...");
								}
								message = new HangmanMessage(gameName,username,"getNumPlayer");
								oos.writeObject(message);
								oos.flush();
								message = (HangmanMessage)ois.readObject();
								if(num != message.getState()) {
									num = message.getState();
									numChanged = true;
								}
								
							}
							System.out.println("\nAll users have joined.");
							inGame = true;
						}
	
					
					if(inGame) {
						break;
					}
					
				}
			}
			
			//Inside game
			System.out.println("\nDetermining secret word...");
			while(inGame) {
				message = new HangmanMessage(gameName,username,"displayWord");
				oos.writeObject(message);
				oos.flush();
				message = (HangmanMessage)ois.readObject();
				//name is gameName, message is secret word, third is Mask
				String[] mask = message.getMask();
				String secretWord = message.getMessage();
				int numGuessLeft = message.getState();
				System.out.print("\nSecret Word ");
				
				for(int i = 0;i< secretWord.length();i++) {
					if(mask[i].equals("0")) {
						System.out.print("_ ");
					} else {
						System.out.print(secretWord.charAt(i)+" ");
					}
				}
				/*if(numGuessLeft == 0) {
					(System.out.println("Sorry, no guesses luck. You ");)
				}*/
				
				System.out.print("\nSecret Word ");
				message = new HangmanMessage(gameName,username,"displayWord");
				oos.writeObject(message);
				oos.flush();
				message = (HangmanMessage)ois.readObject();
				secretWord = message.getMessage();
				numGuessLeft = message.getState();
				System.out.println("\n\nYou have "+numGuessLeft+" incorrect guesses remaining.");
				
				//Message to get currPlayer
				message = new HangmanMessage(gameName,username,"getCurrPlayer");
				oos.writeObject(message);
				oos.flush();
				message = (HangmanMessage)ois.readObject();
				String currPlayer = message.getMessage();
				boolean playerChanged = true;
				while(!currPlayer.equals(username)) {
					if(playerChanged) {
						playerChanged = false;
						System.out.println("\nWaiting for "+currPlayer+" to do something...");
					}
					message = new HangmanMessage(gameName,username,"getCurrPlayer");
					oos.writeObject(message);
					oos.flush();
					message = (HangmanMessage)ois.readObject();
					if(!currPlayer.equals(message.getMessage())) {
						currPlayer = message.getMessage();
						playerChanged = true;
					}
				}
				
				
				
				
				
				
				
				System.out.println("    1) Guess a letter.");
				System.out.println("    2) Guess the word.");
				System.out.print("\nWhat would you like to do? ");
				String nextLine = scan.nextLine();
				while(!nextLine.equals("1") && !nextLine.equals("2")) {
					System.out.println("\nPlease enter the number 1 or 2. ");
					nextLine = scan.nextLine();
				}
				int letterWord = Integer.parseInt(nextLine);
				
				if(letterWord == 1) {
					System.out.print("\nSecret Word ");
					message = new HangmanMessage(gameName,username,"displayWord");
					oos.writeObject(message);
					oos.flush();
					message = (HangmanMessage)ois.readObject();
					secretWord = message.getMessage();
					numGuessLeft = message.getState();
					mask = message.getMask();
					for(int i = 0;i< secretWord.length();i++) {
						if(mask[i].equals("0")) {
							System.out.print("_ ");
						} else {
							System.out.print(secretWord.charAt(i)+" ");
						}
					}
					System.out.println("\nYou have "+numGuessLeft+" incorrect guesses remaining.");
					System.out.print("\nLetter to guess - ");
					//guess
					nextLine = scan.nextLine();
					while(nextLine.length()>1) {
						System.out.println("Please input a single letter!");
						nextLine = scan.nextLine();
					}
					while(!Character.isLetter(nextLine.charAt(0))){
						System.out.println("Please input a single letter!");
						nextLine = scan.nextLine();
					}
					message = new HangmanMessage(gameName,username,"printGuess");
					message.setGuess(nextLine);
					oos.writeObject(message);
					oos.flush();
					message = (HangmanMessage)ois.readObject();
					
					if((secretWord.toLowerCase().contains(nextLine.toLowerCase())==false) && numGuessLeft == 1) {
						System.out.println("\nThe letter '" + nextLine.toLowerCase() + "' is not in the secret word.");
						System.out.println("\nYou ran out of guesses, you lost!");
						System.out.println("The word was '"+secretWord +"'.");
						message = new HangmanMessage(gameName,username,"increLoss");
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						numGuessLeft = 0;
						inGame = false;
						break;
					} else if ((secretWord.toLowerCase().contains(nextLine.toLowerCase())==false) && numGuessLeft > 1) {
						System.out.println("\nThe letter '" + nextLine.toLowerCase() + "' is not in the secret word.");
						numGuessLeft--;
						message = new HangmanMessage(gameName,username,"updateGuess");
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						
					} else {//(secretWord.toLowerCase().contains(nextLine.toLowerCase())==true)
						
						System.out.println("\nThe letter '" + nextLine.toLowerCase() + "' is in the secret word.");
						message = new HangmanMessage(gameName,username,"updateMove");
						//message.setGuess(nextLine);
						int bitFlipped = 0;
						for(int i=0;i<secretWord.length();i++) {
							if(nextLine.charAt(0)==secretWord.charAt(i)) {
								mask[i] = "1";
								
							}
							if(mask[i].equals("1")) {
								bitFlipped++;
							}
						}
						message.setMask(mask);
						message.setState(bitFlipped);
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						if(message.getState()==30) {
							message = new HangmanMessage(gameName,username,"increWin");
							oos.writeObject(message);
							oos.flush();
							message = (HangmanMessage)ois.readObject();
							System.out.println("\nThat is correct! You win!");
							inGame = false;
							break;
						}
						
					}
					message = new HangmanMessage(gameName,username,"moveCurr");
					oos.writeObject(message);
					oos.flush();
					message = (HangmanMessage)ois.readObject();
					
				}
				if(letterWord == 2) {
					System.out.print("\nWhat is the secret word? ");
					nextLine = scan.nextLine();
					if((secretWord.toLowerCase().contains(nextLine.toLowerCase())==true)) {
						int bitFlipped = 0;
						for(int i=0;i<secretWord.length();i++) {
							mask[i] = "1";
							bitFlipped++;
						}
						message = new HangmanMessage(gameName,username,"updateMove");
						message.setMask(mask);
						message.setState(bitFlipped);
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						if(message.getState()==30) {
							System.out.println("\nThat is correct! You win!");
							message = new HangmanMessage(gameName,username,"increWin");
							oos.writeObject(message);
							oos.flush();
							message = (HangmanMessage)ois.readObject();
							inGame = false;
							break;
						}
					} else {//Guessed Wrong
						System.out.println("\nYour guess is wrong! You lose!");
						System.out.println("The word was '"+secretWord +"'.");
						message = new HangmanMessage(gameName,username,"increLoss");
						oos.writeObject(message);
						oos.flush();
						message = (HangmanMessage)ois.readObject();
						inGame = false;
						break;
					}
				}
				
				
				//break;
			}
			
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
		//System.out.println("Great! You are now logged in as .");
		scan.close();
	
	}
	
	public static void main(String [] args) throws FileNotFoundException {
		System.out.print("What is the name of the configuration file? ");
		Scanner scan = new Scanner(System.in);
		String inputFilename = scan.nextLine();
		System.out.println(inputFilename);
		File file = new File(inputFilename);
		while(!file.isFile()) { 
		    // do something
			System.out.println("Please enter valid config file name!");
			inputFilename = scan.nextLine();
			file = new File(inputFilename);
		}
		Scanner fileScan = new Scanner(file);
		Map<String, String> config = new LinkedHashMap< String,String>();
		
		while(fileScan.hasNextLine()) {
			String nextLine = fileScan.nextLine();
			String[] parts = nextLine.split("=");
		    config.put(parts[0], parts[1]);
		}
		
		for (String s : config.keySet()) {
	        System.out.println(s + " - " + config.get(s));
	    }
		System.out.println();
		System.out.print("Trying to connect to server...");
		new HangmanClient(config.get("ServerHostname"), Integer.parseInt(config.get("ServerPort")));
		//scan.close();
		fileScan.close();
	}
}


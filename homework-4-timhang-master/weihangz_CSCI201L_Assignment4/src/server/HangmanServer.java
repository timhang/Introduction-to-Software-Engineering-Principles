package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class HangmanServer {
	private Vector<ServerThread> serverThreads;
	private static Map<String, String> config = new LinkedHashMap< String,String>();
	ArrayList<Game> gameList = new ArrayList<Game>();
	private String [] outputLine = new String[] {
			"trying to log in with password",
			"successfully logged in.",
			"does not have an account so not successfully logged in.",
			"has an account but not successfully logged in.",
			"created an account with password",
			"has record %d wins and %d losses.",
			"wants to start a game called",
			"wants to join a game called",
			"already exists, so unable to start",
			"successfully started game",
			"successfully joined game",
			"exists but %s unable to join because maximum number of players have already joined %s.",
			"needs %d to start game.",
			"has %d so starting game. Secret word is %s.",
			"guessed letter %s.",
			"is in %s in position(s) %d. Secret word now shows %s.",
			"is not in %s. %s now has %d guesses remaining.",
			"guessed word %s.",
			"is incorrect. %s has lost and is no longer in the game.",
			"is correct. %s wins game. %s have lost the game."
	};
	
	public HangmanServer(int port) {
		ServerSocket ss = null;
		serverThreads = new Vector<ServerThread>();
		try {
			ss = new ServerSocket(port);
			while (true) {
				System.out.println("waiting for connection...");
				Socket s = ss.accept();
				System.out.println(outputLine[0]);
				System.out.println("connection from " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
			}
		} catch (IOException ioe) {
			//System.out.println("ioe: " + ioe.getMessage());
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					//System.out.println("ioe closing ss: " + ioe.getMessage());
				}
			}
		}
	}
	
	public void sendMessageToAllClients(HangmanMessage message) {
		for (ServerThread st : serverThreads) {
			st.sendMessage(message);
		}
	}
	
	public int verifyLogin(String username, String password){
		return JDBCDriver.verifyLogin(username, password);
	}
	public void addUser(String username, String password) {
		JDBCDriver.addUser(username, password);
	}
	public static Map<String,String> getConfig() {
		return config;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
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
		while(fileScan.hasNextLine()) {
			String nextLine = fileScan.nextLine();
			String[] parts = nextLine.split("=");
		    config.put(parts[0], parts[1]);
		}
		
		for (String s : config.keySet()) {
	        System.out.println(s + " - " + config.get(s));
	    }
		new HangmanServer(Integer.parseInt(config.get("ServerPort")));
		scan.close();
		fileScan.close();
	}

}


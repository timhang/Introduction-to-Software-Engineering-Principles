package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.ArrayList;

public class ServerThread extends Thread {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private HangmanServer hs;
	//private ArrayList<Game> hs.gameList = new ArrayList<Game>();
	//private JDBCDriver DBdriver;
	//private Map<String, String> config = new LinkedHashMap< String,String>();
	//private JDBCDriver driver;
	public ServerThread(Socket s, HangmanServer hs) {
		try {
			this.hs = hs;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			//System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(HangmanMessage message) {
		try {
			//Send out message to each client in ObjectOutputStream
			oos.writeObject(message);
			oos.flush();
			//System.out.println(message.getMessage());
			//System.out.println(" asdfa" );
		} catch (IOException ioe) {
			//System.out.println("ioe: " + ioe.getMessage());
		}
	}
	/*public void addUser(String username, String password) {
		
	}*/
	public int verifyLogin(String username, String password) {
		return hs.verifyLogin(username,password);
	}
	
	//public int getWin(String)
	public void run() {
		try {
			while(true) {
				HangmanMessage message = (HangmanMessage)ois.readObject();
				int state;
				//JDBCDriver.addUser(message.getName(),message.getMessage());
				if(message.getTag().equals("addUser")) {
					hs.addUser(message.getName(), message.getMessage());
					String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
					System.out.print("\n"+timeStamp+" ");
					System.out.println("added user: "+message.getName());
				}
				if(message.getTag().equals("verifyLogin")) {
					String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
					System.out.print("\n"+timeStamp+" ");
					System.out.println(message.getName() + " - trying to log in with password "+message.getMessage());
					state = verifyLogin(message.getName(), message.getMessage());
					message.setState(state);
				}
				if(message.getTag().equals("getWin")) {
					state = JDBCDriver.getWin(message.getName());
					String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
					System.out.print("\n"+timeStamp+" ");
					System.out.print(message.getName()+ " - has record "+state+" wins and ");
					message.setState(state);
				}
				if(message.getTag().equals("getLoss")) {
					state = JDBCDriver.getLoss(message.getName());
					System.out.println(state+" losses.");
					message.setState(state);
				}
				
				if(message.getTag().equals("increWin")) {
					JDBCDriver.incrementWin(message.getMessage());
				}
				if(message.getTag().equals("increLoss")) {
					JDBCDriver.incrementLoss(message.getMessage());
				}
				if(message.getTag().equals("createGame")) {
					//System.out.println(message.getName()+" :: " + message.getMessage());
					String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
					System.out.print("\n"+timeStamp+" ");
					System.out.println(message.getMessage() + " - wants to start a game called "+message.getName()+".");
					
					Game newGame = new Game(message.getName(),message.getMessage(),message.getState());
					//System.out.println(newGame.getName()+" :: " + newGame.requirementMet());
					//newGame.addUser();
					hs.gameList.add(newGame);
					if(newGame.requirementMet()) {
						timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
						System.out.print("\n"+timeStamp+" ");
						System.out.println(message.getMessage() + " - successfully started game "+message.getName()+".");
						message.setState(5);
						
					}
				}
				if(message.getTag().equals("checkGameName")) {
					boolean flag = false;
					//System.out.println("\nSize :: "+hs.gameList.size());
					for(int i = 0;i<hs.gameList.size();i++) {
						if(hs.gameList.get(i).getName().equals(message.getName())){
							flag = true;
							break;
						}
					}
					if(flag) {
						message.setState(35);//found name
					} else {
						message.setState(45);
					}
				}
				
				if(message.getTag().equals("addUsertoGame")) {
					String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
					System.out.print("\n"+timeStamp+" ");
					System.out.println(message.getMessage() + " - wants to join a game called "+message.getName()+".");
					for(int i = 0;i<hs.gameList.size();i++) {
						if(hs.gameList.get(i).getName().equals(message.getName())){
							if(hs.gameList.get(i).requirementMet()) {
								message.setState(50);//50 is if full
								timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
								System.out.print("\n"+timeStamp+" ");
								System.out.println(message.getMessage() + " - "+message.getName()+" exists, but "+message.getMessage()+" unable to join because maximum number of players have balready joined "+message.getName());
								break;
							}else {
								hs.gameList.get(i).addUser(message.getMessage());
								message.setState(55); //55 is successful add user
								timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
								System.out.print("\n"+timeStamp+" ");
								System.out.println(message.getMessage() + " - successfully joined game "+message.getName()+".");
							}
						}
						
					}
				}
				if(message.getTag().equals("printGuess")) {
					String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
					System.out.print("\n"+timeStamp+" ");
					System.out.println(message.getName()+" "+message.getMessage()+" - guessed letter "+message.returnGuess());
				}
				if(message.getTag().equals("displayWord")) {
					for(int i = 0;i<hs.gameList.size();i++) {
						Game temp = hs.gameList.get(i);
						if(temp.getName().equals(message.getName())) {
							message.setMask(temp.returnMask());
							message.setMessage(temp.returnWord());
							message.setState(temp.returnGuesses());
						}
					}
				}
				if(message.getTag().equals("updateMove")) {
					
					for(int i = 0;i<hs.gameList.size();i++) {
						if(hs.gameList.get(i).getName().equals(message.getName())){
							hs.gameList.get(i).setMask(message.getMask());
							hs.gameList.get(i).setBitFliped(message.getState());
							//System.out.println("\n" + message.getState());
							if(hs.gameList.get(i).checkWin()) {
								message.setState(30);
							} else {
								message.setState(40);
							}
						}
						
					}
				}
				if(message.getTag().equals("updateGuess")) {
					for(int i = 0;i<hs.gameList.size();i++) {
						if(hs.gameList.get(i).getName().equals(message.getName())){
							hs.gameList.get(i).decrementGuesses();
							
						}
						
					}
				}
				if(message.getTag().equals("moveCurr")) {
					for(int i = 0;i<hs.gameList.size();i++) {
						if(hs.gameList.get(i).getName().equals(message.getName())){
							hs.gameList.get(i).moveCurr();
							
						}
						
					}
				}
				if(message.getTag().equals("getCurrPlayer")) {
					for(int i = 0;i<hs.gameList.size();i++) {
						if(hs.gameList.get(i).getName().equals(message.getName())){
							message.setMessage(hs.gameList.get(i).returnCurrUser());
						}
					}
				}
				if(message.getTag().equals("getNumPlayer")) {
					for(int i = 0;i<hs.gameList.size();i++) {
						if(hs.gameList.get(i).getName().equals(message.getName())){
							int temp = hs.gameList.get(i).returnNumPlayers()-hs.gameList.get(i).returnSize();
							message.setState(temp);
							if(temp!=0) {
								//String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
								//System.out.print("\n"+timeStamp+" ");
								//System.out.println(message.getMessage()+" - "+ message.getName() + " needs "+temp+" to start game.");
							}else {
								String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
								System.out.print("\n"+timeStamp+" ");
								System.out.println(message.getMessage()+" - "+ message.getName() + " has "+temp+" so starting game.");

							}
						}
						
					}
				}
				
				//state = verifyLogin(message.getName(), message.getMessage());
				
				//hs.sendMessageToAllClients(message);
				sendMessage(message);
				//System.out.println("login State: "+state);
			}
		} catch (ClassNotFoundException cnfe) {
			//System.out.println("cnfe in run: " + cnfe.getMessage());
		} catch (IOException ioe) {
			//System.out.println("ioe in run: " + ioe.getMessage());
		}
	}
}


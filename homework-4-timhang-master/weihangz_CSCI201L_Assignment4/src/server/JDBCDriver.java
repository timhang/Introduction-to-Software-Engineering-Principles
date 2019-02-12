package server;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class JDBCDriver {
	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;
	private static String DBConn;
	private static String user;
	private static String pass;
	private static Map<String, String> config = new LinkedHashMap< String,String>();
	public JDBCDriver() {
		
	}
	public static void connect(){
		try {
			config = HangmanServer.getConfig();
			JDBCDriver.DBConn = config.get("DBConnection");
			JDBCDriver.user = config.get("DBUser");
			JDBCDriver.pass = config.get("DBPassword");
			//System.out.println(DBConn);
			//System.out.println(user);
			//System.out.println(pass);
			Class.forName("com.mysql.cj.jdbc.Driver");
			String connection;
			
			connection = DBConn + "?user=" + user + "&password=" + pass +"&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
			//System.out.println(connection);
			conn = DriverManager.getConnection(connection);		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(){
		try{
			if (rs!=null){
				rs.close();
				rs = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
			if(ps != null ){
				ps = null;
			}
		}catch(SQLException sqle){
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}
	
	
	public static void addUser(String username, String password) {
		
		connect();
        try {
            
            ps = conn.prepareStatement("INSERT INTO User (username, password, wins, losses) VALUES (?,?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.execute();
            String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
			System.out.print("\n"+timeStamp+" ");
            System.out.println(username+"created an account with password "+password);

        } catch(SQLException sqle){
			System.out.println("SQLException in function \" addUser\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
	}
	public static void incrementWin(String username) {
		int win = getWin(username);
		win++;
		//System.out.println("increment win");
		connect();
        try {
            
            ps = conn.prepareStatement("UPDATE User SET wins=(?) WHERE BINARY username = (?)");
            ps.setInt(1, win);
            ps.setString(2, username);
            ps.execute();

        } catch(SQLException sqle){
			System.out.println("SQLException in function \" incrementWin\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
	}
	
	public static void incrementLoss(String username) {
		int loss = getLoss(username);
		loss++;
		//System.out.println("increment loss");
		connect();
        try {
            
            ps = conn.prepareStatement("UPDATE User SET losses=(?) WHERE BINARY username = (?)");
            ps.setInt(1, loss);
            ps.setString(2, username);
            ps.execute();

        } catch(SQLException sqle){
			System.out.println("SQLException in function \" incrementLoss\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
	}
	
	public static int verifyLogin(String username, String password){
		//int state;	//state 0 = successful login, state 1 = wrong password, state 2 = no credentials exist.
		//ArrayList<ArrayList<String>>  User = new ArrayList<ArrayList<String>>();
		connect();
		try {
			ps = conn.prepareStatement("SELECT username, password FROM User WHERE BINARY username = (?);");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()==false) {
				return 2;
				//System.out.println("login state: "+ state);
			} else {
				do {
					//int count = rs.getInt("count");
					String st;
					st = rs.getString("password");
					//System.out.println(st);
					if(st.equals(password)) {//USERNAME and PASSWORD match
						String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
						System.out.print("\n"+timeStamp+" ");
						System.out.println(username + " - successfully logged in.");
						return 0;
					} else { //USERNAME Match password not match
						String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
						System.out.print("\n"+timeStamp+" ");
						System.out.println(username + " - has an account but not successfully loggedn in.");
						return 1;
					} 

				} while(rs.next());
			}
		} catch(SQLException sqle){
			System.out.println("SQLException in function \" incrementLoss\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
		return 0;
		//return state = 0;
	}
	
	public static int getWin(String username) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT wins FROM User WHERE BINARY username = (?)");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()==false) {
				return 0;
				//System.out.println("login state: "+ state);
			} else {
				do {
					//int count = rs.getInt("count");
					int win;
					win = rs.getInt("wins");
					//System.out.println(st);
					return win;

				} while(rs.next());
			}
			
		}catch(SQLException sqle){
			System.out.println("SQLException in function \" incrementLoss\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
		return 0;
	}
	
	public static int getLoss(String username) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT losses FROM User WHERE BINARY username = (?)");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()==false) {
				return 0;
				//System.out.println("login state: "+ state);
			} else {
				do {
					//int count = rs.getInt("count");
					int loss;
					loss = rs.getInt("losses");
					//System.out.println(st);
					return loss;

				} while(rs.next());
			}
			
		}catch(SQLException sqle){
			System.out.println("SQLException in function \" incrementLoss\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
		return 0;
	}
	
	public static ArrayList<ArrayList<String> >getData(String search){
		ArrayList<ArrayList<String>>  User = new ArrayList<ArrayList<String>>();
		String[] words = search.split(" ");
		for(int i = 0;i<words.length;i++) {
			System.out.println(words[i]);
			
		}
		connect();
		try {
			String connection;
			connection = DBConn + "?user=" + user + "&password" + pass +"&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
			conn = DriverManager.getConnection(connection);		
			ps = conn.prepareStatement("SELECT * FROM User"
					+ " ORDER BY userID");
			rs = ps.executeQuery();
			while(rs.next()){
				int rowCheck=0;
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("User.userID"));
				row.add(rs.getString("User.fname"));
				row.add(rs.getString("User.lname"));
				row.add(rs.getString("User.image"));
				for(int i = 0;i<words.length;i++) {
					if(row.get(1).toLowerCase().contains(words[i].toLowerCase()) || row.get(2).toLowerCase().contains(words[i].toLowerCase())) {
						rowCheck = 1;
					}
					
				}
				if(rowCheck == 1) {
					User.add(row);
				}
			}
		}catch(SQLException sqle){
			System.out.println("SQLException in function \" getData\": ");
			sqle.printStackTrace();
		}finally{
			close();
		}
		return User;
	}

public static ArrayList<String> getUser(String input){
	ArrayList<String>  User = new ArrayList<String>();

	connect();
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/SycamoreCalendar?user=root&password=1234&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
		ps = conn.prepareStatement("SELECT * FROM User WHERE userID = "+input+" ORDER BY userID");
		rs = ps.executeQuery();
		while(rs.next()){
			
			
			User.add(rs.getString("User.userID"));
			User.add(rs.getString("User.fname"));
			User.add(rs.getString("User.lname"));
			User.add(rs.getString("User.image"));

			System.out.println(User.get(1));

		}
	}catch(SQLException sqle){
		System.out.println("SQLException in function \" getData\": ");
		sqle.printStackTrace();
	}finally{
		close();
	}
	return User;
}

}
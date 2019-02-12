package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
	private String gameName;
	private String targetWord;
	private int numGuessesLeft;
	private int numPlayers;
	private String [] mask;
	private String dir;
	private int bitFliped;
	private int curr;
	private ArrayList<String> users = new ArrayList<String>();
	public Game(String name, String username, int num) throws FileNotFoundException {
		numPlayers = num;
		gameName = name;
		numGuessesLeft = 7;
		bitFliped = 0;
		curr = 0;
		int randomNum = ThreadLocalRandom.current().nextInt(1, 4373 + 1);
		//System.out.println("rand:: "+ randomNum);
		dir = HangmanServer.getConfig().get("SecretWordFile");
		File file = new File(dir);
		Scanner fileScan = new Scanner(file);
		for ( int i = 1; i<=randomNum; i++) {
			String nextLine = fileScan.nextLine();
			if(i==randomNum) {
				targetWord = nextLine;
			}
		}
		//System.out.println("targetWord:: "+ targetWord);
		users.add(username);
		mask = new String[targetWord.length()];
		//System.out.print("mask :: ");
		for(int i = 0; i <targetWord.length(); i++) {
			mask[i] = "0";//0 for not showing
			//System.out.print(mask[i]);
		}
		/*System.out.println();
		System.out.println("gameName :: "+ gameName);
		System.out.println("NumPlayer :: "+ numPlayers);*/
		
		fileScan.close();
		//targetWord = 
	}
	public void addUser(String username) {
		users.add(username);
	}
	public void moveCurr() {
		if(curr==numPlayers-1) {
			curr = 0;
		} else {
			curr++;
		}
	}
	public int returnCurr() {
		return curr;
	}
	public String returnCurrUser(){
		return users.get(curr);
	}
	public boolean decrementGuesses() {
		numGuessesLeft--;
		if(numGuessesLeft ==0) {
			return false;
		} else {
			return true;
		}
	}
	public String getName() {
		return gameName;
	}
	public int returnSize() {
		return users.size();
	}
	public boolean requirementMet() {
		return (numPlayers == users.size());
	}
	public String returnWord() {
		return targetWord;
	}
	public String[] returnMask() {
		return mask;
	}
	public int returnGuesses() {
		return numGuessesLeft;
	}
	public int returnNumPlayers() {
		return numPlayers;
	}
	public void displayWord() {
		System.out.print("Secret Word ");
		for(int i = 0;i< targetWord.length();i++) {
			if(mask[i].equals("0")) {
				System.out.print("_ ");
			} else {
				System.out.print(targetWord.charAt(i));
			}
		}
	}
	public boolean checkWin() {
		return (bitFliped == targetWord.length());
	}
	public void setMask(String[] sub) {
		mask = sub;
	}
	public void setBitFliped(int a) {
		bitFliped = a;
	}
	public boolean flipMask(String guess) {
		boolean flag = false;
		bitFliped = 0;
		for(int i=0;i<targetWord.length();i++) {
			if(guess.charAt(0)==targetWord.charAt(i)) {
				mask[i] = "1";
				flag = true;
			}
			if(mask[i].equals("1")) {
				bitFliped++;
			}
		}
		return flag;
	}
}

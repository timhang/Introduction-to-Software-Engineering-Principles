package server;

/*import java.io.Serializable;
import java.util.ArrayList;

public class HangmanMessage implements Serializable {
	public static final long serialVersionUID = 1;
    public ArrayList<String> lines = new ArrayList<>();
    public boolean needsResponse;

    HangmanMessage(boolean needsResponse) {
        this.needsResponse = needsResponse;
    }

    public HangmanMessage addLine(final String line) {
        lines.add(line);
        return this;
    }
}*/

import java.io.Serializable;

public class HangmanMessage implements Serializable {
	public static final long serialVersionUID = 1;
	
	private String name;
	private String message;
	private int state;
	private String tag;
	private String[] mask;
	private String guess;
	private boolean requirement;
	public HangmanMessage(String name, String message,String tag) {
		this.name = name;
		this.message = message;
		this.tag = tag;
	}
	public void setState(int a) {
		state = a;
	}
	public void setGuess(String a) {
		guess = a;
	}
	public String returnGuess() {
		return guess;
	}
	public String getTag() {
		return tag;
	}
	public String getName() {
		return name;
	}
	public void setMessage(String a) {
		message = a;
	}
	public void setRequirement(boolean a) {
		requirement = a;
	}
	public boolean getRequirement() {
		return requirement;
	}
	public String getMessage() {
		return message;
	}
	public void setMask(String[] a) {
		mask = a;
	}
	public String[] getMask() {
		return mask;
	}
	public int getState() {
		return state;
	}

}


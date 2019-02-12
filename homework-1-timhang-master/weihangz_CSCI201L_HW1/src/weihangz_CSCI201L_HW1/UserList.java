/*package weihangz_CSCI201L_HW1;

public class UserList {
	private Users[] userList;
	
	public UserList(Users[] userList) {
		this.userList = userList;
	}
	
	public String toString () {
		return title+", "+time+", "+date;
	}
}
*/
package weihangz_CSCI201L_HW1;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserList {
	@SerializedName("Users")
	@Expose
	private ArrayList<User> users = null;

	public ArrayList<User> getUsers() {
	return users;
	}

	public void setUsers(ArrayList<User> users) {
	this.users = users;
	}

	@Override
	public String toString()
	{
	    return "Users [Users = "+users+"]";
	}

}
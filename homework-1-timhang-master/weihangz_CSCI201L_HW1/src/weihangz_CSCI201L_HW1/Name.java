/*package weihangz_CSCI201L_HW1;

public class Name {
	private String Fname;
	private String Lname;
	public Name(String Fname,String Lname) {
		this.Fname = Fname;
		this.Lname = Lname;
	}
	
	public String getFname() {
		return this.Fname;
	}
	
	public String getLname() {
		return this.Lname;
	}
	
	public String fullName() {
		return Lname+", "+Fname;
	}
}
*/

package weihangz_CSCI201L_HW1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name implements Comparable<Name>{

@SerializedName("Fname")
@Expose
private String fname;
@SerializedName("Lname")
@Expose
private String lname;

public String getFname() {
return fname;
}

public void setFname(String fname) {
this.fname = fname;
}

public String getLname() {
return lname;
}

public void setLname(String lname) {
this.lname = lname;
}

@Override
public String toString()
{
    return lname+ ", "+fname;
}

public int compareTo(Name o) {
	// TODO Auto-generated method stub
	return this.lname.compareTo(o.lname);
}


}
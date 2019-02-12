/*package weihangz_CSCI201L_HW1;

public class Events {
	private String title;
	private String time;
	private Date date;
	
	public Events(String title, String time, Date date) {
		this.title = title;
		this.time  = time;
		this.date  = date;
	}
	
	public String toString () {
		return title+", "+time+", "+date;
	}
}
*/

package weihangz_CSCI201L_HW1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

@SerializedName("Title")
@Expose
private String title;
@SerializedName("Time")
@Expose
private String time;
@SerializedName("Date")
@Expose
private Date date;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

public Date getDate() {
return date;
}

public void setDate(Date date) {
this.date = date;
}

@Override
public String toString()
{
    return title+", "+time+", "+date;
}

}
/*package weihangz_CSCI201L_HW1;

import java.util.Vector;

public class Users {
	/*private Name name;
	private Vector<Events> eventsList = new Vector<Events>();
	public Users(Name name,Vector<Events> eventsList) {
		this.name = name;
		this.eventsList = eventsList;
	}
	
    private Name Name;

    private Events[] Events;

    public Name getName ()
    {
        return Name;
    }

    public void setName (Name Name)
    {
        this.Name = Name;
    }

    public Events[] getEvents ()
    {
        return Events;
    }

    public void setEvents (Events[] Events)
    {
        this.Events = Events;
    }

    @Override
    public String toString()
    {
        return "User [Name = "+Name+", Events = "+Events+"]";
    }
}*/

package weihangz_CSCI201L_HW1;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Comparable<User>{

@SerializedName("Name")
@Expose
private Name name;
@SerializedName("Events")
@Expose
private ArrayList<Event> events = null;

public Name getName() {
return name;
}

public void setName(Name name) {
this.name = name;
}

public ArrayList<Event> getEvents() {
return events;
}

public void setEvents(ArrayList<Event> events) {
this.events = events;
}

@Override
public String toString()
{
    if(events== null)
    	return name+"";
    else 
    	return name+"\n\t"+events;
}

@Override
public int compareTo(User o) {
    return this.name.compareTo(o.name);
}

}


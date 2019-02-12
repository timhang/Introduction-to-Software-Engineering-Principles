package weihangz_CSCI201L_HW1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Date {

@SerializedName("Month")
@Expose
private String month;
@SerializedName("Day")
@Expose
private int day;
@SerializedName("Year")
@Expose
private int year;

public String getMonth() {
return month;
}

public void setMonth(String month) {
this.month = month;
}

public int getDay() {
return day;
}

public void setDay(int day) {
this.day = day;
}

public int getYear() {
return year;
}

public void setYear(int year) {
this.year = year;
}

@Override
public String toString()
{
    return month+" "+day+", "+year;
}

}

/*package weihangz_CSCI201L_HW1;

public class Date {
	private String month;
	private int day;
	private int year;
	
	public Date(String month,int day,int year) {
		this.month = month;
		this.day   = day;
		this.year  = year;
	}
	
	public String getMonth() {
		return this.month;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public String toString() {
		return month+" "+day+", "+year;
	}
}*/

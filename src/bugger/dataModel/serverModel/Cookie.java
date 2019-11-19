package bugger.dataModel.serverModel;

import bugger.dataModel.DataModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cookie extends DataModel
	{
	public final static String param_cookieID = "cookieID";
	public final static String param_userID = "userID";
	public final static String param_timestamp = "timestamp";

	public final static String timeFormat = "yyyy-MM-dd HH:mm:ss";
	public final static long cookieExpirationTime = 24 * 60 * 60 * 1000L;

	public String cookieID;
	public String userID;
	public String timestamp;

	public Cookie(String cookieID, String userID, String timestamp)
		{
		this.cookieID = cookieID;
		this.userID = userID;
		this.timestamp = timestamp;
		}

	public static String GenerateCookieID(String userID, String timestamp)
		{
		return(GenerateID(userID,timestamp,0));
		}

	public static String GetCurrentTimeStamp()
		{
		//System.out.println("Creating Cookie");
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(Cookie.timeFormat);

		return(dateFormat.format(date));
		}

	/*
	Checks that our cookies have a valid timestamp and haven't expired
	 */
	public boolean HasValidTimeStamp()
		{
		try
			{
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat(timeFormat);
			long cookieTimestamp = dateFormat.parse(timestamp).getTime();

			System.out.println(cookieTimestamp + "/" + date.getTime() + "/" + cookieExpirationTime);

			//Return whether the distance is valid
			return(Math.abs(date.getTime() - cookieTimestamp) < cookieExpirationTime);
			}
		catch (ParseException p)
			{
			System.out.println("Invalid cookie - Bad date format");
			return(false);
			}
		}
	}

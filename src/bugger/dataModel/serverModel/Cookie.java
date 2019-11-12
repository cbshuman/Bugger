package bugger.dataModel.serverModel;

import bugger.dataModel.DataModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Cookie extends DataModel
	{
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

	public static String GenerateCookieID()
		{
		Random rand = new Random(new Date().getTime());
		StringBuilder returnValue = new StringBuilder();

		while(returnValue.length() < 35)
			{
			int number = rand.nextInt();
			returnValue.append(number);
			}

		return(returnValue.toString());
		}

	public static String GetCurrentTimeStamp()
		{
		//System.out.println("Creating Cookie");
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(Cookie.timeFormat);

		return(dateFormat.format(date));
		}

	public boolean HasValidTimeStamp()
		{
		try
			{
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat(timeFormat);
			long cookieTimestamp = dateFormat.parse(timestamp).getTime();

			//Return whether the distance is valid
			return((cookieTimestamp - date.getTime()) < cookieExpirationTime);
			}
		catch (ParseException p)
			{
			System.out.println("Invalid cookie - Bad date format");
			return(false);
			}
		}
	}

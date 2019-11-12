package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.IcookieAccess;
import bugger.dataModel.serverModel.Cookie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQL_CookieAccess implements IcookieAccess
	{
	private static String timeFormat = "yyyy-MM-dd HH:mm:ss";

	@Override
	public Cookie CreateNewCookie(String userID)
		{
		//System.out.println("Creating Cookie");
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);

		String cookieID = Cookie.GenerateCookieID((int)date.getTime());
		String timestamp = dateFormat.format(date);

		try
			{
			Connection connect = SQLDataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO Cookies(cookieID,userID,timestamp) VALUES ('"
					+ cookieID + "','"
					+ userID + "','"
					+ timestamp + "')");
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			e.printStackTrace();
			}

		//System.out.println("Created Cookie!");
		return(new Cookie(cookieID, userID, timestamp));
		}

	@Override
	public Cookie[] GetUserCookies(String userID)
		{
		ArrayList<Cookie> returnCookies = new ArrayList<>();

		try
			{
			Connection connect = SQLDataAccess.GetDatabaseConnection();

			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Cookies WHERE userID = '" + userID + "'" );

			while(result.next())
				{
				String cookieID = result.getString("cookieID");
				String timestamp = result.getString("timestamp");
				returnCookies.add(new Cookie(cookieID,userID,timestamp));
				}

			connect.close();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}

		//Transform the results into an array and return
		Cookie[] c = new Cookie[returnCookies.size()];
		returnCookies.toArray(c);
		return (c);
		}

	@Override
	public String GetUserIDFromCookie(String cookieID)
		{
		String returnID = null;

		try
			{
			Connection connect = SQLDataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();

			ResultSet result = statement.executeQuery("SELECT userID FROM Cookies WHERE cookieID = '" + cookieID + "'");

			if(result.next())
				{
				returnID = result.getString("userID");
				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			}

		return(returnID);
		}

	@Override
	public boolean VerifyCookie(String cookieID)
		{
		boolean returnValue = false;

		try
			{
			Connection connect = SQLDataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Cookies WHERE cookieID = '" + cookieID + "'");

			if(result.next())
				{
				DateFormat dateFormat = new SimpleDateFormat(timeFormat);
				String timestamp = result.getString("timestamp");

				//System.out.println(timestamp);

				Date currentDate = new Date();
				Date cookieDate = dateFormat.parse(timestamp);

				long difference = cookieDate.getTime() - currentDate.getTime();
				if((difference / (1000*60*60*24)) <= 1)
					{
					returnValue = true;
					}
				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			}

		return(returnValue);
		}
	}

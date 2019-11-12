package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.IcookieAccess;
import bugger.dataModel.serverModel.Cookie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL_CookieAccess implements IcookieAccess
	{
	@Override
	public Cookie CreateNewCookie(String userID)
		{
		//System.out.println("Creating Cookie");
		String timeStamp = Cookie.GetCurrentTimeStamp();
		String cookieID = Cookie.GenerateCookieID();

		try
			{
			Connection connect = SQLDataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO Cookies(cookieID,userID,timestamp) VALUES ('"
					+ cookieID + "','"
					+ userID + "','"
					+ timeStamp + "')");
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			e.printStackTrace();
			}

		//System.out.println("Created Cookie!");
		return(new Cookie(cookieID, userID, timeStamp));
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
	public boolean DeleteCookie(String cookieID)
		{
		boolean returnValue = false;
		try
			{
			Connection connect = SQLDataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();

			statement.executeUpdate(" DELETE FROM Cookies WHERE cookieID = '" + cookieID + "'");
			connect.close();

			returnValue = true;
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			e.printStackTrace();
			}

		//System.out.println("Created Cookie!");
		return(returnValue);
		}

	}

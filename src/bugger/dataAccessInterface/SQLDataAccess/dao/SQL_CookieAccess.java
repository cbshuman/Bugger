package bugger.dataAccessInterface.SQLDataAccess.dao;

import bugger.dataAccessInterface.SQLDataAccess.SQL_DataAccess;
import bugger.dataAccessInterface.dao.IcookieAccess;
import bugger.dataModel.serverModel.Cookie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL_CookieAccess extends SQL_DAO<Cookie> implements IcookieAccess
	{
	@Override
	public Cookie GetByParameter(String query, String parameter)
		{
		return GetByParameter(query,parameter, SQL_DataAccess.table_cookies);
		}

	@Override
	protected Cookie ParseSQLDataSet(ResultSet resultSet) throws SQLException
		{
		String userID = resultSet.getString(Cookie.param_userID);
		String cookieID = resultSet.getString(Cookie.param_cookieID);
		String timestamp = resultSet.getString(Cookie.param_timestamp);

		Cookie returnCookie = new Cookie(cookieID, userID, timestamp);

		return returnCookie;
		}

	public boolean CheckForValidID(String id)
		{
		boolean returnValue = false;
		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM " + SQL_DataAccess.table_cookies + " WHERE " + Cookie.param_cookieID +  " = '" + id + "'" );

			//Check that we have a result - if we do, then the id is taken
			returnValue = !result.next();
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find cookie! Exception: " + e.getMessage());
			e.printStackTrace();
			}

		return (returnValue);
		}

	@Override
	public Cookie CreateNewCookie(String userID)
		{
		//System.out.println("Creating Cookie");
		String timeStamp = Cookie.GetCurrentTimeStamp();
		String cookieID = Cookie.GenerateCookieID(userID,timeStamp);

		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO " + SQL_DataAccess.table_cookies + "("
					+ Cookie.param_cookieID + ","
					+ Cookie.param_userID + ","
					+ Cookie.param_timestamp
					+ ") VALUES ('"
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
			Connection connect = SQL_DataAccess.GetDatabaseConnection();

			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM " + SQL_DataAccess.table_cookies + " WHERE " + Cookie.param_userID + " = '" + userID + "'" );

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
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();

			statement.executeUpdate(" DELETE FROM " + SQL_DataAccess.table_cookies + " WHERE cookieID = '" + cookieID + "'");
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

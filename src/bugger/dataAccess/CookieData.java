package bugger.dataAccess;

/*
public class CookieData
	{
	private static String timeFormat = "yyyy-MM-dd HH:mm:ss";

	public static Cookie CreateNewCookie(String userID)
		{
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);

		String cookieID = Cookie.GenerateCookieID();
		String timestamp = dateFormat.format(date);

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
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

		return(new Cookie(cookieID, userID, timestamp));
		}

	public static String GetUserFromCookie(String cookieID)
		{
		String returnID = null;

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
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

	public static boolean VerifyCookie(String cookieID)
		{
		boolean returnValue = false;

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
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
*/
package bugger.dataModel;

import bugger.dataAccess.DataAccess;

public class Cookie extends DataModel
	{
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
		StringBuilder returnValue = new StringBuilder();
		int number = DataAccess.GetLastID("lastCookieID");

		returnValue.append("Cookie_");
		returnValue.append(number);

		while(returnValue.length() < 35)
			{
			returnValue.append(0);
			}

		return(returnValue.toString());
		}
	}

package bugger.dataModel.serverModel;

import bugger.dataModel.DataModel;

import java.util.Random;

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

	public static String GenerateCookieID(int seed)
		{
		Random rand = new Random(seed);
		StringBuilder returnValue = new StringBuilder();
		int number = rand.nextInt();

		returnValue.append("Cookie_");
		returnValue.append(number);

		while(returnValue.length() < 35)
			{
			returnValue.append(0);
			}

		return(returnValue.toString());
		}

	public boolean ValidTimeStamp()
		{
		return(true);
		}
	}

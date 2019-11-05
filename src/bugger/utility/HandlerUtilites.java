package bugger.utility;

import bugger.dataModel.serverModel.User;
import com.sun.net.httpserver.Headers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HandlerUtilites
	{
	public static String GetCookieIDFromCookie(Headers headers)
		{
		String returnValue = null;
		for (Map.Entry<String,List<String>> entry : headers.entrySet())
			{
    		//System.out.println("Key: " + entry.getKey() + ": \n -" + entry.getValue());
			if(entry.getKey().toLowerCase().equals("cookie"))
				{
				//System.out.println(" -- Found a sweet cookie! (::) -> Contents -- ");
				Iterator<String> strings = entry.getValue().iterator();
				while(strings.hasNext())
					{
					String cookieChip = strings.next();
					//System.out.println("   -> " + cookieChip);
					returnValue = PullUserID(cookieChip);
					}
				}
			}
		return(returnValue);
		}

	private static String PullUserID(String inputString)
		{
		String returnString = null;
		//System.out.println("Substring: " + inputString.substring(0,6));
		if(inputString.substring(0,6).equals("UserID"))
			{
			returnString = inputString.substring(7);
			}
		return(returnString);
		}

	public static User GetUserFromCookie(String cookieContents)
		{
		/*
		String userID = CookieData.GetUserFromCookie(cookieContents);
		User user = DataProxy.GetUserByParameter(userID,"userID");
		user.GetPermissions();
		 */
		return(null);
		}
	}

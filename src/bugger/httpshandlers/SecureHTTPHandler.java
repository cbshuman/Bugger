package bugger.httpshandlers;

import bugger.command.BuggerCMD;
import bugger.command.BuggerCommand;
import bugger.command.ValidateCookie;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;

public abstract class SecureHTTPHandler implements HttpHandler
	{
	private class CookieData
		{
		public String cookieID;
		public String userID;

		 public CookieData(String cookieID,String userID)
			{
			this.cookieID = cookieID;
			this.userID = userID;
			}
		}
	private Object List;

	public boolean HasValidCookie(Headers headers)
		{
		boolean returnValue = false;

		System.out.println("Loorking for cookie . . .");
		//Iterate through the headers to find our cookie

		java.util.List<String> cookies = headers.get("Cookie");
		if (cookies != null)
			{
			for (String value : cookies)
				{
				if (value.contains(LoginHandler.CookieString))
					{
					System.out.println("Found the cookie:" + value);
					//Get the data from the string list
					CookieData cookieData = ParseCookies(value);
					//Check the cookie for validation
					BuggerCommand<Boolean> validation = BuggerCMD.DoCommand(new ValidateCookie(cookieData.userID, cookieData.cookieID));
					returnValue = validation.GetReturnValue();
					}
				}
			}
		//Return the results
		return (returnValue);
		}

	private CookieData ParseCookies(String value)
		{
		CookieData returnValue = null;

		int equalsIndex = value.indexOf('=');
		int colonIndex = value.indexOf(':');

		for(int i = 0; i < value.length(); i++)
			{
			
			}

		System.out.println(value.substring(equalsIndex,colonIndex));
		System.out.println(value.substring(colonIndex));

		return returnValue;
		}
	}

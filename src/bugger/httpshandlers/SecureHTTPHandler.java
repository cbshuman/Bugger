package bugger.httpshandlers;

import bugger.command.BuggerCMD;
import bugger.command.BuggerCommand;
import bugger.command.cookieCMD.CMD_ValidateCookie;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;

abstract class SecureHTTPHandler implements HttpHandler
	{
	private class CookieData
		{
		public String cookieID;
		public String userID;

		 CookieData(String cookieID, String userID)
			{
			this.cookieID = cookieID;
			this.userID = userID;
			}
		}

	private CookieData requestData;

	 String GetHandlerUserID()
		{
		return(requestData.userID);
		}

	boolean HasValidCookie(Headers headers)
		{
		boolean returnValue = false;
		//Iterate through the headers to find our cookie

		java.util.List<String> cookies = headers.get("Cookie");
		if (cookies != null)
			{
			for (String value : cookies)
				{
				if (value.contains(LoginHandler.CookieString))
					{
					//Get the data from the string list
					returnValue = ParseCookies(value);
					}

				if(returnValue == true)
					{
					break;
					}
				}
			}
		//Return the results
		return (returnValue);
		}

	private boolean ParseCookies(String value)
		{
		System.out.println(" -> Looking through cookies . . .");
		boolean returnValue = false;
		int semiColonIndex = 0;
		String currentCookie = value;

		while(semiColonIndex < currentCookie.length())
			{
			int cookieStart = value.indexOf(LoginHandler.CookieString);

			currentCookie = value.substring(cookieStart);

			int equalsIndex = currentCookie.indexOf('=');
			int colonIndex = currentCookie.indexOf(':');
			semiColonIndex = currentCookie.indexOf(';') - 1;

			if(semiColonIndex <= 0)
				{
				semiColonIndex = currentCookie.length();
				}

			if(equalsIndex > -1 && colonIndex > -1)
				{
				String cookieData = currentCookie.substring(equalsIndex + 1,colonIndex - 1);
				String userID = currentCookie.substring(colonIndex +  1,semiColonIndex);

				BuggerCommand<Boolean> validation = BuggerCMD.DoCommand(new CMD_ValidateCookie(userID, cookieData));
				returnValue = validation.GetReturnValue();

				if(returnValue == true)
					{
					requestData = new CookieData(cookieData,userID);
					break;
					}
				}
			}

		return returnValue;
		}
	}

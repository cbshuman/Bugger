package bugger.command.cookieCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Cookie;

public class CMD_ValidateCookie extends BuggerCommand<Boolean>
	{
	private String username;
	private String cookieID;
	private boolean returnValue = false;

	public CMD_ValidateCookie(String username, String cookieID)
		{
		this.username = username;
		this.cookieID = cookieID;
		}

	@Override
	public void DoCommand()
		{
		Cookie[] cookies = DataProxy.GetUserCookies(username);

		for (Cookie cookie : cookies)
			{
			//System.out.println(" -> '" + cookieID + "' ? '" + cookie.cookieID + "'");
			//System.out.println(" -> " + cookieID.equals(cookie.cookieID) + " / " + cookie.HasValidTimeStamp());
			if (cookie.HasValidTimeStamp() && cookieID.equals(cookie.cookieID))
				{
				returnValue = true;
				}
			else if(cookieID.equals(cookie.cookieID))
				{
				DataProxy.DeleteCookie(cookie.cookieID);
				}
			}

		commandSuccessful = returnValue;
		}

	@Override
	public Boolean GetReturnValue()
		{
		return CommandSuccessful();
		}
	}

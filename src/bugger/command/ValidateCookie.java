package bugger.command;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Cookie;

public class ValidateCookie extends BuggerCommand<Boolean>
	{
	private String username;
	private String cookieID;
	private boolean returnValue = false;

	public ValidateCookie(String username, String cookieID)
		{
		this.username = username;
		}

	@Override
	public void DoCommand()
		{
		Cookie[] cookies = DataProxy.GetUserCookies(username);

		for (Cookie cookie : cookies)
			{
			if (cookie.HasValidTimeStamp())
				{
				returnValue = true;
				}
			else
				{
				DataProxy.DeleteCookie(cookie.cookieID);
				}
			}
		}

	@Override
	public Boolean CommandSuccessful()
		{
		return returnValue;
		}

	@Override
	public Boolean GetReturnValue()
		{
		return CommandSuccessful();
		}
	}

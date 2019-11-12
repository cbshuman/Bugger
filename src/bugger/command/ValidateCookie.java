package bugger.command;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Cookie;

public class ValidateCookie extends BuggerCommand<Boolean>
	{
	String username;
	String cookieID;
	boolean returnValue = false;

	public ValidateCookie(String username, String cookieID)
		{
		this.username = username;
		}

	@Override
	public void DoCommand()
		{
		Cookie[] cookies = DataProxy.GetUserCookies(username);

		for(int i = 0; i < cookies.length; i++)
			{
			if(cookies[i].ValidTimeStamp())
				{
				returnValue = true;
				}
			else
				{
				//TODO : Delete old cookies
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

package bugger.command;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Cookie;

public class CreateCookie extends BuggerCommand<Cookie>
	{
	private String userName;
	private Cookie returnValue;

	public CreateCookie(String userName)
		{
		this.userName = userName;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			try
				{
				returnValue = DataProxy.CreateNewCookie(userName);
				commandSuccessful = true;
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			commandSuccessful = false;
			}
		}


	@Override
	public Cookie GetReturnValue()
		{
		return (returnValue);
		}
	}

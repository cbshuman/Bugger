package bugger.command;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Cookie;

public class CreateCookie implements BuggerCommand<Cookie>
	{
	private boolean hasRun = false;
	private boolean runSuccessfully;
	private String userName;
	private Cookie returnValue;

	public CreateCookie(String userName)
		{
		this.userName = userName;
		}

	@Override
	public void DoCommand()
		{
		if(!hasRun)
			{
			try
				{
				returnValue = DataProxy.CreateNewCookie(userName);
				runSuccessfully = true;
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			hasRun = false;
			}
		}

	@Override
	public Boolean CommandSuccessful()
		{
		return runSuccessfully;
		}

	@Override
	public Cookie GetReturnValue()
		{
		return (returnValue);
		}
	}

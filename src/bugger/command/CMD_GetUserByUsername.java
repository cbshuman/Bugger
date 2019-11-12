package bugger.command;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.User;

public class CMD_GetUserByUsername extends BuggerCommand<User>
	{
	private String username;
	private User user;

	public CMD_GetUserByUsername(String username)
		{
		this.username = username;
		user = null;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			user = DataProxy.GetUserByParameter(username, "username");
			commandSuccessful = true;
			}
		}

	@Override
	public User GetReturnValue()
		{
		return user;
		}
	}

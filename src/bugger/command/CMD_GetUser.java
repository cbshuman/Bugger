package bugger.command;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.User;

public class CMD_GetUser extends BuggerCommand<User>
	{
	String username;
	User user;

	public CMD_GetUser(String username)
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

package bugger.command.userCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.User;

public class CMD_GetUserByUsername extends BuggerCommand<User>
	{
	private String username;
	private User user = null;

	public CMD_GetUserByUsername(String username)
		{
		this.username = username;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			user = DataProxy.GetUserByParameter(username, User.param_username);
			commandSuccessful = (user != null);
			}
		}

	@Override
	public User GetReturnValue()
		{
		return user;
		}
	}

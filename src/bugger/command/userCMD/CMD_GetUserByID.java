package bugger.command.userCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.User;

public class CMD_GetUserByID extends BuggerCommand<User>
	{
	private String username;
	private User user;

	public CMD_GetUserByID(String username)
		{
		this.username = username;
		user = null;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			user = DataProxy.GetUserByParameter(username, User.param_userID);
			commandSuccessful = (user != null);
			}
		}

	@Override
	public User GetReturnValue()
		{
		return user;
		}
	}

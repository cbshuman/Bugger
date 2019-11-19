package bugger.command.userCMD;

import bugger.dataModel.serverModel.User;

public class CMD_GetUserByUsername extends CMD_GetUser
	{
	private String username;

	public CMD_GetUserByUsername(String username)
		{
		this.username = username;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			commandSuccessful = GetUser(User.param_username,username);
			}
		}
	}

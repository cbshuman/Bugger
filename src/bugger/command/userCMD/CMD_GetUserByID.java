package bugger.command.userCMD;

import bugger.dataModel.serverModel.User;

public class CMD_GetUserByID extends CMD_GetUser
	{
	private String username;

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
			commandSuccessful = GetUser(User.param_userID,username);
			}
		}
	}

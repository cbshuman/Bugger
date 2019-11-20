package bugger.command.permissionCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;

public class CMD_AddUserPermission extends BuggerCommand<Boolean>
	{
	String permissionID;
	String userID;

	public CMD_AddUserPermission(String permissionID, String userID)
		{
		this.permissionID = permissionID;
		this.userID = userID;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			commandSuccessful = DataProxy.AddPermissionToUser(permissionID, userID);
			}
		}

	@Override
	public Boolean GetReturnValue()
		{
		return (commandSuccessful);
		}
	}

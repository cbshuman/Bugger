package bugger.command.permissionCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Permission;

public class CMD_CreatePermission extends BuggerCommand<Permission>
	{
	Permission returnValue = null;

	public CMD_CreatePermission(String permissionName, String permissionDisc)
		{
		returnValue = new Permission(permissionName,permissionDisc);
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			commandSuccessful = DataProxy.CreateNewPermission(returnValue);
			}
		}

	@Override
	public Permission GetReturnValue()
		{
		return (returnValue);
		}
	}

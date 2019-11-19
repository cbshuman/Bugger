package bugger.command.permissionCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Permission;

public class CMD_GetPermissionByName extends BuggerCommand<Permission>
	{
	private String permissionName;
	private Permission permission;

	public CMD_GetPermissionByName(String permissionName)
		{
		this.permissionName = permissionName;
		permission = null;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			permission = DataProxy.GetPermission(permissionName, Permission.param_permissionName);
			commandSuccessful = (permission != null);
			}
		}

	@Override
	public Permission GetReturnValue()
		{
		return permission;
		}
	}

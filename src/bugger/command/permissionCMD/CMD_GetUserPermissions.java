package bugger.command.permissionCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Permission;

import java.util.List;

public class CMD_GetUserPermissions extends BuggerCommand<List<Permission>>
	{
	private String userID;
	private List<Permission> permissions;

	public CMD_GetUserPermissions(String userID)
		{
		this.userID = userID;
		permissions = null;
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			permissions = DataProxy.GetPermissionList(userID);
			commandSuccessful = (permissions != null);
			}
		}

	@Override
	public List<Permission> GetReturnValue()
		{
		return permissions;
		}
	}

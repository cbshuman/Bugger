package bugger.command.userCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.User;

import java.util.List;

public abstract class CMD_GetUser extends BuggerCommand<User>
	{
	protected User user;

	boolean GetUser(String value,String parameter)
		{
		user = DataProxy.GetUserByParameter(value,parameter);

		//System.out.println(" -> Found this: " + user + " - Parameter: '" + parameter + "' Value: '" + value + "'");

		if(user != null)
			{
			List<Permission> permissionList = DataProxy.GetPermissionList(user.userID);
			Permission[] permissions = new Permission[permissionList.size()];
			permissionList.toArray(permissions);
			user.permissions = permissions;
			}

		return(user != null);
		}

	@Override
	public User GetReturnValue()
		{
		return user;
		}
	}

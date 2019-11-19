package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.Permission;

import java.util.List;

public interface IpermissionAccess extends Idao<Permission>
	{
	boolean CreatePermission(Permission permission);
	boolean GetPermissionExists(String userID);
	List<Permission> GetPermissions(String userID);
	}

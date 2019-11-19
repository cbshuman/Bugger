package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.Permission;

import java.util.List;

public interface IpermissionAccess
	{
	boolean CreatePermission(Permission permission);
	boolean GetPermissionExists(String userID);
	Permission GetPermissionByParameter(String permissionID, String parameter);
	List<Permission> GetPermissionList(String userID);
	}

package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.Permission;

import java.util.List;

public interface IpermissionAccess extends Idao<Permission>
	{
	boolean CreatePermission(Permission permission);
	boolean GetPermissionExists(String userID);
	boolean AddPermissionToUser( String permissionID,String userID);
	List<Permission> GetUserPermissionList(String userID);
	}

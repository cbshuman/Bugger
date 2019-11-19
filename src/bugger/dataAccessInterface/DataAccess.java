package bugger.dataAccessInterface;

import bugger.dataAccessInterface.dao.IcookieAccess;
import bugger.dataAccessInterface.dao.IpermissionAccess;
import bugger.dataAccessInterface.dao.IuserAccess;
import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.User;

import java.util.List;

public abstract class DataAccess
    {
    protected IuserAccess userAccess;
    protected IcookieAccess cookieAccess;
    protected IpermissionAccess permissionAccess;

    public boolean CreateUser(User targetUser)
        {
        return(userAccess.CreateUser(targetUser));
        }

    //------------ Users --------------- \\
    public User[] GetAllUsers()
        {
        return(userAccess.GetAllUsers());
        }

    public User GetUserProfile()
        {
        return(userAccess.GetUserProfile());
        }

    public User GetUserByParameter(String query, String parameter)
        {
        return(userAccess.GetByParameter(query,parameter));
        }

    public boolean DeleteUser()
        {
        return(userAccess.DeleteUser());
        }

    public boolean GetUserExisits(String username)
        {
        return(userAccess.GetUserExisits(username));
        }

    //------------ Cookies --------------- \\

    public Cookie[] GetUserCookies(String username)
        {
        return(cookieAccess.GetUserCookies(username));
        }

    public Cookie CreateNewCookie(String username)
        {
        return(cookieAccess.CreateNewCookie(username));
        }

    public boolean DeleteCookie(String cookieID)
        {
        return(cookieAccess.DeleteCookie(cookieID));
        }

    //------------ Permissions --------------- \\

    public boolean CreateNewPermission(Permission permission)
        {
        return(permissionAccess.CreatePermission(permission));
        }

    public List<Permission> GetPermissionList(String userID)
        {
        return(permissionAccess.GetPermissions(userID));
        }

    public Permission GetPermission(String permissionID,String parameter)
        {
        return(permissionAccess.GetByParameter(permissionID, parameter));
        }

    public abstract void ValidateDatabase();
    }
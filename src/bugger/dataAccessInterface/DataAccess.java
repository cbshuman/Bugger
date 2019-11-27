package bugger.dataAccessInterface;

import bugger.dataAccessInterface.dao.IcookieAccess;
import bugger.dataAccessInterface.dao.IpermissionAccess;
import bugger.dataAccessInterface.dao.IprojectAccess;
import bugger.dataAccessInterface.dao.IuserAccess;
import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.Project;
import bugger.dataModel.serverModel.User;

import java.util.List;

public abstract class DataAccess
    {
    protected IuserAccess userAccess;
    protected IcookieAccess cookieAccess;
    protected IpermissionAccess permissionAccess;
    protected IprojectAccess projectAccess;

    //------------ Users --------------- \\

    boolean CreateUser(User targetUser)
        {
        return(userAccess.CreateUser(targetUser));
        }

    User[] GetAllUsers()
        {
        return(userAccess.GetAllUsers());
        }

    User GetUserProfile()
        {
        return(userAccess.GetUserProfile());
        }

    User GetUserByParameter(String query, String parameter)
        {
        return(userAccess.GetByParameter(query,parameter));
        }

    boolean DeleteUser()
        {
        return(userAccess.DeleteUser());
        }

    boolean GetUserExistsByUsername(String username)
        {
        return(userAccess.GetUserExistsByUsername(username));
        }

    boolean GetUserExistsByID(String userID)
        {
        return(userAccess.GetUserExistsByID(userID));
        }

    //------------ Cookies --------------- \\

    Cookie[] GetUserCookies(String username)
        {
        return(cookieAccess.GetUserCookies(username));
        }

    Cookie CreateNewCookie(String username)
        {
        return(cookieAccess.CreateNewCookie(username));
        }

    boolean DeleteCookie(String cookieID)
        {
        return(cookieAccess.DeleteCookie(cookieID));
        }

    //------------ Permissions --------------- \\

    boolean CreateNewPermission(Permission permission)
        {
        return(permissionAccess.CreatePermission(permission));
        }

    List<Permission> GetUserPermissionList(String userID)
        {
        return(permissionAccess.GetUserPermissionList(userID));
        }

    Permission GetPermission(String permissionID, String parameter)
        {
        return(permissionAccess.GetByParameter(permissionID, parameter));
        }

    boolean AddPermissionToUser(String permissionID, String userID)
        {
        return (permissionAccess.AddPermissionToUser(permissionID,userID));
        }

    //------------ Projects --------------- \\

    boolean AddProject(Project targetProject)
        {
        return(projectAccess.CreateProject(targetProject));
        }

    public abstract void ValidateDatabase();
    }
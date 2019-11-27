package bugger.dataAccessInterface;

import bugger.dataAccessInterface.dao.IcookieAccess;
import bugger.dataAccessInterface.dao.IpermissionAccess;
import bugger.dataAccessInterface.dao.IprojectAccess;
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
    protected IprojectAccess projectAccess;

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

    public boolean GetUserExistsByUsername(String username)
        {
        return(userAccess.GetUserExistsByUsername(username));
        }

    public boolean GetUserExistsByID(String userID)
        {
        return(userAccess.GetUserExistsByID(userID));
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

    public List<Permission> GetUserPermissionList(String userID)
        {
        return(permissionAccess.GetUserPermissionList(userID));
        }

    public Permission GetPermission(String permissionID,String parameter)
        {
        return(permissionAccess.GetByParameter(permissionID, parameter));
        }

    public  boolean AddPermissionToUser(String permissionID, String userID)
        {
        return (permissionAccess.AddPermissionToUser(permissionID,userID));
        }

    //------------ Projects --------------- \\

    public boolean AddProject(String projectName, String projectDisc, String[] permissions, String defaultAssignee)
        {
        return(projectAccess.CreateProject(projectName,projectDisc,permissions,defaultAssignee));
        }

    public abstract void ValidateDatabase();
    }
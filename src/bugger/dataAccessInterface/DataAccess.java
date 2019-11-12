package bugger.dataAccessInterface;

import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.User;

public abstract class DataAccess
    {
    protected IuserAccess userAccess;
    protected IcookieAccess cookieAccess;

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
        return(userAccess.GetUserByParameter(query,parameter));
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

    public boolean DeleteCookie(String cookieID)
        {
        return(cookieAccess.DeleteCookie(cookieID));
        }

    public Cookie CreateNewCookie(String username)
        {
        return(cookieAccess.CreateNewCookie(username));
        }

    public abstract void ValidateDatabase();
    }
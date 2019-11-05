package bugger.dataAccessInterface;

import bugger.dataModel.User;

public abstract class DataAccess
    {
    protected IuserAccess userAccess;

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


    public abstract void ValidateDatabase();
    }

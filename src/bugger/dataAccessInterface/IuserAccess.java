package bugger.dataAccessInterface;

import bugger.dataModel.User;

public interface IuserAccess
    {
    User[] GetAllUsers();
    User GetUserProfile();
    User GetUserByParameter(String query, String parameter);
    boolean CreateUser(User targetUser);
    boolean DeleteUser();
    boolean GetUserExisits(String username);
    }

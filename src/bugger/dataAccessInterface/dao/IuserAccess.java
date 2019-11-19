package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.User;

public interface IuserAccess
    {
    User[] GetAllUsers();
    User GetUserProfile();
    User GetUserByParameter(String query, String parameter);
    boolean CreateUser(User targetUser);
    boolean DeleteUser();
    boolean GetUserExisits(String username);
    }

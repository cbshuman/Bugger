package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.User;

public interface IuserAccess extends Idao<User>
    {
    User[] GetAllUsers();
    User GetUserProfile();
    boolean CreateUser(User targetUser);
    boolean DeleteUser();
    boolean GetUserExisits(String username);
    }

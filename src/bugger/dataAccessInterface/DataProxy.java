package bugger.dataAccessInterface;

import bugger.dataAccessInterface.SQLDataAccess.SQLDataAccess;
import bugger.dataModel.User;

public class DataProxy
    {
    public enum DatabaseType
        {
        SQL
        }    

    private static DataAccess dataAccess;

    public static void SelectDatabase(DatabaseType database) throws Exception
        {
        switch(database)
            {
            case SQL:
                dataAccess = new SQLDataAccess();
                break;
            default:
                throw(new Exception("Unsupported Database!") );
            }
        }

    public static void ValidateDatabase()
        {
        dataAccess.ValidateDatabase();
        }

    public static User[] GetAllUsers()
        {
        return(dataAccess.GetAllUsers());
        }

    public static User GetUserProfile()
        {
        return(dataAccess.GetUserProfile());
        }

    public static User GetUserByParameter(String query, String parameter)
        {
        return(dataAccess.GetUserByParameter(query,parameter));
        }

    public static boolean DeleteUser()
        {
        return(dataAccess.DeleteUser());
        }

    public static boolean GetUserExisits(String username)
        {
        return(dataAccess.GetUserExisits(username));
        }

    }

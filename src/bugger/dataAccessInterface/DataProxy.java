package bugger.dataAccessInterface;

import bugger.command.BuggerCMD;
import bugger.command.BuggerCommand;
import bugger.command.permissionCMD.CMD_AddUserPermission;
import bugger.command.permissionCMD.CMD_CreatePermission;
import bugger.command.permissionCMD.CMD_GetPermissionByName;
import bugger.command.userCMD.CMD_CreateUser;
import bugger.command.userCMD.CMD_GetUserByUsername;
import bugger.dataAccessInterface.SQLDataAccess.SQL_DataAccess;
import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.User;

import java.util.List;

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
                dataAccess = new SQL_DataAccess();
                break;
            default:
                throw(new Exception("Unsupported Database!") );
            }
        }

    /*
    Makes sure that everything in the database is set up and ready to run
     */
    public static void ValidateDatabase()
        {
        //Checks that the admin account was made at some point
        boolean createdAdmin = true;
        //Validates the database in use (creates needed tables and the like)
        dataAccess.ValidateDatabase();

        //Check that the admin account exists in the table
        BuggerCommand<User> adminExists = BuggerCMD.DoCommand(new CMD_GetUserByUsername("admin"));
        if(!adminExists.CommandSuccessful())
            {
            //If it doesn't exist, create it
            System.out.println("\n --- Cannot find administrator account, creating it now . . .");

            BuggerCommand<Boolean> createAdmin = BuggerCMD.DoCommand(new CMD_CreateUser("admin","","admin","admin","sudo", "su"));
            boolean created = createAdmin.CommandSuccessful();

            System.out.println("Admin Creation Success: " + created + "\n");

            createdAdmin = false;
            }

        //Check that the admin permission exists in the database
        BuggerCommand<User> adminPermission = BuggerCMD.DoCommand(new CMD_GetPermissionByName("admin"));
        if(!adminPermission.CommandSuccessful())
            {
            //If it doesn't exist, create it
            System.out.println("\n --- Cannot find administrator permission, creating it now . . .");

            BuggerCommand createAdminPermission = BuggerCMD.DoCommand(new CMD_CreatePermission("Admin", "The Admin Group is for the administration of Bugger, and grants access to administrative functions."));
            boolean created = createAdminPermission.CommandSuccessful();

            System.out.println("Admin Permission Creation Success: " + created + "\n");

            createdAdmin = false;
            }

        //If either had to be created, make sure to add this to the permissions table
        if(!createdAdmin)
            {
            System.out.println("Adding admin user to Admin security group . . . ");
            //Get the admin user
            BuggerCommand<User> adminUserC = BuggerCMD.DoCommand(new CMD_GetUserByUsername("admin"));
            User adminUser = adminUserC.GetReturnValue();

            BuggerCommand<Permission> adminPer = BuggerCMD.DoCommand(new CMD_GetPermissionByName("Admin"));
            Permission adminPermiss = adminPer.GetReturnValue();

            BuggerCommand<Boolean> addedAdminToAdmin = BuggerCMD.DoCommand(new CMD_AddUserPermission(adminPermiss.permissionID,adminUser.userID));

            System.out.println("Added admin user to Admin security group success: " + addedAdminToAdmin.CommandSuccessful() + "\n");
            }
        }

    // ----  User Data ---- \\

    public static Boolean CreatenewUser(User user)
        {
        return(dataAccess.CreateUser(user));
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

    public static boolean GetUserExistsByUsername(String username)
        {
        return(dataAccess.GetUserExistsByUsername(username));
        }

    public static boolean GetUserExistsByID(String userID)
        {
        return(dataAccess.GetUserExistsByID(userID));
        }

    // ----  Cookie Data ---- \\

    public static Cookie CreateNewCookie(String username)
        {
        return(dataAccess.CreateNewCookie(username));
        }

    public static boolean DeleteCookie(String cookieID)
        {
        return(dataAccess.DeleteCookie(cookieID));
        }

    public static Cookie[] GetUserCookies(String username)
        {
        return(dataAccess.GetUserCookies(username));
        }

    // ----  Permission Data ---- \\

    public static boolean CreateNewPermission(Permission permission)
        {
        return(dataAccess.CreateNewPermission(permission));
        }

    public static List<Permission> GetPermissionList(String userID)
        {
        return(dataAccess.GetPermissionList(userID));
        }

    public static Permission GetPermission(String permissionID,String parameter)
        {
        return(dataAccess.GetPermission(permissionID,parameter));
        }

    public static boolean AddPermissionToUser(String permissionID, String userID)
        {
        return(dataAccess.AddPermissionToUser(permissionID, userID));
        }
    }

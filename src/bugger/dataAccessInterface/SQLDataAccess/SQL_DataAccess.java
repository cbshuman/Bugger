package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.DataAccess;
import bugger.dataAccessInterface.SQLDataAccess.dao.SQL_CookieAccess;
import bugger.dataAccessInterface.SQLDataAccess.dao.SQL_PermissionAccess;
import bugger.dataAccessInterface.SQLDataAccess.dao.SQL_ProjectAccess;
import bugger.dataAccessInterface.SQLDataAccess.dao.SQL_UserAccess;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.Project;
import bugger.dataModel.serverModel.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL_DataAccess extends DataAccess
    {
    //Tables needed for SQL Bugger to work - should be normalized to at least 3rd normal form
	public static final String table_user = "User";
	public static final String table_cookies = "Cookies";
	public static final String table_permission = "Permission";
	public static final String table_project = "Project";
	public static final String table_user_permission = "UserPermission";
	public static final String table_project_permission = "ProjectPermission";

	//Will also make these customizable later
	public static final String databaseUserName = "bugger";
	public static final String databasePassword = "letmein2c01_";
	public static final String databaseConnection = "jdbc:mysql://localhost/bugger?user="+databaseUserName+"&password="+databasePassword+"&serverTimezone=UTC&useSSL=false";

    public SQL_DataAccess()
        {
        userAccess = new SQL_UserAccess();
        cookieAccess = new SQL_CookieAccess();
		permissionAccess = new SQL_PermissionAccess();
		projectAccess = new SQL_ProjectAccess();
        }

    public static Connection GetDatabaseConnection() throws SQLException
        {
        return(DriverManager.getConnection(databaseConnection));
        }

    //Creates tables needed for basic opperation if they don't already exist in the database
    public void ValidateDatabase()
        {
		try
			{
		    Connection connect = GetDatabaseConnection();
		    Statement statement = connect.createStatement();

		    //Create user table
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS ServerData("
								    + "serverID VARCHAR(255) NOT NULL,"
								    + "lastUserID int NOT NULL,"
								    + "lastCookieID int NOT NULL,"
								    + "lastPermID int NOT NULL,"
								    + "lastProjectID int NOT NULL,"
								    + "lastBugID int NOT NULL)");

		    //Create user table
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+ table_user + "("
								    + User.param_userID + " VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + User.param_username + " VARCHAR(255) NOT NULL,"
								    + User.param_email + " VARCHAR(255) NOT NULL,"
								    + User.param_hashedPassword + " TEXT NOT NULL,"
								    + User.param_alias + " VARCHAR(255),"
								    + User.param_firstName + " VARCHAR(255) NOT NULL,"
								    + User.param_lastName + " VARCHAR(255) NOT NULL,"
								    + User.param_enabled + " BOOLEAN DEFAULT true)");

		    //Create a Table to store user cookies
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_cookies + "("
								    + "cookieID VARCHAR(255) NOT NULL,"
								    + "userID VARCHAR(255) NOT NULL,"
								    + "timestamp DATETIME NOT NULL,"
								    + "FOREIGN KEY (userID) REFERENCES User(userID))");

		    //Create a table to store Permission groups
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_permission + "("
								    + Permission.param_permissionID + " VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + Permission.param_permissionName + " VARCHAR(255) NOT NULL,"
								    + Permission.param_discription + " TEXT)");

		    //Create a Table to store Project Information
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_project + "("
								    + Project.param_projectID + " VARCHAR(255) NOT NULL PRIMARY KEY,"
									+ Project.param_projectName + " VARCHAR(255) NOT NULL,"
									+ Project.param_defaultAssign + " VARCHAR(255) NOT NULL,"
									+ Project.param_description + " TEXT)");

		    //Create User Permission Table
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_user_permission + "("
								    + Permission.param_permissionID + " VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + User.param_userID + " VARCHAR(255) NOT NULL,"
								    + "FOREIGN KEY (" + Permission.param_permissionID + ") REFERENCES Permission(" + Permission.param_permissionID + "),"
								    + "FOREIGN KEY (" + User.param_userID + ") REFERENCES " + table_user + "(" + User.param_userID + "))");

		    //Create User Permission Table
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_project_permission + "("
								    + "permissionID VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + "projectID VARCHAR(255) NOT NULL,"
								    + "FOREIGN KEY (permissionID) REFERENCES Permission(permissionID),"
								    + "FOREIGN KEY (projectID) REFERENCES Project(projectID))");

            //Close the database connection
            connect.close();
            }
		catch (Exception e)
			{
            System.out.println("Error validating the Database! Error:");
			System.out.println(e.getMessage());
			e.printStackTrace();
        	}
        //End of validate Database
        }
    }

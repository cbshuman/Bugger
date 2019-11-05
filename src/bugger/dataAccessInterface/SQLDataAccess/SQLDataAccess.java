package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.DataAccess;
import bugger.dataModel.serverModel.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDataAccess extends DataAccess
    {
	//Will also make these customizable later
	public static final String databaseUserName = "bugger";
	public static final String databasePassword = "letmein2c01_";
	public static final String databaseConnection = "jdbc:mysql://localhost/bugger?user="+databaseUserName+"&password="+databasePassword+"&serverTimezone=UTC&useSSL=false";

    public SQLDataAccess()
        {
        userAccess = new SQL_UserAccess();
        cookieAccess = new SQL_CookieAccess();
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
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS User("
								    + "userID VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + "username VARCHAR(255) NOT NULL,"
								    + "email VARCHAR(255) NOT NULL,"
								    + "password TEXT NOT NULL," 
								    + "alias VARCHAR(255),"
								    + "firstName VARCHAR(255) NOT NULL,"
								    + "lastName VARCHAR(255) NOT NULL,"
								    + "enabled BOOLEAN DEFAULT true)");

		    //Create a Table to store user cookies
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS Cookies("
								    + "cookieID VARCHAR(255) NOT NULL,"
								    + "userID VARCHAR(255) NOT NULL,"
								    + "timestamp DATETIME NOT NULL,"
								    + "FOREIGN KEY (userID) REFERENCES User(userID))");

		    //Create a table to store Permission groups
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS Permission("
								    + "permissionID VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + "permissionName VARCHAR(255) NOT NULL,"
								    + "discription TEXT)");

		    //Create a Table to store Project Information
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS Project("
								    + "projectID VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + "projectName VARCHAR(255) NOT NULL,"
								    + "discription TEXT)");

		    //Create User Permission Table
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS UserPermission("
								    + "permissionID VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + "userID VARCHAR(255) NOT NULL,"
								    + "FOREIGN KEY (permissionID) REFERENCES Permission(permissionID),"
								    + "FOREIGN KEY (userID) REFERENCES User(userID))");

		    //Create User Permission Table
		    statement.executeUpdate("CREATE TABLE IF NOT EXISTS ProjectPermission("
								    + "permissionID VARCHAR(255) NOT NULL PRIMARY KEY,"
								    + "projectID VARCHAR(255) NOT NULL,"
								    + "FOREIGN KEY (permissionID) REFERENCES Permission(permissionID),"
								    + "FOREIGN KEY (projectID) REFERENCES Project(projectID))");

		    //Check that an admin exists in the table

			if(!userAccess.GetUserExisits("admin"))
				{
				System.out.println("\n --- Cannot find administrator account, creating it now . . .");

				boolean created = userAccess.CreateUser(new User("admin","admin","","admin","admin","sudo", "su",true));

				System.out.println("Admin Creation Success: " + created + "\n");
				}

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

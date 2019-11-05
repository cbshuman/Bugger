package bugger.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataAccess
	{
	//Will change this so it can be set somewhere else later
	public static final int salt = 365;

	//Will also make these customizable later
	public static final String databaseUserName = "bugger";
	public static final String databasePassword = "letmein2c01_";
	public static final String databaseConnection = "jdbc:mysql://localhost/bugger?user="+databaseUserName+"&password="+databasePassword+"&serverTimezone=UTC&useSSL=false";

	//Makes sure that the database is set up correctly. If any tables are missing, or
	//there is not an admin user/admin permission group, then it will create them
	public static void ValidateDatabase()
		{
		try
			{
			Connection connect = DriverManager.getConnection(databaseConnection);
			Statement statement = connect.createStatement();

			//Create user table
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS ServerData("
									+ "serverID VARCHAR(255) NOT NULL,"
									+ "lastUserID int NOT NULL,"
									+ "lastCookieID int NOT NULL,"
									+ "lastPermID int NOT NULL,"
									+ "lastProjectID int NOT NULL,"
									+ "lastBugID int NOT NULL)");

			SetUpServerSettings(connect);

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
									+ "cookieID VARCHAR(255) NOT NULL PRIMARY KEY UNIQUE,"
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

			//Create the admin user/admin permission; Make sure that the admin account is in the admin permission group
			if(PermissionData.GetByName("admin") == null)
				{
				System.out.println("admin permission not found -- Creating new one. . .");
				PermissionData.CreateNewPermission("admin","Admin is a security group that is used to adminster the server. You can use it to add new users and create security groups.");
				}

			/*
			if(!UserData.CheckForUserByUsername("admin"))
				{
				System.out.println("admin account not found -- Creating new one. . .");
				UserData.CreateNewUser("admin", "admin@bugger.admin", "admin", "admin", "Sudo", "Su");
				}

			UserData.AddUserPermission("admin","admin");
			*/

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage()); 
        	}
		}

	private static void SetUpServerSettings(Connection connect)
		{
		try
			{
			Statement statement = connect.createStatement();

			ResultSet result = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM ServerData" );
			result.next();

			if(result.getInt("rowcount") == 0)
				{
				statement.executeUpdate("INSERT INTO ServerData(serverID,lastUserID,lastCookieID,lastPermID,lastProjectID,lastBugID)" +
										"VALUES ('bugger',0,0,0,0,0)");
				}
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage()); 
	    	}
		}

	public static int GetLastID(String columnName)
		{
		int returnValue = 0;
		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();

			ResultSet result = statement.executeQuery("SELECT " + columnName + " FROM ServerData");
			result.next();
			returnValue = result.getInt(columnName);
			result.close();

			statement.executeUpdate("UPDATE ServerData SET " + columnName + " = " + (returnValue+1) + " WHERE serverID = 'bugger'");

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Problem looking up Last ID: " + e.getMessage()); 
        	}
		return(returnValue);
		}

	//End of Data Access
	}

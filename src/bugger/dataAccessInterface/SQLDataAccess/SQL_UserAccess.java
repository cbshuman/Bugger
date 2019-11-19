package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.dao.IuserAccess;
import bugger.dataModel.serverModel.Password;
import bugger.dataModel.serverModel.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL_UserAccess implements IuserAccess
    {
	public final String col_userID = "userID";
	public final String col_username = "username";
	public final String col_email = "email";
	public final String col_hashedPassword = "password";
	public final String col_alias = "alias";
	public final String col_firstName = "firstName";
	public final String col_lastName = "lastName";
	public final String col_enabled = "enabled";

	@Override
	public User[] GetAllUsers()
		{
		return new User[0];
		}

	@Override
    public boolean CreateUser(User targetUser)
        {
        boolean returnValue = false;
        
        if(!GetUserExisits(targetUser.username))
            {
            returnValue = InsertUserIntoTable(targetUser);
            }

        return(returnValue);
        }

	@Override
	public User GetUserByParameter(String query, String parameter)
		{
		//The user that will be returned, may return null if not found
		User returnValue = null;

		if(query == null || parameter == null)
			{
			return null;
			}

		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM User WHERE " + parameter + " = '"+ query +"'" );

			//Check that we have a result
			if(result.next())
				{
				String userID = result.getString(User.param_userID);
				String username = result.getString(User.param_username);
				String email = result.getString(User.param_email);
				String hashedPassword = result.getString(User.param_hashedPassword);
				String alias = result.getString(User.param_alias);
				String firstName = result.getString(User.param_firstName);
				String lastName = result.getString(User.param_lastName);
				boolean enabled = result.getBoolean(User.param_enabled);

				if(username != null && email != null && hashedPassword != null && firstName != null && lastName != null)
					{
					Password password = new Password(hashedPassword,true);
					returnValue = new User(userID,username,email,password,alias,firstName,lastName,enabled);
					}
				}
			//user.SetPermissions(DataProxy.GetPermissionList(user.username));

			//Close our connection to the database
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Exception when looking for user: " + e.getMessage());
			e.printStackTrace();
			}

		return(returnValue);
		}

	private boolean InsertUserIntoTable(User targetUser)
        {
        try
            {
            Connection connect = SQL_DataAccess.GetDatabaseConnection();

		    Statement statement = connect.createStatement();
		    statement.executeUpdate("INSERT INTO User("
									+ User.param_userID + ","
									+ User.param_username + ","
									+ User.param_email + ","
									+ User.param_hashedPassword + ","
									+ User.param_alias + ","
									+ User.param_firstName + ","
									+ User.param_lastName
									+ ") VALUES ('"
								    + targetUser.userID + "','"
								    + targetUser.username + "','"
								    + targetUser.email + "','"
								    + targetUser.password.GetHashedPassword() + "','"
								    + targetUser.alias + "','"
								    + targetUser.firstName + "','"
								    + targetUser.lastName + "')");
            
            connect.close();
            }
        catch(Exception e)
            {
			System.out.println("Error inserting user into the table, error: " + e.getMessage());
			e.printStackTrace();

            return(false);
            }

        return(true);
        }

	public User GetUserProfile()
        {
        return(null);
        }

    public boolean DeleteUser()
        {
        boolean returnValue = false;

        return(returnValue);
        }

    public boolean GetUserExisits(String username)
        {
        boolean returnValue = false;

		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM User WHERE USERNAME = '" + username + "'" );

			//Check that we have a result - if we do, then we have a result
			returnValue = result.next();
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find user! Exception: " + e.getMessage());
			e.printStackTrace();
			}

        return(returnValue);
        }
    }

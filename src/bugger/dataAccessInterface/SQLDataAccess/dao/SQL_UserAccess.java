package bugger.dataAccessInterface.SQLDataAccess.dao;

import bugger.dataAccessInterface.SQLDataAccess.SQL_DataAccess;
import bugger.dataAccessInterface.dao.IuserAccess;
import bugger.dataModel.serverModel.Password;
import bugger.dataModel.serverModel.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL_UserAccess extends SQL_DAO<User> implements IuserAccess
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
        
        if(!GetUserExistsByUsername(targetUser.username))
            {
            returnValue = InsertUserIntoTable(targetUser);
            }

        return(returnValue);
        }

	@Override
	public User GetByParameter(String query, String parameter)
		{
		return(GetByParameter(query,parameter, SQL_DataAccess.table_user));
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

        //TODO : Figure out if we're going to remove users or just disable them

        return(returnValue);
        }

    public boolean GetUserExistsByUsername(String username)
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

	@Override
	public boolean GetUserExistsByID(String userID)
		{
		boolean returnValue = false;

		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM "+ SQL_DataAccess.table_user +" WHERE " + User.param_userID + " = '" + userID + "'" );

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

	public boolean CheckForValidID(String id)
		{
		boolean returnValue = false;
		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM User WHERE " + User.param_userID +  " = '" + id + "'" );

			//Check that we have a result - if we do, then the id is taken
			returnValue = !result.next();
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find user! Exception: " + e.getMessage());
			e.printStackTrace();
			}

		return (returnValue);
		}

	@Override
	protected User ParseSQLDataSet(ResultSet resultSet) throws SQLException
		{
		User returnValue = null;

		//Check that we have a result
		if(resultSet.next())
			{
			String userID = resultSet.getString(User.param_userID);
			String username = resultSet.getString(User.param_username);
			String email = resultSet.getString(User.param_email);
			String hashedPassword = resultSet.getString(User.param_hashedPassword);
			String alias = resultSet.getString(User.param_alias);
			String firstName = resultSet.getString(User.param_firstName);
			String lastName = resultSet.getString(User.param_lastName);
			boolean enabled = resultSet.getBoolean(User.param_enabled);

			if(username != null && email != null && hashedPassword != null && firstName != null && lastName != null)
				{
				Password password = new Password(hashedPassword,true);
				returnValue = new User(userID,username,email,password,alias,firstName,lastName,enabled);
				}
			}
		return(returnValue);
		}
	}

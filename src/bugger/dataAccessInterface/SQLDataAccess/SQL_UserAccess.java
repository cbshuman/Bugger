package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.IuserAccess;
import bugger.dataModel.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL_UserAccess implements IuserAccess
    {

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
			Connection connect = SQLDataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM User WHERE " + parameter + " = '"+ query +"'" );

			//Check that we have a result
			if(result.next())
				{
				String userID = result.getString("userID");
				String username = result.getString("username");
				String email = result.getString("email");
				String password = result.getString("password");
				String alias = result.getString("alias");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				boolean enabled = result.getBoolean("enabled");

				if(username != null && email != null && password != null && firstName != null && lastName != null)
					{
					returnValue = new User(userID,username,email,password,alias,firstName,lastName,enabled);
					}
				}
			else
				{
				System.out.println("Cannot find user with " + parameter + " equaling: " + query);
				}
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
            Connection connect = SQLDataAccess.GetDatabaseConnection();

		    Statement statement = connect.createStatement();
		    statement.executeUpdate("INSERT INTO User(userID,username,email,password,alias,firstName,lastName) VALUES ('"
								    + targetUser.userID + "','"
								    + targetUser.username + "','"
								    + targetUser.email + "','"
								    + targetUser.password + "','"
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
			Connection connect = SQLDataAccess.GetDatabaseConnection();
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

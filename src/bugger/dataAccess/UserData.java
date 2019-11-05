package bugger.dataAccess;

/*
public class UserData
	{
	//Creates a new entry into the database
	public static User CreateNewUser(String username, String email, String password, String alias, String firstName, String lastName)
		{
		if(username == null || email == null || password == null || firstName == null || lastName == null)
			{
			return null;
			}

		String userID = User.GenerateUserID(firstName,lastName);
		String hashedPassword = User.HashPassword(password);

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO User(userID,username,email,password,alias,firstName,lastName) VALUES ('"
									+ userID + "','"
									+ username + "','"
									+ email + "','"
									+ hashedPassword + "','"
									+ alias + "','"
									+ firstName + "','"
									+ lastName + "')");
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage()); 
        	}

		return(new User(userID,username,email,hashedPassword,alias,firstName,lastName,true));
		}

	//Gets a new user, DOES NOT get the user's permissions~
	public static User GetUserByParameter(String query, String parameter)
		{
		User returnValue = null;

		if(query == null || parameter == null)
			{
			return null;
			}

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM User WHERE " + parameter + " = '"+ query +"'" );

			result.next();

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
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find user! Exception: " + e.getMessage()); 
        	}

		return(returnValue);
		}

	//Returns all users in the database
	public static User[] GetAllUsers()
		{
		ArrayList<User> userList = new ArrayList<User>();

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();
			
			//Get the permissions
			ResultSet result = statement.executeQuery("SELECT * FROM User" );

			while(result.next())
				{
				String userID = result.getString("userID");
				String username = result.getString("username");
				String email = result.getString("email");
				String password = result.getString("password");
				String alias = result.getString("alias");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				boolean enabled = result.getBoolean("enabled");

				userList.add(new User(userID,username,email,password,alias,firstName,lastName,enabled));
				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage()); 
        	}

		return(userList.toArray(new User[userList.size()]));
		}

	public static boolean CheckForUserByUsername(String username)
		{
		boolean returnValue = false;

		if(username == null)
			{
			return false;
			}

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM User WHERE username = '"+ username +"'" );

			if(result.next())
				{
				returnValue = true;
				}
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find user! Exception: " + e.getMessage()); 
	    	}

		return(returnValue);
		}


	public static boolean AddUserPermission(String username,String permissionName)
		{
		boolean returnValue = false;
		//Load the user and permission
		User targetUser = GetUserByParameter(username, "username");
		Permission targetPermission = PermissionData.GetByName(permissionName);

		if(targetUser != null || targetPermission != null)
			{
			try
				{
				Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
				Statement statement = connect.createStatement();
				statement.executeUpdate("INSERT IGNORE INTO UserPermission(permissionID,userID) VALUES ('"
										+ targetPermission.permissionID + "','"
										+ targetUser.userID + "')");
				connect.close();

				returnValue = true;
				}
			catch (Exception e)
				{
				System.out.println(e.getMessage()); 
				}
			}

		return(returnValue);
		}

	public static Permission[] GetUserPermissions(String username)
		{
		ArrayList<Permission> permissionList = new ArrayList<Permission>();
		boolean targetUser = CheckForUserByUsername(username);

		if(targetUser)
			{
			try
				{
				Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
				Statement statement = connect.createStatement();

				//Get USERID
				ResultSet result = statement.executeQuery("SELECT USERID FROM User WHERE USERNAME = '" + username + "'");
				result.next();
				String userID = result.getString("USERID");
				
				//Get the permissions
				result = statement.executeQuery("SELECT * FROM  UserPermission JOIN Permission on UserPermission.permissionID = Permission.permissionID WHERE userID ='"+ userID +"'" );

				while(result.next())
					{
					String permissionID = result.getString("permissionID");
					String permissionName = result.getString("permissionName");
					String discription = result.getString("discription");
					permissionList.add(new Permission(permissionID, permissionName,discription));
					}

				connect.close();
				}
			catch (Exception e)
				{
				System.out.println(e.getMessage()); 
		    	}
			}

		return(permissionList.toArray(new Permission[permissionList.size()]));
		}

	public static boolean ValidateUserID()
		{
		boolean returnValue = false;

		return(returnValue);
		}
	}
*/
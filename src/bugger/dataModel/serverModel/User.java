package bugger.dataModel.serverModel;

import bugger.dataAccess.DataAccess;
import bugger.dataModel.DataModel;

public class User extends DataModel
	{
	public String userID;
	public String username;
	public String email;
	public Password password;
	public String alias;
	public String firstName;
	public String lastName;
	public boolean enabled;
	public Permission[] permissions;

	//Creates a new user with an unhashed password
	public User(String userID, String username, String email, String password, String alias, String firstName, String lastName, boolean enabled)
		{
		if(username == null || email == null || password == null || firstName == null || lastName == null)
			{
			return;
			}
		this.userID = userID;
		this.username = username;
		this.email =  email;
		this.password = new Password(password);
		this.alias = alias;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;

		GetPermissions();
		}

	//Creates a new user with a hashed password
	public User(String userID, String username, String email, Password password, String alias, String firstName, String lastName, boolean enabled)
		{
		if(username == null || email == null || password == null || firstName == null || lastName == null)
			{
			return;
			}
		this.userID = userID;
		this.username = username;
		this.email =  email;
		this.password = password;
		this.alias = alias;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;

		permissions = new Permission[0];
		}

	public void GetPermissions()
		{
		//permissions = UserData.GetUserPermissions(username);
		}

	public boolean CompareUnhashedPassword(String unhashedPassword)
		{
		return(password.CompareUnhashedPassword(unhashedPassword));
		}

	public boolean HasPermission(String permissionName)
		{
		boolean returnValue = false;

		for(int i = 0; i < permissions.length; i++)
			{
			System.out.println(" -> Permission: " + permissions[i].permissionName);
			if(permissions[i].permissionName.equals(permissionName))
				{
				returnValue = true;
				break;
				}
			}

		return(returnValue);
		}

	//Hashes out a userID
	public static String GenerateUserID(String firstName,String lastName)
		{
		StringBuilder returnValue = new StringBuilder();
		int number = DataAccess.GetLastID("lastUserID");

		returnValue.append(firstName.charAt(0));
		returnValue.append(lastName.charAt(0));
		returnValue.append("_");
		returnValue.append(number);

		while(returnValue.length() < 35)
			{
			returnValue.append(0);
			}

		return(returnValue.toString());
		}
	}

package bugger.dataModel;

import bugger.dataAccess.DataAccess;

import java.util.Random;

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

	public void GetPermissions()
		{
		//permissions = UserData.GetUserPermissions(username);
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

	//Hashes a password
	public static String HashPassword(String password)
		{
		StringBuilder returnValue = new StringBuilder();
		long seed = (DataAccess.salt * password.charAt(0) + password.charAt(password.length()-1) );

		Random gen = new Random();
		gen.setSeed(seed);

		for(int i = 0; i < 255; i++)
			{
			int indexAlpNum = (int)(gen.nextFloat() * (alpNum.length() - 1));
			returnValue.append(alpNum.charAt(indexAlpNum));
			}

		return(returnValue.toString());
		}
	}

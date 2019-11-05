package bugger.dataModel.clientModel;

import bugger.dataModel.serverModel.User;

public class ClientUser
	{
	public String userID;
	public String username;
	public String email;
	public String alias;
	public String firstName;
	public String lastName;
	public boolean enabled;
	public String[] permissions;

	public ClientUser(User userIn)
		{
		username = userIn.username;
		email = userIn.email;
		alias = userIn.alias;
		firstName = userIn.firstName;
		lastName = userIn.lastName;
		enabled = userIn.enabled;

		permissions = new String[userIn.permissions.length];

		for(int i= 0; i < permissions.length; i++)
			{
			permissions[i] = userIn.permissions[i].permissionName;
			}
		}

	}

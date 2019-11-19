package bugger.dataModel.serverModel;

import bugger.dataModel.DataModel;

public class Permission extends DataModel
	{
	public static final String param_permissionID = "permissionID";
	public static final String param_permissionName = "permissionName";
	public static final String param_discription = "discription";

	public String permissionID;
	public String permissionName;
	public String discription;

	public Permission(String permissionName, String discription)
		{
		this.permissionName = permissionName;
		this.discription = discription;
		}

	public Permission(String permissionID, String permissionName, String discription)
		{
		this.permissionID = permissionID;
		this.permissionName = permissionName;
		this.discription = discription;
		}

	//Hashes out a permisionID
	public static String GeneratePermissionID(String name)
		{
		StringBuilder returnValue = new StringBuilder();
		int number = 0;

		returnValue.append(name.charAt(0));

		returnValue.append("_");
		returnValue.append(number);

		while(returnValue.length() < 35)
			{
			returnValue.append(0);
			}

		return(returnValue.toString());
		}
	}

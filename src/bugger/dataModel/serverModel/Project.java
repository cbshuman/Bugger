package bugger.dataModel.serverModel;

public class Project
	{
	public final static String param_projectID = "projectID";
	public final static String param_projectName = "projectName";
	public final static String param_description = "description";

	public String projectID;
	public String projectName;
	public String discription;
	public Permission[] permissions;
		
	public Project(String projectID, String projectName, String discription)
		{
		this.projectID = projectID;
		this.projectName = projectName;
		this.discription = discription;
		}

	public static String GenerateProjecID(String projectName)
		{
		StringBuilder returnValue = new StringBuilder();
		int number = 0;

		returnValue.append(projectName.charAt(0));
		returnValue.append("_");
		returnValue.append(number);

		while(returnValue.length() < 35)
			{
			returnValue.append(0);
			}

		return(returnValue.toString());
		}

	//Yea. . . likely going to deprecate this
	public void GetPermissions()
		{
		permissions = new Permission[0];
		}
	}

package bugger.dataModel.serverModel;

import bugger.dataModel.DataModel;

public class Project extends DataModel
	{
	public final static String param_projectID = "projectID";
	public final static String param_projectName = "projectName";
	public final static String param_description = "description";
	public final static String param_defaultAssign = "defaultContact";

	public String projectID;
	public String projectName;
	public String discription;
	public Permission[] permissions;
	public String defaultAssign;

	public Project(String projectName, String discription,String defaultAssign)
		{
		this.projectID = GenerateID(projectName,discription,0);
		this.projectName = projectName;
		this.discription = discription;
		this.defaultAssign = defaultAssign;
		}

	public Project(String projectID, String projectName, String discription,String defaultAssign)
		{
		this.projectID = projectID;
		this.projectName = projectName;
		this.discription = discription;
		this.defaultAssign = defaultAssign;
		}

	//Yea. . . likely going to deprecate this
	public void GetPermissions()
		{
		permissions = new Permission[0];
		}
	}

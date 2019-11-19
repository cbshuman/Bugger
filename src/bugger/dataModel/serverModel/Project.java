package bugger.dataModel.serverModel;

import bugger.dataAccess.ProjectData;

public class Project
	{
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

	public void GetPermissions()
		{
		permissions = ProjectData.GetProjectPermissions(projectID);
		}
	}

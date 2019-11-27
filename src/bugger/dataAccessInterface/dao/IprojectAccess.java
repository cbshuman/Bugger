package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.Project;

import java.util.List;

public interface IprojectAccess extends  Idao<Project>
	{
	List<Project> GetAllProjects(int min, int max);
	boolean CreateProject(String projectName, String projectDisc, String[] permissions, String defaultAssignee);
	}

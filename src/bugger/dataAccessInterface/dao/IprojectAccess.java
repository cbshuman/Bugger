package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.Project;

import java.util.List;

public interface IprojectAccess extends  Idao<Project>
	{
	List<Project> GetAllProjects(int min, int max);
	boolean CreateProject(Project targetProject);
	Project GetProjectByParameter(String query, String parameter);
	}

package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.dao.IprojectAccess;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.Project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQL_ProjectAccess extends SQL_DAO<Project> implements IprojectAccess
	{
	@Override
	public boolean CheckForValidID(String id)
		{
		return false;
		}

	public boolean  CreateProject(String projectName, String projectDisc, String[] permissions, String defaultAssignee)
		{
		boolean returnValue = false;


		return(returnValue);
		}

	@Override
	protected Project ParseSQLDataSet(ResultSet resultSet) throws SQLException
		{
		Project returnValue = null;

		//Check that we have a result
		if(resultSet.next())
			{
			String projectID = resultSet.getString(Project.param_projectID);
			String description = resultSet.getString(Project.param_description);
			String projectName = resultSet.getString(Project.param_projectName);

			if(projectID != null && description != null && projectName != null)
				{
				returnValue = new Project(projectID,description,projectName);
				}
			}
		return(returnValue);
		}

	@Override
	public Project GetByParameter(String query, String parameter)
		{
		return GetByParameter(query,parameter,SQL_DataAccess.table_project);
		}

	//Gets a project based on a parameter
	public static Project GetProjectByParameter(String query, String parameter)
		{
		Project returnValue = null;

		if(query == null || parameter == null)
			{
			return null;
			}

		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Project WHERE " + parameter + " = '"+ query +"'" );

			result.next();

			String projectID = result.getString("projectID");
			String projectName = result.getString("projectName");
			String discription = result.getString("discription");

			if(projectID != null && projectName != null && discription != null)
				{
				returnValue = new Project(projectID,projectName,discription);
				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find user! Exception: " + e.getMessage());
			}

		return(returnValue);
		}

	public static boolean AddProjectPermission(String projectName,String permisionID)
		{
		boolean returnValue = false;
		//Load the user and permission
		Project targetProject = GetProjectByParameter(projectName, "projectID");
		Permission targetPermission = null;

		if(targetProject != null || targetPermission != null)
			{
			try
				{
				Connection connect = SQL_DataAccess.GetDatabaseConnection();
				Statement statement = connect.createStatement();
				statement.executeUpdate("INSERT IGNORE INTO ProjectPermission(permissionID,projectID) VALUES ('"
						+ targetPermission.permissionID + "','"
						+ targetProject.projectID + "')");
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

	/*
	Returns all the projects in Bugger. . .
	This is likely a big operation, so only the Admins should have access
	to it
	*/
	public List<Project> GetAllProjects(int min, int max)
		{
		ArrayList<Project> projectList = new ArrayList<Project>();

		String query = "SELECT * FROM " + SQL_DataAccess.table_project;

		//For now this is kinda future-proofing . . .
		// but limit the number of projects that can be called on.
		if(min >= 0 && max > 0)
			{
			query += " Limit " + min + "," + max;
			}

		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			//Get the Projects . . . all of them
			ResultSet result = statement.executeQuery(query);

			//Parse through the results and add them to the list
			while(result.next())
				{
				String projectID = result.getString("projectID");
				String projectName = result.getString("projectName");
				String discription = result.getString("discription");

				Project newProject = new Project(projectID, projectName,discription);
				//TODO: Change this, like for reals. It doesn't really jive with the way we've been doing this
				newProject.GetPermissions();

				//Add the new project to the list
				projectList.add(newProject);
				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			}

		return(projectList);
		}
	}

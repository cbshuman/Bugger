package bugger.dataAccess;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectData
	{
	//Creates a new entry into the database
	public static Project CreateNewProject(String projectName, String projectDiscription)
		{
		System.out.println(" -> Creating Project with SQL '" + projectName + "' / '" + projectDiscription + "'");
		if(projectName == null || projectDiscription == null)
			{
			return null;
			}

		String projectID = Project.GenerateProjecID(projectName);

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();
		
			//Start by creating the project
			statement.executeUpdate("INSERT INTO Project(projectID,projectName,discription) VALUES ('"
									+ projectID + "','"
									+ projectName + "','"
									+ projectDiscription + "')");
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage()); 
        	}

		return(new Project(projectID,projectName,projectDiscription));
		}

	public static boolean CheckForProjetByID(String projectID)
		{
		boolean returnValue = false;

		if(projectID == null)
			{
			return false;
			}

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Project WHERE projectID = '"+ projectID +"'" );

			if(result.next())
				{
				returnValue = true;
				}
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find user! Exception: " + e.getMessage()); 
	    	}

		return(returnValue);
		}

	public static Permission[] GetProjectPermissions(String projectID)
		{
		ArrayList<Permission> permissionList = new ArrayList<Permission>();
		boolean targetProject = CheckForProjetByID(projectID);

		if(targetProject)
			{
			try
				{
				Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
				Statement statement = connect.createStatement();

				//Get the permissions
				ResultSet result = statement.executeQuery("SELECT * FROM Permission JOIN ProjectPermission on ProjectPermission.permissionID = Permission.permissionID WHERE projectID ='"+ projectID +"'" );

				while(result.next())
					{
					String permissionID = result.getString("permissionID");
					String permissionName = result.getString("permissionName");
					String discription = result.getString("discription");
					permissionList.add(new Permission(permissionID, permissionName,discription));
					}

				connect.close();
				}
			catch (Exception e)
				{
				System.out.println(e.getMessage()); 
		    	}
			}

		return(permissionList.toArray(new Permission[permissionList.size()]));
		}

	//Gets a new user, DOES NOT get the user's permissions~
	public static Project GetProjectByParameter(String query, String parameter)
		{
		Project returnValue = null;

		if(query == null || parameter == null)
			{
			return null;
			}

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
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
		Permission targetPermission = DataProxy.GetPermission(permisionID,"permissionName");

		if(targetProject != null || targetPermission != null)
			{
			try
				{
				Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
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

	public static Project[] GetProjects()
		{
		ArrayList<Project> projectList = new ArrayList<Project>();

		try
			{
			Connection connect = DriverManager.getConnection(DataAccess.databaseConnection);
			Statement statement = connect.createStatement();
			
			//Get the permissions
			ResultSet result = statement.executeQuery("SELECT * FROM Project" );

			while(result.next())
				{
				String projectID = result.getString("projectID");
				String projectName = result.getString("projectName");
				String discription = result.getString("discription");
				Project newProject = new Project(projectID, projectName,discription);
				newProject.GetPermissions();
				projectList.add(newProject);
				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage()); 
        	}

		return(projectList.toArray(new Project[projectList.size()]));
		}
	}

package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.dao.IpermissionAccess;
import bugger.dataModel.serverModel.Permission;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQL_PermissionAccess implements IpermissionAccess
	{
	@Override
	public boolean CreatePermission(Permission permission)
		{
		boolean returnValue = false;
		if(!GetPermissionExists(permission.permissionID))
			{
			returnValue = InsertPermissionIntoTable(permission);
			}
		return (returnValue);
		}

	@Override
	public boolean GetPermissionExists(String permissionID)
		{
		return (GetPermissionByParameter(permissionID,"permissionID") != null);
		}

	public boolean InsertPermissionIntoTable(Permission permission)
		{
		boolean returnValue = false;
		try
			{
			Connection connect = SQL_Connector.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO Permission(permissionID,permissionName,discription) VALUES ('"
					+ permission.permissionID + "','"
					+ permission.permissionName + "','"
					+ permission.discription + "')");
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			}

		return(returnValue);
		}

	@Override
	public Permission GetPermissionByParameter(String parameterValue, String parameter)
		{
		Permission returnValue = null;

		if(parameterValue == null)
			{
			return null;
			}

		try
			{
			Connection connect = SQL_Connector.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Permission WHERE "+ parameter +" = '"+ parameterValue +"'" );

			//Check that we have a result
			if(result.next())
				{
				//If we find the result, get it put together
				String permissionName = result.getString("permissionName");
				String discription = result.getString("discription");

				if(parameterValue != null && permissionName != null && discription != null)
					{
					returnValue = new Permission(parameterValue,permissionName,discription);
					}
				}
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			}

		return(returnValue);
		}

	@Override
	public List<Permission> GetPermissionList(String userID)
		{
		List<Permission> returnValue = new ArrayList<>();

		try
			{
			Connection connect = SQL_Connector.GetDatabaseConnection();
			Statement statement = connect.createStatement();

			//Get the permissions
			ResultSet result = statement.executeQuery("SELECT * FROM UserPermission WHERE userID = " + userID );

			while(result.next())
				{
				String permissionID = result.getString("permissionID");

				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			}

		return returnValue;
		}
	}

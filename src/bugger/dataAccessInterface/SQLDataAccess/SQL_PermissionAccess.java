package bugger.dataAccessInterface.SQLDataAccess;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataAccessInterface.dao.IpermissionAccess;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQL_PermissionAccess extends SQL_DAO<Permission> implements IpermissionAccess
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
		return (GetByParameter(permissionID,"permissionID") != null);
		}

	@Override
	public boolean AddPermissionToUser(String permissionID,String userID)
		{
		boolean returnValue = false;

		//System.out.println(userID + "/" + permissionID);
		//System.out.println("Valid UserID and PermissionID: " + GetPermissionExists(permissionID) + "<- | ->" + DataProxy.GetUserExistsByID(userID));

		if(GetPermissionExists(permissionID) && DataProxy.GetUserExistsByID(userID))
			{
			try
				{
				Connection connect = SQL_Connector.GetDatabaseConnection();
				Statement statement = connect.createStatement();

				statement.executeUpdate("INSERT INTO " + SQL_DataAccess.table_user_permission  + "("
						+ Permission.param_permissionID + ","
						+ User.param_userID
						+ ") VALUES ('"
						+ permissionID + "','"
						+ userID +  "')");

				connect.close();

				returnValue = true;
				}
			catch (Exception e)
				{
				System.out.println(e.getMessage());
				}

			}

		return (returnValue);
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
	public Permission GetByParameter(String query, String parameter)
		{
		return(GetByParameter(query,parameter,SQL_DataAccess.table_permission));
		}

	@Override
	public List<Permission> GetPermissions(String userID)
		{
		List<Permission> returnValue = new ArrayList<>();

		try
			{
			Connection connect = SQL_Connector.GetDatabaseConnection();
			Statement statement = connect.createStatement();

			//Get the permissions
			ResultSet result = statement.executeQuery("SELECT * FROM " + SQL_DataAccess.table_user_permission
														+ " LEFT JOIN " + SQL_DataAccess.table_permission
														+ " ON " + SQL_DataAccess.table_user_permission + "." + Permission.param_permissionID
														+ " = " + SQL_DataAccess.table_permission + "." + Permission.param_permissionID
														+ " WHERE " + User.param_userID + " = '" + userID + "'");

			while(result.next())
				{
				String permissionID = result.getString(Permission.param_permissionID);
				String permissionName = result.getString(Permission.param_permissionName);
				String discription = result.getString(Permission.param_discription);

				if(permissionID != null && permissionName != null && discription != null)
					{
					returnValue.add(new Permission(permissionID,permissionName,discription));
					}

				}

			connect.close();
			}
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			e.printStackTrace();
			}

		return returnValue;
		}

	public boolean CheckForValidID(String id)
		{
		boolean returnValue = false;
		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM UserPermission WHERE " + Permission.param_permissionID +  " = '" + id + "'" );

			//Check that we have a result - if we do, then the id is taken
			returnValue = !result.next();
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Cannot find user! Exception: " + e.getMessage());
			e.printStackTrace();
			}

		return (returnValue);
		}

	@Override
	protected Permission ParseSQLDataSet(ResultSet resultSet) throws SQLException
		{
		Permission returnValue = null;

		//Check that we have a result
		if(resultSet.next())
			{
			//If we find the result, get it put together
			String permissionID = resultSet.getString(Permission.param_permissionID);
			String permissionName = resultSet.getString(Permission.param_permissionName);
			String discription = resultSet.getString(Permission.param_discription);

			if(permissionID != null && permissionName != null && discription != null)
				{
				returnValue = new Permission(permissionID,permissionName,discription);
				}
			}

		return returnValue;
		}
	}

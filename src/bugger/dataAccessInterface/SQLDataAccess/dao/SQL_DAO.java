package bugger.dataAccessInterface.SQLDataAccess.dao;

import bugger.dataAccessInterface.SQLDataAccess.SQL_DataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQL_DAO<T>
	{
	public boolean CheckForValidID(String id)
		{
		return false;
		}

	T GetByParameter(String query, String parameter, String table)
		{
		//The user that will be returned, may return null if not found
		T returnValue = null;

		if(query == null || parameter == null)
			{
			return null;
			}

		try
			{
			Connection connect = SQL_DataAccess.GetDatabaseConnection();
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM " + table + " WHERE " + parameter + " = '"+ query +"'" );

			//Get the return value from the set
			returnValue = ParseSQLDataSet(result);

			//Close our connection to the database
			connect.close();
			}
		catch (Exception e)
			{
			System.out.println("Exception when looking for user: " + e.getMessage());
			e.printStackTrace();
			}

		return(returnValue);
		}

	protected abstract T ParseSQLDataSet(ResultSet resultSet) throws SQLException;
	}

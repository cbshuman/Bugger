package bugger.dataAccessInterface.SQLDataAccess;

import java.sql.*;

public class SQL_Connector
    {
	public static final String databaseUserName = "bugger";
	public static final String databasePassword = "letmein2c01_";
	public static final String databaseConnection = "jdbc:mysql://localhost/bugger?user="+databaseUserName+"&password="+databasePassword+"&serverTimezone=UTC&useSSL=false";

    public static Connection GetDatabaseConnection() throws SQLException
        {
        return(DriverManager.getConnection(databaseConnection));
        }

    public static void WriteToSQL(String sqlStatement)
        {
        try
			{
			Connection connect = GetDatabaseConnection();

			//Statement statement = connect.createStatement().executeUpdate(sqlStatement);

			connect.close();
			}
		catch (SQLException e)
			{
			System.out.println("Error ");
			}

        }

    public static ResultSet ReadFromSQL(String sqlStatement)
        {
		ResultSet result = null;
		try
			{
			Connection connect = GetDatabaseConnection();

			Statement statement = connect.createStatement();
			result = statement.executeQuery(sqlStatement);

			connect.close();
			return (result);
			}
		catch (SQLException e)
			{

			}
		return(result);
        }
    }

package bugger.dataAccess;

public class DataAccess
	{
	//Will change this so it can be set somewhere else later
	public static final int salt = 365;

	//Will also make these customizable later
	public static final String databaseUserName = "bugger";
	public static final String databasePassword = "letmein2c01_";
	public static final String databaseConnection = "jdbc:mysql://localhost/bugger?user="+databaseUserName+"&password="+databasePassword+"&serverTimezone=UTC&useSSL=false";

	//End of Data Access
	}

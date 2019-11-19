package bugger.dataAccessInterface.dao;

public interface Idao<T>
	{
	boolean CheckForValidID(String id);
	T GetByParameter(String query, String parameter);
	}

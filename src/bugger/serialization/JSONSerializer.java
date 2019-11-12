package bugger.serialization;

import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.User;
import com.google.gson.Gson;

public class JSONSerializer implements ISerializer
	{
	private String SerializeObject(Object objectIn)
		{
		try
			{
			return (new Gson().toJson(objectIn));
			}
		catch (Exception e)
			{
			e.printStackTrace();
			return (null);
			}
		}

	@Override
	public String SerializeUser(User userIn)
		{
		return SerializeObject(userIn);
		}

	@Override
	public String SerializeCookie(Cookie cookieIn)
		{
		return SerializeObject(cookieIn);
		}
	}

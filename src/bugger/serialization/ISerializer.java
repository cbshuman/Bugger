package bugger.serialization;

import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.User;

public interface ISerializer
	{
	String SerializeUser(User userIn);
	String SerializeCookie(Cookie cookieIn);
	}

package bugger.serialization;

import bugger.dataModel.clientModel.ClientUser;
import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.User;

public interface ISerializer
	{
	String SerializeUser(User userIn);
	String SerializeCookie(Cookie cookieIn);
	String SerializeUser(ClientUser user);
	}

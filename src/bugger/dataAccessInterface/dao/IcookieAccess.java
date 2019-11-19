package bugger.dataAccessInterface.dao;

import bugger.dataModel.serverModel.Cookie;

public interface IcookieAccess extends Idao<Cookie>
	{
	Cookie CreateNewCookie(String userID);
	Cookie[] GetUserCookies(String userID);
	boolean DeleteCookie(String cookieID);
	}

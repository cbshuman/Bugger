package bugger.dataAccessInterface;

import bugger.dataModel.serverModel.Cookie;

public interface IcookieAccess
	{
	Cookie CreateNewCookie(String userID);
	Cookie[] GetUserCookies(String userID);
	String GetUserIDFromCookie(String cookieID);
	boolean VerifyCookie(String cookieID);
	}

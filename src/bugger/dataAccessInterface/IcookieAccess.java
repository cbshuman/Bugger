package bugger.dataAccessInterface;

import bugger.dataModel.Cookie;

public interface IcookieAccess
	{
	Cookie CreateNewCookie(String userID);
	String GetUserIDFromCookie(String cookieID);
	boolean VerifyCookie(String cookieID);
	}

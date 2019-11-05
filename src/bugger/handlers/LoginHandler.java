package bugger.handlers;

import bugger.dataAccess.CookieData;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.Cookie;
import bugger.dataModel.User;
import bugger.utility.Utility;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginHandler implements HttpHandler
	{
	private static String timeFormat = "d, yyyy/M HH:mm:ss";

	public void handle(HttpExchange exchange) throws IOException
		{
		boolean loginSuccess = false;
		String returnMessage = "";
		if(exchange.getRequestMethod().toLowerCase().equals("post"))
			{
			LoginDetails login = new Gson().fromJson(Utility.InputStreamToString(exchange.getRequestBody()), LoginDetails.class);
			
            System.out.println("--  '" + login.username + "'  is attempting to login  --");

			User user = DataProxy.GetUserByParameter(login.username, "username");
			if(user != null)
				{
				loginSuccess = user.password.equals(User.HashPassword(login.password));
				}

			if(loginSuccess == true)
				{
				System.out.println("Guess they get to log in!");
				//Create a cookie for their session
				Cookie cookie = CookieData.CreateNewCookie(user.userID);

				//Add the cookie to the headers
				Date today = new Date();
				Date currentDate = new Date(today.getTime() + (1000 * 60 * 60 * 24));
				DateFormat dateFormat = new SimpleDateFormat(timeFormat);
				
				String timestamp = dateFormat.format(currentDate);
				
				exchange.getResponseHeaders().add("Set-Cookie", "UserID = " + cookie.cookieID + "; Path=/; Max-Age= 86400;");

				returnMessage = new Gson().toJson(new ReturnUser(user));
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_CREATED, returnMessage.length());
				}
			else
				{
				System.out.println("Login was a failure");
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
				returnMessage = "Invalid username/password combination!";
				}
			}

		OutputStream stream = exchange.getResponseBody();
		Utility.WriteStringToStream(stream,returnMessage);			
		stream.close();
		exchange.close();
		}

	private class LoginDetails
		{
		public String username;
		public String password;
		}
	}

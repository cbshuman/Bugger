package bugger.httpshandlers;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.clientModel.ClientUser;
import bugger.dataModel.serverModel.Cookie;
import bugger.dataModel.serverModel.User;
import bugger.utility.Utility;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

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
            System.out.println("Provided (Username:Password) : (" +login.username + ":" + login.password + ")");

			User user = DataProxy.GetUserByParameter(login.username, "username");

			if(user != null)
				{

				loginSuccess = user.CompareUnhashedPassword(login.password);

				System.out.println("Password Comparision: " + loginSuccess);
				//System.out.println(user.password.GetHashedPassword() );
				//System.out.println(Password.HashPassword(login.password) );
				}

			if(loginSuccess == true)
				{
				//Create a cookie for their session
				Cookie cookie = DataProxy.CreateNewCookie(user.userID);
				
				exchange.getResponseHeaders().add("Set-Cookie", "UserID = " + cookie.cookieID + "; Path=/; Max-Age= 86400;");
				exchange.getResponseHeaders().add("Set-Cookie", "UserID = " + cookie.userID + "; Path=/; Max-Age= 86400;");

				returnMessage = new Gson().toJson(new ClientUser(user));
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, returnMessage.length());

				System.out.println("-- Correct Login Credentials -- \n");
				}
			else
				{
				System.out.println("-- Login Failed -- \n");
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

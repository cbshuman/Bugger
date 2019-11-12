package bugger.httpshandlers;

import bugger.command.BuggerCMD;
import bugger.command.BuggerCommand;
import bugger.command.CMD_GetUserByUsername;
import bugger.command.CreateCookie;
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
	static String CookieString = "BuggerCookie";
	private static String timeFormat = "d, yyyy/M HH:mm:ss";

	public void handle(HttpExchange exchange) throws IOException
		{
		boolean loginSuccess = false;
		String returnMessage = "";
		if(exchange.getRequestMethod().toLowerCase().equals("post"))
			{
			LoginDetails login = new Gson().fromJson(Utility.InputStreamToString(exchange.getRequestBody()), LoginDetails.class);
			
            System.out.println(" -- '" + login.username + "'  is attempting to login  --");
            //System.out.println("Provided (Username:Password) : (" +login.username + ":" + login.password + ")");

			BuggerCommand<User> getUserCommand = BuggerCMD.DoCommand(new CMD_GetUserByUsername(login.username));
			User user = getUserCommand.GetReturnValue();

			if(user != null)
				{
				loginSuccess = user.CompareUnhashedPassword(login.password);
				//System.out.println("Password Comparision: " + loginSuccess);
				}

			if(loginSuccess)
				{
				System.out.println(" --> Correct Credentials - Packaging response. . .");

				//Create a cookie for their session
				BuggerCommand<Cookie> cookieCMD = BuggerCMD.DoCommand(new CreateCookie(user.userID));
				Cookie cookie = cookieCMD.GetReturnValue();

				exchange.getResponseHeaders().add("Set-Cookie", CookieString + " = " + cookie.cookieID + ":" + cookie.userID + "; Path=/; Max-Age= 86400;");

				//System.out.println("Set the cookie, now for some json");

				try
					{
					returnMessage = new Gson().toJson(new ClientUser(user));
					}
				catch (Exception e)
					{
					e.printStackTrace();
					}

				//System.out.println("Returning: " + returnMessage);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, returnMessage.length());

				System.out.println("-- Login Success! -- \n");
				}
			else
				{
				System.out.println("-- Login Failed -- \n");
				returnMessage = "{message : Invalid username/password combination!}";

				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, returnMessage.length());
				}
			}

		OutputStream stream = exchange.getResponseBody();
		Utility.WriteStringToStream(stream,returnMessage);			
		stream.close();
		exchange.close();
		}

	private class LoginDetails
		{
		String username;
		String password;
		}
	}

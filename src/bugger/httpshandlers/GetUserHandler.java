package bugger.httpshandlers;

import bugger.dataModel.clientModel.ClientUser;
import bugger.dataModel.serverModel.User;
import bugger.utility.HandlerUtilites;
import bugger.utility.Utility;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;


public class GetUserHandler extends SecureHTTPHandler
	{
	public void handle(HttpExchange exchange) throws IOException
		{
		String returnMessage = "";

		if(exchange.getRequestMethod().toLowerCase().equals("get"))
			{
			System.out.println("\n -- Getting User Info  --");
			System.out.println(" -> Authenticating: ");
			Headers headers = exchange.getRequestHeaders();

			String cookieContents = HandlerUtilites.GetCookieIDFromCookie(headers);

			System.out.print(" -> Cookie # " + cookieContents);

			if(HasValidCookie(headers))
				{
				User user = HandlerUtilites.GetUserFromCookie(cookieContents);
				returnMessage = new Gson().toJson(new ClientUser(user));

				exchange.sendResponseHeaders(HttpURLConnection.HTTP_CREATED, returnMessage.length());
				}
			else
				{
				//remove invalid cookies
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
				returnMessage = "Invalid or expired Cookie!";
				}

			//System.out.println(headers.entrySet());
			}

		OutputStream stream = exchange.getResponseBody();
		Utility.WriteStringToStream(stream,returnMessage);
		stream.close();
		exchange.close();
    	System.out.println(" -- REQUEST COMPLETE -- \n");
		}
	}

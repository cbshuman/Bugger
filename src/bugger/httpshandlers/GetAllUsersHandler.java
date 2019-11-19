package bugger.httpshandlers;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.clientModel.ClientUser;
import bugger.dataModel.serverModel.User;
import bugger.utility.Utility;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class GetAllUsersHandler extends SecureHTTPHandler
	{
	public void handle(HttpExchange exchange) throws IOException
		{
		if(exchange.getRequestMethod().toLowerCase().equals("post") == true)
			{

			}
		else if(exchange.getRequestMethod().toLowerCase().equals("get") == true)
			{
			GetProjects(exchange);
			}
		}

	private void GetProjects(HttpExchange exchange) throws IOException
		{
        System.out.println("\n -- Getting Projects -- ");

		String returnMessage = "";
		Headers headers = exchange.getRequestHeaders();

		System.out.println(" -> Authenticating Cookie: ");
		if(HasValidCookie(headers))
			{
			User[] users = DataProxy.GetAllUsers();
			ClientUser[] jsonResponce = new ClientUser[users.length];

			for(int i = 0; i < users.length; i++)
				{
				jsonResponce[i] = new ClientUser(users[i]);
				}

			returnMessage = new Gson().toJson(jsonResponce);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			}
		else
			{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
			returnMessage = "Invalid or expired Cookie! Try loging in again!";
			}

		OutputStream stream = exchange.getResponseBody();
		Utility.WriteStringToStream(stream,returnMessage);			
		stream.close();
		exchange.close();
    	System.out.println(" -- REQUEST COMPLETE -- \n");
		}
	}

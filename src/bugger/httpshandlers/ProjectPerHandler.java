package bugger.httpshandlers;

import bugger.utility.Utility;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ProjectPerHandler extends SecureHTTPHandler
	{
	public void handle(HttpExchange exchange) throws IOException
		{
		if(exchange.getRequestMethod().toLowerCase().equals("put") == true)
			{
			UpdatePermission(exchange);
			}
		else if(exchange.getRequestMethod().toLowerCase().equals("delete") == true)
			{
			RemovePermission(exchange);
			}
		}

	private void UpdatePermission(HttpExchange exchange) throws IOException
		{
        System.out.println("\n -- Adding Project Permission -- ");

		String returnMessage = "";
		Headers headers = exchange.getRequestHeaders();

		System.out.println(" -> Authenticating Cookie: ");
		if(HasValidCookie(headers))
			{
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

	private void RemovePermission(HttpExchange exchange) throws IOException
		{
        System.out.println("\n -- Remove Project Permission -- ");

		String returnMessage = "";
		Headers headers = exchange.getRequestHeaders();

		System.out.println(" -> Authenticating Cookie: ");
		if(HasValidCookie(headers))
			{
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

	private class ProjectPermission
		{
		String projectName;
		String permission;
		}
	}

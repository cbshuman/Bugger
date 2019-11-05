package bugger.handlers;

import bugger.utility.Utility;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler
	{
	public void handle(HttpExchange exchange) throws IOException
		{
        System.out.print("\n -- Called DEFAULT on: ");
		System.out.println(exchange.getHttpContext().getPath());
        System.out.print("-- Girl look at this body: ");
		System.out.println(Utility.InputStreamToString(exchange.getRequestBody()));
		if(exchange.getRequestMethod().toLowerCase().equals("put") == true)
			{
			}

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		}
	}

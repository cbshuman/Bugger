package bugger.httpshandlers;

import bugger.command.BuggerCMD;
import bugger.command.BuggerCommand;
import bugger.command.CMD_GetUserByUsername;
import bugger.dataModel.serverModel.User;
import bugger.serialization.SerializationInterface;
import bugger.utility.Utility;
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
			Headers headers = exchange.getRequestHeaders();

			if(HasValidCookie(headers))
				{
				System.out.println(" -> Got Valid Cookie --");

				//Get the user
				BuggerCommand<User> userCommand = BuggerCMD.DoCommand(new CMD_GetUserByUsername(GetHandlerUserID()));
				User user = userCommand.GetReturnValue();
				//Return the user details
				returnMessage = SerializationInterface.SerializeUser(user);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_CREATED, returnMessage.length());
				//TODO: Remove debugging statements
				System.out.println(" -> Sent User Information  --");
				}
			else
				{
				//remove invalid cookies
				returnMessage = "{ message : Invalid or expired Cookie! }";
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, returnMessage.length());
				System.out.println(" -> Invalid Cookies");
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

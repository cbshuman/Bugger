package bugger.httpshandlers;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Permission;
import bugger.dataModel.serverModel.User;
import bugger.utility.HandlerUtilites;
import bugger.utility.Utility;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class PermissionHandler extends SecureHTTPHandler
	{
	public void handle(HttpExchange exchange) throws IOException
		{
		if(exchange.getRequestMethod().toLowerCase().equals("post") == true)
			{
			CreateNewPermission(exchange);
			}
		else if(exchange.getRequestMethod().toLowerCase().equals("get") == true)
			{
			GetPermissions(exchange);
			}
		else if(exchange.getRequestMethod().toLowerCase().equals("delete") == true)
			{
			DeletePermission(exchange);
			}
		}

	private void CreateNewPermission(HttpExchange exchange) throws IOException
		{
        System.out.println("\n -- Creating New Permission -- ");

		String returnMessage = "";
		Headers headers = exchange.getRequestHeaders();

		String cookieContents = HandlerUtilites.GetCookieIDFromCookie(headers);

		System.out.println(" -> Authenticating Cookie: " + cookieContents);
		if(HasValidCookie(headers))
			{
			PermissionJSON permission = new Gson().fromJson(Utility.InputStreamToString(exchange.getRequestBody()), PermissionJSON.class);

			User user = HandlerUtilites.GetUserFromCookie(cookieContents);

			if(user.HasPermission("admin") == true)
				{
				System.out.println(" -> Authenticated! Attempting to create permission!");
				if(permission.permissionName.equals("") || permission.permissionDisc.equals(""))
					{
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
					returnMessage = "You must have a discription and group name!";
					}
				else
					{
					returnMessage = CreatePermission(exchange, permission);
					}
				//End of verify cookie if
				}
			else
				{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
				returnMessage = "User does not have required permissions!";
				}
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

	private String CreatePermission(HttpExchange exchange, PermissionJSON data) throws IOException
		{
		String returnMessage = "";
		if(DataProxy.GetPermission(data.permissionName,"permissionName") != null)
			{
			System.out.println(" -> " + data.permissionName + " already exists!");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			returnMessage = "Security group already exists! Try another name!";			
			}
		else
			{
			DataProxy.CreateNewPermission(new Permission(data.permissionName,data.permissionDisc));
			returnMessage = "Security group created!";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_CREATED, 0);
			}
		return(returnMessage);
		}

	private void GetPermissions(HttpExchange exchange) throws IOException
		{
        System.out.println("\n -- Getting Permissions -- ");

		String returnMessage = "";
		Headers headers = exchange.getRequestHeaders();

		String cookieContents = HandlerUtilites.GetCookieIDFromCookie(headers);

		System.out.println(" -> Authenticating Cookie: " + cookieContents);
		if(HasValidCookie(headers))
			{
			List<Permission> permissionList = DataProxy.GetPermissionList(GetHandlerUserID());

			Permission[] permissions = new Permission[permissionList.size()];
			permissionList.toArray(permissions);

			PermissionJSON[] jsonResponce = new PermissionJSON[permissions.length];

			for(int i = 0; i < permissions.length; i++)
				{
				jsonResponce[i] = new PermissionJSON(permissions[i].permissionName, permissions[i].discription);
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

	private void DeletePermission(HttpExchange exchange) throws IOException
		{
        System.out.println("\n -- Deleting Permissions -- ");

		String returnMessage = "";
		Headers headers = exchange.getRequestHeaders();

		String cookieContents = HandlerUtilites.GetCookieIDFromCookie(headers);

		System.out.println(" -> Authenticating Cookie: " + cookieContents);
		if(HasValidCookie(headers))
			{
			String permissionName = exchange.getRequestURI().getPath().substring(17);
			System.out.println(" -> Target Permission: " + permissionName);

			User user = HandlerUtilites.GetUserFromCookie(cookieContents);

			if(user.HasPermission("admin") == true)
				{

				//End of verify cookie if
				}
			else
				{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
				returnMessage = "User does not have required permissions!";
				}
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

	private class PermissionJSON
		{
		public String permissionName;
		public String permissionDisc;

		PermissionJSON(String permissionName, String permissionDisc)
			{
			this.permissionName = permissionName;
			this.permissionDisc = permissionDisc;
			}
		}
	}

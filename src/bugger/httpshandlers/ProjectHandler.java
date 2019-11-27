package bugger.httpshandlers;

import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.serverModel.Project;
import bugger.utility.Utility;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ProjectHandler extends SecureHTTPHandler
	{
	public void handle(HttpExchange exchange) throws IOException
		{
		if(exchange.getRequestMethod().toLowerCase().equals("post"))
			{
			CreateNewProject(exchange);
			}
		else if(exchange.getRequestMethod().toLowerCase().equals("get"))
			{
			GetProjects(exchange);
			}
		}

	private void CreateNewProject(HttpExchange exchange) throws IOException
		{
		Headers headers = exchange.getRequestHeaders();
		String returnMessage = "";

		System.out.println("\n -- Creating a new Project -- ");
		System.out.println(" -> Authenticating Cookie: ");
		if(HasValidCookie(headers))
			{
			System.out.println(" -> Creating Project");
			ProjectJSON newProject = new Gson().fromJson(Utility.InputStreamToString(exchange.getRequestBody()), ProjectJSON.class);

			Project targetProject = new Project(newProject.projectName,newProject.projectDisc,newProject.defaultAssignee);

			if(DataProxy.CreateNewProject(targetProject))
				{
				//TODO: Add project permissions in here

				returnMessage = "Created new project successfully!";
				}
			else
				{
				returnMessage = "Error creating new project! Check that the name is not already taken!";
				}

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

	private void GetProjects(HttpExchange exchange) throws IOException
		{
        System.out.println("\n -- Getting Projects -- ");
		System.out.println(" -> Authenticating Cookie: ");
		//Get the headers
		Headers headers = exchange.getRequestHeaders();
		String returnMessage = "";
		if(HasValidCookie(headers))
			{
			Project[] projects = null; //TODO: Fix this

			ProjectJSON[] jsonResponce = new ProjectJSON[projects.length];

			for(int i = 0; i < projects.length; i++)
				{
				jsonResponce[i] = new ProjectJSON(projects[i]);
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

	private class ProjectJSON
		{
		String projectName;
		String defaultAssignee;
		String projectDisc;
		public String[] permissions;

		ProjectJSON(Project targetProject)
			{
			this.projectName = targetProject.projectName;
			this.projectDisc = targetProject.discription;

			permissions = new String[targetProject.permissions.length];

			for(int i= 0; i < permissions.length; i++)
				{
				permissions[i] = targetProject.permissions[i].permissionName;
				}
			}
		}
	}

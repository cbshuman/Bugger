package bugger;

import bugger.dataAccessInterface.DataProxy;
import bugger.httpshandlers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Bugger
    {
    public static void main(String[] args)
        {
        //Making sure we have a correct number of arguments
        if(args.length != 1)
            {
            System.out.println("Invalid number of arguments. Please only provide the port number");
            return;
            }

        try
            {
            DataProxy.SelectDatabase(DataProxy.DatabaseType.SQL);
            DataProxy.ValidateDatabase();
            }
        catch(Exception e)
            {
            //End the program due to bad juju with the databaseConnection
            return;
            }

        //Setting up the ports
        int portAddress = Integer.parseInt(args[0]);
        InetSocketAddress port = new InetSocketAddress(portAddress);
        HttpServer server;

        //Try getting the server all up and running
        try
            {
            System.out.println("Starting up Bugger v0.3 [| Port: " + portAddress + " |]");
            //Set up the port
            server = HttpServer.create(port,0);

            //Listen in for requests

            //User requests
            server.createContext("/api/users/login", new LoginHandler());
            server.createContext("/api/users/register", new RegisterHandler());
            server.createContext("/api/users/allusers/", new GetAllUsersHandler());
            server.createContext("/api/users", new GetUserHandler());

            //Permission Requests
            server.createContext("/api/permissions/", new PermissionHandler());

            //Project Requests
            server.createContext("/api/projects/security", new ProjectPerHandler());
            server.createContext("/api/projects", new ProjectHandler());

            //Defualt Requests
            server.createContext("/", new RegisterHandler());

            //Execute the database!
            server.setExecutor(null);
            server.start();
            }
        catch(IOException e)
            {
            System.out.println("IOException Detected!");
            e.printStackTrace();
            }
        catch(Exception e)
            {
            System.out.println("Exeception Detected!");
            e.printStackTrace();
            }

        //Let us know that bugger has started successfully!
        System.out.println("# -- Running Bugger -- #");
        }
    //End of Bugger Class
    }
package bugger.httpshandlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;

import java.util.Map;

public abstract class SecureHTTPHandler implements HttpHandler
	{
	private Object List;

	public boolean HasValidCookie(Headers headers)
		{
		boolean returnValue = false;

		for (Map.Entry<String, java.util.List<String>> entry : headers.entrySet())
			{
			if(entry.getKey().toLowerCase().equals("cookie"))
				{
				System.out.println("Found Cookie");
				}
			}

		return(returnValue);
		}
	}

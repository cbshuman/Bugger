package bugger.utility;

import java.io.*;

/**
Utility is a group of static functions that don't fit into 
a particular class, and/or used frequently between several classes.
*/
public abstract class Utility
	{
	public static void WriteStringToStream(OutputStream streamIn,String input) throws IOException
		{
		OutputStreamWriter sw = new OutputStreamWriter(streamIn);
		sw.write(input);
		sw.flush();
		}

	public static String InputStreamToString(InputStream input) throws IOException
		{
		StringBuilder returnString = new StringBuilder();
		InputStreamReader streamReader = new InputStreamReader(input);
		char[] buffer = new char[1024];
		int length;

		while ((length = streamReader.read(buffer)) > 0)
			{
			returnString.append(buffer, 0, length);
			}
		return (returnString.toString());
		}

	}

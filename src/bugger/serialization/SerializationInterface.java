package bugger.serialization;

import bugger.dataModel.serverModel.User;

public class SerializationInterface
	{
	private static ISerializer serializer;

	public enum SerializeType
		{
			gson
		}

	public static void SetSerializer(SerializeType serializeType) throws Exception
		{
		switch (serializeType)
			{
			case gson:
				serializer = new JSONSerializer();
				break;
			default:
				throw(new Exception("Unsupported Serializer!"));
			}
		}

	public static String SerializeUser(User user)
		{
		return(serializer.SerializeUser(user));
		}
	}

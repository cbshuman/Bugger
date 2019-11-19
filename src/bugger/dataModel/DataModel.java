package bugger.dataModel;

import java.util.Random;

public class DataModel
	{
	protected static final String alpNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

	private static final int prime = 131;
	private static final int IDlength = 35;

	public static String GenerateID(String stringA, String stringB, int count)
		{
		StringBuilder returnValue = new StringBuilder();
		int seed = (stringA.charAt(0) + stringB.charAt(0) + count)*prime;

		returnValue.append(stringA.charAt(0));
		returnValue.append(stringB.charAt(0));

		Random rand = new Random(seed);

		while(returnValue.length() < IDlength)
			{
			char nextChar = alpNum.charAt((Math.abs(rand.nextInt())) %alpNum.length());
			returnValue.append(nextChar);
			}

		return(returnValue.toString());
		}
	}

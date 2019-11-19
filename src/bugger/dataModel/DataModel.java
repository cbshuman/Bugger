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

		int seed = 0;

		//Generate a seed
		for(int i = 0; i < stringA.length() && i < stringB.length() && i < IDlength; i++)
			{
			seed += stringA.charAt(i) + stringB.charAt(i);
			}

		seed *= prime;

		//Append the first two letters of the two strings
		returnValue.append(stringA.charAt(0));
		returnValue.append(stringB.charAt(0));

		//Seed our rand generator
		Random rand = new Random(seed);

		//Get our characters of IDlength
		while(returnValue.length() < IDlength)
			{
			char nextChar = alpNum.charAt((Math.abs(rand.nextInt())) %alpNum.length());
			returnValue.append(nextChar);
			}

		//Return the seeded string
		return(returnValue.toString());
		}
	}

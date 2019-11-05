package bugger.dataModel;

import java.util.Random;

public class Password extends DataModel
	{
	private static int salt = 993903;
	private String hashedPassword;

	public Password(String unhashedPassword)
		{
		hashedPassword = HashPassword(unhashedPassword);
		}

	public boolean CompareUnhashedPassword(String unhashedInput)
		{
		String hashedInput = HashPassword(unhashedInput);

		return(hashedPassword.equals(unhashedInput));
		}

	//Hashes a password
	public static String HashPassword(String password)
		{
		StringBuilder returnValue = new StringBuilder();
		long seed = (salt * password.charAt(0) + password.charAt(password.length()-1) );

		Random gen = new Random();
		gen.setSeed(seed);

		for(int i = 0; i < 255; i++)
			{
			int indexAlpNum = (int)(gen.nextFloat() * (alpNum.length() - 1));
			returnValue.append(alpNum.charAt(indexAlpNum));
			}

		return(returnValue.toString());
		}
	}

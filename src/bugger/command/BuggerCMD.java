package bugger.command;

import java.util.LinkedList;

public class BuggerCMD
	{
	private static LinkedList<BuggerCommand> commands = new LinkedList<>();

	public static boolean DoCommand(BuggerCommand command)
		{
		command.DoCommand();

		boolean returnValue = command.CommandSuccessful();

		if(returnValue)
			{
			commands.add(command);
			}

		return(returnValue);
		}

	public static BuggerCommand GetLastCommand()
		{
		return (commands.getFirst());
		}

	public static void FlushCommands()
		{
		//Potentially write a log or audit here

		commands = new LinkedList<>();
		}
	}

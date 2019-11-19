package bugger.command;

public abstract class BuggerCommand<T>
	{
	protected boolean commandSuccessful = false;

	public boolean CommandSuccessful()
		{
		return(commandSuccessful);
		}

	public abstract void DoCommand();
	public abstract T GetReturnValue();
	}

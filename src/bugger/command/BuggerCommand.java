package bugger.command;

public interface BuggerCommand<T>
	{
	void DoCommand();
	Boolean CommandSuccessful();
	T GetReturnValue();
	}

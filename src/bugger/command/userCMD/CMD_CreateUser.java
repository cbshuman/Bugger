package bugger.command.userCMD;

import bugger.command.BuggerCommand;
import bugger.dataAccessInterface.DataProxy;
import bugger.dataModel.DataModel;
import bugger.dataModel.serverModel.User;

public class CMD_CreateUser extends BuggerCommand<User>
	{
	private User user;

	//Take in the user information, password should be unhashed
	public CMD_CreateUser(String username, String email, String password, String alias, String firstName, String lastName)
		{
		//Creates a new user and generates an ID
		user = new User(DataModel.GenerateID(firstName,lastName),username,email,password,alias,firstName,lastName,true);
		}

	@Override
	public void DoCommand()
		{
		if(!commandSuccessful)
			{
			commandSuccessful = DataProxy.CreatenewUser(user);
			}
		}

	@Override
	public User GetReturnValue()
		{
		return user;
		}
	}

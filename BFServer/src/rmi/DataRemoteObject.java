package rmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
//	private ExecuteService executeService;
	protected DataRemoteObject() throws RemoteException, FileNotFoundException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
//		executeService=new ExecuteServiceImpl();
	}
	

	@Override
	public boolean writeFile(String file, String userId, String fileName) throws IOException{
		// TODO Auto-generated method stub
		return iOService.writeFile(file, userId, fileName);
	
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFile(userId, fileName);
	}

	@Override
	public String readFileList(String userId) throws IOException{
		// TODO Auto-generated method stub
		return iOService.readFileList(userId);
	}

	@Override
	public boolean login(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.logout(username);
	}


	@Override
	public String run(String code, String parm) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.run(code, parm);
	}

	

}

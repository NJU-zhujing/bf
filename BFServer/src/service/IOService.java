//需要客户端的Stub
package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException,  IOException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String readFileList(String userId)throws RemoteException, IOException;
	
	public String run(String code,String parm)throws RemoteException;
	

}

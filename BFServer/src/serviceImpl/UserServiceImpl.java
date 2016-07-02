package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import service.UserService;

public class UserServiceImpl implements UserService{
	
	static int state;
	File file=new File("user_password");
	//已登陆用户列表
	static List <String> isLoginAccount=new ArrayList<>();
	
	@Override
	public boolean login(String username, String password) throws Exception {
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		FileWriter fw=new FileWriter(file,true);
		BufferedWriter bw=new BufferedWriter(fw);
		
		if(isLoginAccount.contains(username)){
			return false;
		}
		
		String help=br.readLine();
		while(help!=null){
			
			if(help.substring(0, help.lastIndexOf("_")).equals(username)&&help.substring(help.lastIndexOf("_")+1).equals(password)){
				state=1; 
				isLoginAccount.add(username);
				return true;
			}else if(help.substring(0, help.lastIndexOf("_")).equals(username)&&!help.substring(help.lastIndexOf("_")+1).equals(password)){
				
				return false;
			}
			help=br.readLine();
		}
		br.close();
		fr.close();
		bw.newLine();
		bw.write(username+"_"+password);
		bw.flush();
		bw.close();
		fw.close();
		isLoginAccount.add(username);
		return true;

	}
	
		
	
	@Override
	public boolean logout(String username) throws RemoteException {
		if(state==0){
			return false;
		}else{
			state=0;
			isLoginAccount.remove(username);
			return true;
		}
	}

}

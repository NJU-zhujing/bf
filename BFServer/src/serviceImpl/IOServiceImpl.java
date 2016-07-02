package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import service.IOService;

public class IOServiceImpl implements IOService{
	File file=new File("user_fileList");
	int flag;
	//历史版本写在同名文件
	@Override
	public boolean writeFile(String file0, String userId, String fileName) throws IOException {
		
		String name=userId + "_" +fileName ;
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		FileWriter fw0=new FileWriter(file,true);
		BufferedWriter bw0=new BufferedWriter(fw0);
		String help=br.readLine();
		while(help!=null){
			if(help.equals(name)){
				flag=1;				
			}
			help=br.readLine();
		}
		br.close();
		fr.close();
		
		if(flag!=1){
			bw0.newLine();
			bw0.write(name);
		}
		bw0.flush();
		bw0.close();
		fw0.close();
		
		String saveHelp = new String();
		File f = new File(name);
		try {
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw=new BufferedWriter(fw);
			FileReader Fr=new FileReader(f);
			BufferedReader Br=new BufferedReader(Fr);
			String help2=Br.readLine();
			while(help2!=null){
				saveHelp=help2;
				help2=Br.readLine();
			}
			
			if(saveHelp.equals(file0)){
				
			}else{
			
			
			bw.newLine();
			bw.write(file0);
			}
			Br.close();
			Fr.close();
			bw.flush();
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName)  {
		// TODO Auto-generated method stub
		
		List <String> readHelp=new ArrayList<>();	
		int num = 0;
		int index=fileName.indexOf("_");
		try {
			String result=new String();
			if(index!=-1){
				num=Integer.parseInt(fileName.substring(fileName.length()-1));
				fileName=fileName.substring(0,index);
			}
			
			FileReader file = new FileReader(userId + "_" +fileName);
			BufferedReader br=new BufferedReader(file);
			try {
				String help=br.readLine();
				readHelp.add(help);
				while(help!=null){				
					help=br.readLine();
					readHelp.add(help);
				}
				br.close();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//判断有无历史版本
			try{
				if(readHelp.size()>2){
					result=readHelp.get(readHelp.size()-num-1);	 

				}else{
					result=readHelp.get(0);				
				}
			}
			catch(ArrayIndexOutOfBoundsException e){
				
			}
			
			return result;
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "文件已经丢失或损坏！";
		}
	}

	@Override
	public String readFileList(String userId) throws IOException {
		// TODO Auto-generated method stub
		String list=new String();
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		String help=br.readLine();
		while(help!=null){
			if(help.substring(0, help.indexOf("_")).equals(userId)){
				list+=help.substring(help.indexOf("_")+1)+" ";
			}
			help=br.readLine();
		}
		br.close();
		fr.close();
		
		return list;
	
	}

	@Override
	public String run(String code, String parm) throws RemoteException {
		// TODO Auto-generated method stub
		ExecuteServiceImpl help=new ExecuteServiceImpl();	
		return help.execute(code, parm);
	}
	

}

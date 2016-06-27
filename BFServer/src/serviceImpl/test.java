package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) throws IOException {
		ExecuteServiceImpl go=new ExecuteServiceImpl();
		//hello world 加法 减法
		String a="++++++++++[>+++++++>++++++++++>+++>+<<<<-] >++.>+.+++++++..+++.>++.<< +++++++++++++++. >.+++.------.--------.>+.>.";
		String b=",>++++++[<-------->-],,[<+>-],<.>.";
		String c=",>,,>++++++++[<------<------>>-] <<[>[>+>+<<-]>>[<<+>>-]<<<-] >>>++++++[<++++++++>-],<.>.";
//		System.out.println(go.execute(c, "3*3 "));	
		
//		FileWriter fw=new FileWriter("user_password",true);
//		BufferedWriter bw=new BufferedWriter(fw);
//		FileReader fr=new FileReader("user_password");
//		BufferedReader br=new BufferedReader(fr);
//		
//		String help=br.readLine();
//		BufferedReader br02=new BufferedReader(new InputStreamReader(System.in));
//		String help01=br02.readLine();
//		String help02=br02.readLine();
//		System.out.println(help.equals(help01+"_"+help02));
//		File file=new File("user_password");
//		
//		FileReader fr=new FileReader(file);
//		BufferedReader br=new BufferedReader(fr);
//		FileWriter fw=new FileWriter(file,true);
//		BufferedWriter bw=new BufferedWriter(fw);
//		while(br.readLine()!=null){
//				System.out.println(br.readLine());
//		}
//		bw.newLine();
//		bw.write("aaaaa");
//		bw.flush();
//		bw.close();
//		File file=new File("user_fileList");
//		int flag=0;
//		
//			String name="admin_code";
//			FileReader fr=new FileReader(file);
//			BufferedReader br=new BufferedReader(fr);
//			FileWriter fw0=new FileWriter(file,true);
//			BufferedWriter bw=new BufferedWriter(fw0);
//			
//			String aaaa=br.readLine();
//			while(aaaa!=null){
//				System.out.println(aaaa);
//				aaaa=br.readLine();
//			}
//			br.close();
//			fr.close();
			
//			if(flag!=1){
//				bw.newLine();
//				bw.write(name);
//			}
//			bw.flush();
//			bw.close();
//			fw0.close();
//		String fileName="code_1";
//		int index=fileName.indexOf("_");
//		int num=0;
//			String result=new String();
//			if(index!=-1){
//				num=Integer.parseInt(fileName.substring(fileName.length()-1));
////				System.out.println(num);
//				fileName=fileName.substring(0,index);
////				System.out.println(fileName);
//			}
//			FileReader file = new FileReader("admin" + "_" +fileName);
//			BufferedReader br=new BufferedReader(file);
//			System.out.println(br.readLine());
//			br.close();
//			file.close();
		
		IOServiceImpl help=new IOServiceImpl();
//		System.out.println(help.readFile("admin", "code_1"));
		help.writeFile("cccc", "admin", "code");
	}
}

//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {
	/**
	 * 请实现该方法
	 */
	//模拟栈和指针
	char[] stack;
	int pointer=0;
	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO Auto-generated method stub
			StringBuffer result=new StringBuffer();
			boolean order=false;
			//代码和数据数组
			char []codeArray=code.toCharArray();
			char []dataArray=param.toCharArray();
			
			stack=new char[code.length()];
			int length=code.length();
			int numOfDataArray=0;
			
			for(int i=0;i<length;i++){
				switch(codeArray[i]){
				case'>':if(pointer>=length)this.expand();//栈扩展
						pointer++;
						break;
				case'<':pointer--;
						break;
				case'+':stack[pointer]++;
					    break;
				case'-':stack[pointer]--;
				        break;
				case'.':
					if(order){
						if(stack[pointer]>=48){	
							result.append(String.valueOf(stack[pointer]-'0'));
						}else{
							result.append(stack[pointer]);				
						}			
					}else {
						result.append(stack[pointer]);	
					}
					break;
				case',':
					char next=dataArray[numOfDataArray];
					//区分加减乘除与其它操作导致结果发生变化
				    if(next=='+'||next=='-'||next=='*'||next=='/'){
				        order=true;
				    }
				    stack[pointer]=next;
				    numOfDataArray++;
				    break;
				
				case'[':
					if(i>=0&&i<length){
						if(stack[pointer]==0){
							int m=1;//多重循环计数
							while (m!=0){
								i++;
								if(codeArray[i]=='['){
									m++;
								}else if(codeArray[i]==']'){
									m--;
								}
							}
						}
					}
				    break;
				
				case']':
					if(i>=0&&i<length) {
						if (stack[pointer]!=0){
							int m=1;//多重循环计数
							while (m!=0){
								i--;
								if(codeArray[i]==']'){
									m++;
								}else if(codeArray[i]=='['){
									m--;
								}
							}                 
						}
					}
					break;
				}
			}
	
		return result.toString();
	}
	
	public void expand(){
		int capacity=this.stack.length*2;
		//扩大两倍
        char[] newStack=new char[capacity];
        for (int i = 0; i < this.stack.length; i++) {
            newStack[i]=this.stack[i];
        }
        this.stack=newStack;

	}

}

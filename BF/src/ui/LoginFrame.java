package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.*;

import rmi.RemoteHelper;


public class LoginFrame extends JFrame{
	//
	static String userId;
	//基本组件
	JTextField textField;
	JPasswordField passwordField;
	JLabel account;
	JLabel password;
	JButton cancel;
	JButton ok;
	JButton register;
	
	JRadioButton saveIdAndP;
	public LoginFrame(){
		super("BF Login");
		textField=new JTextField(10);
		passwordField=new JPasswordField(10);
		cancel=new JButton("CANCEL");
		ok=new JButton("OK");
		account=new JLabel("账户：");
		password=new JLabel("密码：");
		
		backGroundPanel background=new backGroundPanel();
		background.setLayout(null);
		
		ok.setBounds(120, 240, 100, 40);
		cancel.setBounds(280, 239, 100, 40);
	
		ok.setFont(new Font("", Font.ITALIC, 16));
		cancel.setFont(new Font("", Font.ITALIC, 16));
		account.setFont(new Font("", Font.ITALIC, 28));
		account.setBounds(220,58,160,28);
		textField.setBounds(300, 58, 160, 30);
		textField.setFont(new Font("", Font.ITALIC, 24));
		password.setFont(new Font("", Font.ITALIC, 28));
		password.setBounds(220,128,160,28);
		passwordField.setBounds(300, 128, 160, 30);
		passwordField.setFont(new Font("", Font.ITALIC, 24));
		

		
		passwordField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int a=e.getKeyCode();
				if(a==KeyEvent.VK_ENTER){
					loginHelper();
					
				}
				
			}
		});
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				loginHelper();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginFrame.this.dispose();
				
			}
		});
		
		//记住账户和密码
		 saveIdAndP=new JRadioButton("save password");
		
		background.add(textField);
		background.add(passwordField);
		background.add(account);
		background.add(password);
		background.add(cancel);
		background.add(ok);
		this.add(background);
		Toolkit tool=Toolkit.getDefaultToolkit();
		Dimension dim=tool.getScreenSize();
		int width=(int)dim.getWidth();
		int height=(int)dim.getHeight();
		this.setBounds((width-490)/2, (height-330)/2, 490,330 );
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	//登陆help
	public void loginHelper(){
		try {
			boolean help=RemoteHelper.getInstance().getUserService().login(textField.getText(), String.valueOf(passwordField.getPassword()));
			if(help){
				LoginFrame.this.dispose();
				userId=textField.getText();
			}else{
				JOptionPane.showMessageDialog(null,  "你输的的密码有误");			
				passwordField.setText("");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
}
//背景
class backGroundPanel extends JPanel{
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		String help="bg"+String.valueOf(MainFrame.bgNum%4)+".png";	
		Image img=new ImageIcon(this.getClass().getResource(help)).getImage();
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),this);
		
	}
	
	
}

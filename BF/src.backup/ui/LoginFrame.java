package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.*;

import rmi.RemoteHelper;


public class LoginFrame extends JFrame{
	static String userId;
	JTextField textField;
	JPasswordField passwordField;
	JLabel account;
	JLabel password;
	JButton cancel;
	JButton ok;
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
		
		ok.setBounds(80, 240, 120, 40);
		cancel.setBounds(280, 239, 120, 40);
		ok.setFont(new Font("", Font.ITALIC, 20));
		cancel.setFont(new Font("", Font.ITALIC, 20));
		account.setFont(new Font("", Font.ITALIC, 28));
		account.setBounds(220,60,160,28);
		textField.setBounds(300, 60, 160, 30);
		textField.setFont(new Font("", Font.ITALIC, 24));
		password.setFont(new Font("", Font.ITALIC, 28));
		password.setBounds(220,130,160,28);
		passwordField.setBounds(300, 130, 160, 30);
		passwordField.setFont(new Font("", Font.ITALIC, 24));
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					boolean help=RemoteHelper.getInstance().getUserService().login(textField.getText(), String.valueOf(passwordField.getPassword()));
//					String help=textField.getText()+"_"+ String.valueOf(passwordField.getPassword());
//					System.out.println(help);
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
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginFrame.this.dispose();
				
			}
		});
		
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
	public static void main(String[] args) {
		new LoginFrame();
	}
}
class backGroundPanel extends JPanel{
	//486 407
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		Image img=new ImageIcon(this.getClass().getResource("bg.png")).getImage();
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),this);
		
	}
	
	
}

package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginFrame extends JFrame {
	
	private Box box1=Box.createHorizontalBox();//水平的盒子
	private Box box2=Box.createHorizontalBox();
	private Box box3=Box.createHorizontalBox();
	private Box box5=Box.createHorizontalBox();
	private Box box4=Box.createVerticalBox();//竖直的盒子
	
	private Icon ico;
	private JLabel jl=new JLabel();
	private JButton jbt1=new JButton("  ok  ");
	private JButton jbt2=new JButton("cancel");
	private JLabel jl1=new JLabel("User Name:");
	private JLabel jl2=new JLabel(" Password:");
	private JTextField jt1=new JTextField(10);
	private JPasswordField jp1=new JPasswordField(10);
	
	public LoginFrame(){
		super("BF Login");
		this.setSize(450,330);
		//未完成
		jbt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//未完成
		jp1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		jbt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginFrame.this.dispose();
			}
		});
		
		
		Toolkit tool=Toolkit.getDefaultToolkit();
		Dimension dim=tool.getScreenSize();
		int width=(int)dim.getWidth();
		int height=(int)dim.getHeight();
		
		this.setLocation((width-450)/2, (height-350)/2);
		this.setResizable(false);
		
		
		ico=new ImageIcon(this.getClass().getResource("/ui/api_thumb_450.jpg"));
		jl.setIcon(ico);
		box1.add(jl);
		
		box2.add(Box.createHorizontalStrut(80));//增加水平间距
		box2.add(jl1);
		box2.add(Box.createHorizontalStrut(10));
		box2.add(jt1);
		box2.add(Box.createHorizontalStrut(100));
		
		box3.add(Box.createHorizontalStrut(80));
		box3.add(jl2);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(jp1);
		box3.add(Box.createHorizontalStrut(100));
		box5.add(jbt1);
		box5.add(Box.createHorizontalStrut(20));
		box5.add(jbt2);	
		
		box4.add(box1);	
		box4.add(Box.createVerticalStrut(20));//增加竖直间距
		box4.add(box2);
		box4.add(Box.createVerticalStrut(40));
		box4.add(box3);	
		box4.add(Box.createVerticalStrut(40));
		box4.add(box5);	
		box4.add(Box.createVerticalStrut(40));
		this.add(box4);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
	}
//	public static void main(String[] args) {
//		LoginFrame LF=new LoginFrame();
//	}

}

package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.naming.TimeLimitExceededException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.SliderUI;

import rmi.RemoteHelper;

public class MainFrame extends JFrame {
	
	//undo和redo辅助
	List <String>codeArray=new ArrayList<>();
	boolean undoOrRedoFlag=true;
	int undoTime;
	int pointer;
	//主要组件
	private JSlider slider;
	private JTextArea textArea;
	private JTextArea resultTextArea;
	private JTextArea inputTextArea;
	//时间轴上label
	private JLabel label00=new JLabel("  now");
	private JLabel label01=new JLabel("v 1");
	private JLabel label02=new JLabel("v 2");
	private JLabel label03=new JLabel("v 3");
	private JLabel label04=new JLabel("v 4");
	//打开和单选菜单
	ButtonGroup group=new ButtonGroup();
	JMenuItem openMenuItem;
	JMenu versionMenu;
//	当前版本
	String version;
	int versionNum;
	String fileName=new String();
	
	public MainFrame() {
		// 创建窗体
		super("BF Client");
		background bg=new background();		
		bg.setLayout(new BorderLayout());
		
		codeArray.add("请在此输入BF代码...");

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu editMenu=new JMenu("Edit");
		menuBar.add(editMenu);
		JMenu accountMenu=new JMenu("Account");
		menuBar.add(accountMenu);
		versionMenu=new JMenu("Version");
		menuBar.add(versionMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 1"));
		
		fileMenu.add(newMenuItem);
		openMenuItem = new JMenuItem("Open");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 2"));
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 3"));
		fileMenu.add(openMenuItem);	
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		
		
		JMenuItem exitMenuItem=new JMenuItem("Exit");

		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(new MenuItemActionListener());
		JMenuItem runMenuItem = new JMenuItem("Run");
		runMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 4"));
		runMenuItem.addActionListener(new MenuItemActionListener());
		
		JMenuItem undoMenuItem=new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 5"));
		
		JMenuItem redoMenuItem=new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 6"));
		editMenu.add(runMenuItem);
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
		
		JMenuItem loginMenuItem =new JMenuItem("Login");
		loginMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 7"));
		JMenuItem logoutMenuItem =new JMenuItem("Out");
		logoutMenuItem.addActionListener(new MenuItemActionListener());
		logoutMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 8"));
		accountMenu.add(loginMenuItem);
		accountMenu.addSeparator();
		accountMenu.add(logoutMenuItem);
		
		loginMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new LoginFrame();
			}
		});
		
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());	
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new MenuItemActionListener());
		undoMenuItem.addActionListener(new UndoActionListener());
		redoMenuItem.addActionListener(new RedoActionListener());
		this.setJMenuBar(menuBar);
		
		//
		JPopupMenu popup=new JPopupMenu();
		JMenuItem runMenuItem1=new JMenuItem("Run");
		JMenuItem undoMenuItem1=new JMenuItem("Undo");
		JMenuItem redoMenuItem1=new JMenuItem("Redo");
		popup.add(runMenuItem1);
		popup.add(undoMenuItem1);
		popup.add(redoMenuItem1);
		
		//timeline
		JPanel nouthPanel=new JPanel();
		slider=new JSlider();
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(25);
		Dictionary<Integer,Component>labelTable=new Hashtable<>();
		labelTable.put(0, label00);
		labelTable.put(25, label01);
		labelTable.put(50, label02);
		labelTable.put(75, label03);
		labelTable.put(100, label04);
		slider.setLabelTable(labelTable);
		nouthPanel.setLayout(new GridLayout(1, 2));
		JLabel code=new JLabel("Code:");
		code.setFont(new Font("", Font.ITALIC, 28));
		nouthPanel.add(code);
		
		nouthPanel.add(new JLabel("Timeline",SwingConstants.RIGHT));
		nouthPanel.add(slider);
		
		
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(version!=null){
					if(LoginFrame.userId!=null){
						int helpNum=slider.getValue();
						if(helpNum==25){
							fileName=label01.getText();
						}else if(helpNum==50){
							fileName=label02.getText();
							
						}else if(helpNum==75){
							fileName=label03.getText();
							
						}else if(helpNum==100){
							fileName=label04.getText();					
						}
						
						try{
						textArea.setText(RemoteHelper.getInstance().getIOService().readFile(LoginFrame.userId, fileName));
						inputTextArea.setText("此输入操作数或保存文件名...");
						resultTextArea.setText("");
						
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
					}
			}
			}
			
		});
			
			
		nouthPanel.setOpaque(false);
		bg.add(nouthPanel,BorderLayout.NORTH);	
		
		
		textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setFont(new Font("华文仿宋",Font.ITALIC,24));
		textArea.setText("请在此输入BF代码...");
		textArea.addMouseListener(new MyMouseListener("请在此输入BF代码..."));
		textArea.setForeground(Color.green);
		textArea.addKeyListener(new KeyListener() {
			
			
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
				if(undoTime!=0){
					undoTime=0;
					pointer=0;
					String help=codeArray.get(codeArray.size()-2);
					System.out.println(pointer);
					codeArray=new ArrayList<>();
					codeArray.add(help);
				}
				
			}
			
		});
		
		textArea.getDocument().addDocumentListener(new MyDocumentListener());
		JScrollPane jsp=new JScrollPane(textArea);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		textArea.setComponentPopupMenu(popup);
		textArea.setOpaque(false);
		bg.add(jsp, BorderLayout.CENTER);
		
		// 显示输入和结果
		resultTextArea = new JTextArea();
		resultTextArea.setFont(new Font("", Font.ITALIC, 28));
		resultTextArea.setForeground(Color.RED);
		inputTextArea=new JTextArea(2,8);
		JScrollPane js=new JScrollPane(inputTextArea);
		JScrollPane js02=new JScrollPane(resultTextArea);
		inputTextArea.setFont(new Font("",Font.ITALIC, 15));
		inputTextArea.setForeground(Color.BLUE);
		inputTextArea.setText("此输入操作数或保存文件名...");
		inputTextArea.addMouseListener(new MyMouseListener("此输入操作数或保存文件名..."));
		

		JPanel southPanel=new JPanel();
		southPanel.setLayout(new GridLayout(2, 2));
		JLabel help1=new JLabel(" Input:");
		JLabel help2=new JLabel(" Result:");
		help1.setFont(new Font("", Font.ITALIC, 28));
		help2.setFont(new Font("", Font.ITALIC, 28));
		southPanel.add(help1);
		southPanel.add(help2);
		southPanel.add(js);
		southPanel.add(js02);
		southPanel.setOpaque(false);
		bg.add(southPanel, BorderLayout.SOUTH);
		
		Toolkit tool=Toolkit.getDefaultToolkit();
		Dimension dim=tool.getScreenSize();
		int width=(int)dim.getWidth();
		int height=(int)dim.getHeight();
		this.add(bg);
		this.setSize(700, 550);
		this.setLocation((width-700)/2, (height-550)/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	class MyMouseListener  implements MouseListener{

		String help;
		public MyMouseListener(String a){
			help=a;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(inputTextArea.getText().equals(help)){
				inputTextArea.setText("");
				inputTextArea.requestFocus();
			}else if(textArea.getText().equals(help)){
				textArea.setText("");
				textArea.requestFocus();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		
		}
	
}
	class RadioButtomListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(LoginFrame.userId!=null){
				String order=e.getActionCommand();
				version=order;
				
				Dictionary<Integer,Component>labelTable1=new Hashtable<>();				
				try{
					label01=new JLabel(order+"_1");
					label02=new JLabel(order+"_2");
					label03=new JLabel(order+"_3");
					label04=new JLabel(order+"_4");
				
				}catch(ArrayIndexOutOfBoundsException e1){
				
				}
				labelTable1.put(0, label00);
				labelTable1.put(25, label01);
				labelTable1.put(50, label02);
				labelTable1.put(75, label03);
				labelTable1.put(100, label04);

				slider.setLabelTable(labelTable1);
			}
		
	}
	}
	
	
	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				if(LoginFrame.userId!=null){
					try {	
						String[] help=RemoteHelper.getInstance().getIOService().readFileList(LoginFrame.userId).split(" ");									
						for(int i=0;i<help.length;i++){
							JRadioButtonMenuItem jrbm=new JRadioButtonMenuItem(help[i]);
							jrbm.setSelected(false);
							group.add(jrbm);
							versionMenu.add(jrbm);
							jrbm.addActionListener(new RadioButtomListener());
						}
						
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null,  "尚未登陆");
				}

			} else if (cmd.equals("New")) {
				textArea.setText("请在此输入BF代码...");
				inputTextArea.setText("此输入操作数或保存文件名...");
				resultTextArea.setText("");
				
				
				
			} else if (cmd.equals("Run")) {
				try {
					String a=RemoteHelper.getInstance().getIOService().run(textArea.getText(), inputTextArea.getText());
					resultTextArea.setText(a);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
			}else if(cmd.equals("Exit")){
				System.exit(0);
			}else if(cmd.equals("Out")){
				try {
					boolean help=RemoteHelper.getInstance().getUserService().logout(LoginFrame.userId);
					if(help){
						JOptionPane.showMessageDialog(null,  "登出成功");
						LoginFrame.userId=null;
					}else{
						JOptionPane.showMessageDialog(null,  "尚未登陆");
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	class MyDocumentListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			try{
			if(undoOrRedoFlag){
				pointer++;
				codeArray.add(textArea.getText());
//				System.out.println("+ "+pointer);
			}
//			else{
//				System.out.println(pointer);
//			}		
			}
			catch(ArrayIndexOutOfBoundsException e1){
				
			}
			
		}
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			try{
			if(undoOrRedoFlag){				
				pointer++;
				codeArray.add(textArea.getText());
//				System.out.println("- "+pointer);
			}
//			else{
//				System.out.println(pointer);	
//			}
			}
			catch(ArrayIndexOutOfBoundsException e1){
				
			}
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		String code = textArea.getText();
		String ver=inputTextArea.getText();
		if(LoginFrame.userId!=null){
			try {
				if(ver!=null){
					version=ver;
				}else{					
				
				}
				RemoteHelper.getInstance().getIOService().writeFile(code, LoginFrame.userId,ver );		 
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null,  "尚未登陆");
		}

	}
	}
	
	class UndoActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try{
			undoOrRedoFlag=false;
			pointer--;
			textArea.setText(codeArray.get(pointer));		
			undoTime++;
			undoOrRedoFlag=true;
			}
			catch(ArrayIndexOutOfBoundsException e1){
				
			}
			
		}
		
	}
	
	class RedoActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try{
			if(undoTime!=0){
				undoOrRedoFlag=false;
				pointer++;
				textArea.setText(codeArray.get(pointer));
				undoTime--;
				undoOrRedoFlag=true;
			}
		}
		catch(ArrayIndexOutOfBoundsException e1){
			
		}
			
			
		}	
	}
	
	public static void main(String[] args) {
		MainFrame MF=new MainFrame();
	}
	
}
class background extends JPanel{
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		Image img=new ImageIcon(this.getClass().getResource("background.png")).getImage();
		g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),this);
		
	}
}


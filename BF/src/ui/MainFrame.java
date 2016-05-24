package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Popup;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rmi.RemoteHelper;

public class MainFrame extends JFrame {
	private JSlider slider;
	private JTextArea textArea;
	private JButton resultButton;
	private JTextField inputTextField;

	
	public MainFrame() {
		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu editMenu=new JMenu("Edit");
		menuBar.add(editMenu);
		JMenu accountMenu=new JMenu("Account");
		menuBar.add(accountMenu);
		
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 1"));
		
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 2"));
		fileMenu.add(openMenuItem);	
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 3"));
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		JMenuItem runMenuItem = new JMenuItem("Run");
		runMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 4"));
		fileMenu.add(runMenuItem);
		JMenuItem exitMenuItem=new JMenuItem("Exit");
		//已完成
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenuItem undoMenuItem=new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 5"));
		
		editMenu.add(undoMenuItem);
		JMenuItem redoMenuItem=new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl 6"));
		editMenu.add(redoMenuItem);
		
		JMenuItem loginMenuItem =new JMenuItem("Login");
		JMenuItem logoutMenuItem =new JMenuItem("Out");
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
		frame.setJMenuBar(menuBar);
		
		//
		JPopupMenu popup=new JPopupMenu();
		JMenuItem undoMenuItem1=new JMenuItem("Undo");
		JMenuItem redoMenuItem1=new JMenuItem("Redo");
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
		labelTable.put(0, new JLabel("     now"));
		labelTable.put(25, new JLabel("version1"));
		labelTable.put(50, new JLabel("version2"));
		labelTable.put(75, new JLabel("version3"));
		labelTable.put(100, new JLabel("version4"));
		labelTable.put(125, new JLabel("version5"));
		slider.setLabelTable(labelTable);
		nouthPanel.setLayout(new GridLayout(1, 2));
		nouthPanel.add(new JLabel("Timeline",SwingConstants.RIGHT));
		nouthPanel.add(slider);
		//未完成
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.add(nouthPanel,BorderLayout.NORTH);	
		
		
		textArea = new JTextArea();
		textArea.setText("code");
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.LIGHT_GRAY);	
		JScrollPane jsp=new JScrollPane(textArea);
		textArea.setComponentPopupMenu(popup);
		
		frame.add(jsp, BorderLayout.CENTER);
		
		// 显示输入和结果
		resultButton = new JButton();
		resultButton.setText("result");
		inputTextField=new JTextField();
		inputTextField.setText("input");
		
//		inputButton=new JButton();
//		inputButton.setText("input");
		JPanel southPanel=new JPanel();
		southPanel.setLayout(new GridLayout(1, 2));
		southPanel.add(inputTextField);
		southPanel.add(resultButton);
		
		frame.add(southPanel, BorderLayout.SOUTH);
		
		Toolkit tool=Toolkit.getDefaultToolkit();
		Dimension dim=tool.getScreenSize();
		int width=(int)dim.getWidth();
		int height=(int)dim.getHeight();
		frame.setSize(700, 550);
		frame.setLocation((width-700)/2, (height-550)/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		Image img=new ImageIcon("icon.gif").getImage();
		frame.setIconImage(img);
		
	}

	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				textArea.setText("Open");
			} else if (cmd.equals("Save")) {
				textArea.setText("Save");
			} else if (cmd.equals("Run")) {
				resultButton.setText("Hello, result");
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}
	public static void main(String[] args) {
		MainFrame MF=new MainFrame();
		
		
	}
}

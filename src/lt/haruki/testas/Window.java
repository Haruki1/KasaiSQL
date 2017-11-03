package lt.haruki.testas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class Window {
	
	private JFrame frame;
	private JMenuBar menuBar;
	private JLabel label;
	private JTable table;
	private int width;
	private int height;
	private String title;
	
	public Window(int width, int height, String title) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		InitializeWindow();
	}

	private void InitializeWindow() {
		frame = new JFrame();
		frame.setLayout(new GridLayout(0, 1, 0, 0));
		frame.setTitle(title);
		frame.setSize(width, height);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
	}
	
	public void AddElementsToWindow() {
		
		menuBar = new JMenuBar();
		label = new JLabel("", SwingConstants.CENTER);
		table = new JTable();
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		menuBar.add(edit);
		JMenu view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		menuBar.add(view);
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		menuBar.add(help);
		JMenu secret = new JMenu(":)");
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(secret);
		
		//FILE Menu Items
		JMenuItem fileItem1 = new JMenuItem("New Connection...");
		fileItem1.setMnemonic(KeyEvent.VK_C);
		fileItem1.setToolTipText("Initialize New Connection To Database");
		fileItem1.addActionListener((ActionEvent event) -> {
			App.cDialog.setVisible(true);
			frame.dispose();
		});
		JMenuItem fileItem2 = new JMenuItem("New");
		fileItem2.setMnemonic(KeyEvent.VK_N);
		fileItem2.setToolTipText("New Table");
		JMenuItem fileItem3 = new JMenuItem("Open");
		fileItem3.setMnemonic(KeyEvent.VK_O);
		fileItem3.setToolTipText("Open Table");
		JMenuItem fileItem4 = new JMenuItem("Save");
		fileItem4.setMnemonic(KeyEvent.VK_S);
		fileItem4.setToolTipText("Save Table");
		JMenuItem fileItem5 = new JMenuItem("Save As...");
		fileItem5.setMnemonic(KeyEvent.VK_A);
		fileItem5.setToolTipText("Save Table as SQL file");
		JMenuItem fileItem6 = new JMenuItem("Exit");
		fileItem6.setMnemonic(KeyEvent.VK_E);
		fileItem6.setToolTipText("Exit application");
		fileItem6.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		
		JMenuItem secretItem = new JMenuItem("Don't Click Me");
		secretItem.addActionListener((ActionEvent event) -> {
			frame.setTitle("It's peanut butter jelly time!");
			frame.getContentPane().setBackground(Color.YELLOW);
		});
		secret.add(secretItem);
		
		file.add(fileItem1);
		file.addSeparator();
	    file.add(fileItem2);
	    file.add(fileItem3);
	    file.add(fileItem4);
	    file.add(fileItem5);
	    file.addSeparator();
	    file.add(fileItem6);
	        
		frame.add(label);
		frame.setJMenuBar(menuBar);
	}
	
	public void setLabel(String x) {
		label.setText("<html>" + x + "</html>");
	}
	
	public void extendLabel(String x) {
		label.setText(label.getText().substring(0, label.getText().length() - 7) + "<br/>" + x + "</html>");
	}
	
	public void drawTable(Object[][] content, Object[] header) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table = new JTable(content, header);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		frame.add(new JScrollPane(table), BorderLayout.CENTER);
		frame.pack();
	}
	
}

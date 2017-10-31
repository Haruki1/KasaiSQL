package lt.haruki.testas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
		menuBar = new JMenuBar();
		frame.setLayout(new FlowLayout());
		label = new JLabel("", SwingConstants.CENTER);
		table = new JTable();
		frame.setTitle(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		JMenu edit = new JMenu("Edit");
		file.setMnemonic(KeyEvent.VK_E);
		menuBar.add(edit);
		JMenu view = new JMenu("View");
		file.setMnemonic(KeyEvent.VK_V);
		menuBar.add(view);
		JMenu help = new JMenu("Help");
		file.setMnemonic(KeyEvent.VK_H);
		menuBar.add(help);
		
		JMenuItem item1 = new JMenuItem("New");
		JMenuItem item2 = new JMenuItem("Open");
		JMenuItem item3 = new JMenuItem("Save");
		JMenuItem item4 = new JMenuItem("Save As...");
		JMenuItem item5 = new JMenuItem("Exit");
		item1.setMnemonic(KeyEvent.VK_N);
		item2.setMnemonic(KeyEvent.VK_O);
		item3.setMnemonic(KeyEvent.VK_S);
		item4.setMnemonic(KeyEvent.VK_A);
		item5.setMnemonic(KeyEvent.VK_E);
		item1.setToolTipText("New Table");
		item2.setToolTipText("Open Table");
		item3.setToolTipText("Save Table");
		item4.setToolTipText("Save Table as SQL file");
		item5.setToolTipText("Exit application");
		item5.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		
	    file.add(item1);
	    file.add(item2);
	    file.add(item3);
	    file.add(item4);
	    file.add(item5);
	        
		frame.add(label);
		frame.setJMenuBar(menuBar);
		
		frame.setVisible(true);
		
		
		
	}
	
	public void setLabel(String x) {
		label.setText("<html>" + x + "</html>");
	}
	
	public void extendLabel(String x) {
		label.setText(label.getText().substring(0, label.getText().length() - 7) + "<br/>" + x + "</html>");
	}
	
	public void drawTable(Object[][] content, Object[] header) {
		table = new JTable(content, header);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		frame.add(new JScrollPane(table), BorderLayout.CENTER);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.pack();
	}
	
}

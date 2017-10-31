package lt.haruki.testas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class Window {
	private JFrame frame;
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
		frame.setLayout(new FlowLayout());
		label = new JLabel("", SwingConstants.CENTER);
		table = new JTable();
		frame.setTitle(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.add(label);
		
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

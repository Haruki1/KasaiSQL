package lt.haruki.testas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class NewConnectionDialog {
	
	public JTextField getHostnameField() {
		return hostnameField;
	}

	public void setHostnameField(JTextField hostnameField) {
		this.hostnameField = hostnameField;
	}

	public JTextField getEncodingField() {
		return encodingField;
	}

	public void setEncodingField(JTextField encodingField) {
		this.encodingField = encodingField;
	}

	public JTextField getDatabaseField() {
		return databaseField;
	}

	public void setDatabaseField(JTextField databaseField) {
		this.databaseField = databaseField;
	}

	public JTextField getUserField() {
		return userField;
	}

	public void setUserField(JTextField userField) {
		this.userField = userField;
	}

	public JTextField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}
	
	private JDialog start;
	private BoxLayout bl;
	private JButton connect;
	private JTextField hostnameField;
	private JTextField encodingField;
	private JTextField databaseField;
	private JTextField userField;
	private JPasswordField passwordField;
	
	public NewConnectionDialog() {
		InitializeWindow();
	}
	
	public void setVisible(boolean x) {
		InitializeWindow();
	}
	
	private void InitializeWindow() {
		start = new JDialog((JDialog)null);
		start.setTitle("KasaiSQL > New Connection...");
		bl = new BoxLayout(start.getContentPane(), 1);
		connect = new JButton("Connect");
		start.setLayout(bl);
		hostnameField = new JTextField("127.0.0.1", 100);
		encodingField = new JTextField("utf8", 100);
		databaseField = new JTextField("c:/users/darbinis/desktop/testas.fdb",100);
		userField  = new JTextField("SYSDBA" ,100);
		passwordField = new JPasswordField("testas", 100);
		passwordField.setEchoChar('*');
		start.add(new JLabel("Hostname: "));
		start.add(hostnameField);
		start.add(new JLabel("Database Encoding: "));
		start.add(encodingField);
		start.add(new JLabel("Database Name (Full path): "));
		start.add(databaseField);
		start.add(new JLabel("Database User: "));
		start.add(userField);
		start.add(new JLabel("Database Password: "));
		start.add(passwordField);
		start.add(connect);
		
		start.setSize(360,240);
		start.setResizable(false);
		start.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		start.setLocationRelativeTo(null);
		
		start.setVisible(true);
		
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.UseSql();
				App.ResetVariables();
				App.AssignVariables();
				//TODO: Fix future connections after opening table.
				start.dispose();
			}
		});
	}
}

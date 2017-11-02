package lt.haruki.testas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class App {

	/*== CONSTANTS ===========================*/
	public static final String TITLE = "KasaiSQL";
	public static final String VERSION = "SNAPSHOT-0.0.2";
	/*== VARIABLES ===========================*/
	private static Sql sql;
	private static String hostname;
	private static String encoding;
	private static String database;
	private static String user;
	private static String password;
	private static String table;
	private static JDialog start;
	private static BoxLayout bl;
	private static JButton connect;
	private static JTextField hostnameField;
	private static JTextField encodingField;
	private static JTextField databaseField;
	private static JTextField userField;
	private static JTextField passwordField;
	private static JTextField tableField;
	/*========================================*/
	
	public static void main(String args[]) throws Exception {
		
		InitializeStartWindow();
		
	}
	
	private static void InitializeStartWindow() {
		start = new JDialog();
		start.setTitle("KasaiSQL > New Connection...");
		bl = new BoxLayout(start.getContentPane(), 1);
		connect = new JButton("Connect");
		start.setLayout(bl);
		hostnameField = new JTextField("127.0.0.1", 100);
		encodingField = new JTextField("utf8", 100);
		databaseField = new JTextField("c:/users/darbinis/desktop/testas.fdb",100);
		userField  = new JTextField("SYSDBA" ,100);
		passwordField = new JTextField("testas", 100);
		tableField  = new JTextField("cars", 100);
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
		start.add(new JLabel("Table: "));
		start.add(tableField);
		start.add(connect);
		
		start.setSize(480,320);
		start.setResizable(false);
		start.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		start.setLocationRelativeTo(null);
		
		start.setVisible(true);
		
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AssignVariables();
				useSql();
				start.dispose();
			}
		});
	}
	
	private static void AssignVariables() {
		hostname = hostnameField.getText();
		encoding = encodingField.getText();
		database = databaseField.getText();
		user = userField.getText();
		password = passwordField.getText();
		table = tableField.getText();
	}
	
	private static void useSql() {
		sql = new Sql(TITLE + "-" + VERSION, hostname, encoding, database, user, password, table);
		
		sql.PrintDriverVersion();
		sql.InitializeSqlConnection();
		sql.DisableAutoCommit();
		sql.ShowTables();
		/*==QUERIES====================================================*/
		sql.DoQuery("SELECT a.car_id, a.car_model, a.garage_id, b.diena, b.status"
				+ " FROM CARS a LEFT OUTER JOIN days b USING (car_id)");
		/*=============================================================*/
		sql.endConnection();
	}
	
}
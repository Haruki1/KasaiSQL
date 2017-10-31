package lt.haruki.testas;

import java.sql.*;

public class App {

	/*== VARIABLES ===========================*/
	private static Window window;
	private static Sql sql;
	/*========================================*/
	
	public static void main(String args[]) throws Exception {
		
		window = new Window(640, 480, "Kasai SQL-SNAPSHOT 0.0.1");
		sql = new Sql("localhost", "UTF8", "C:/Users/Darbinis/Desktop/TESTAS.FDB", "SYSDBA", "testas");
		sql();
		window.drawTable(sql.content, sql.header);
	}
	
	public static void sql() {
		
		try {
			sql.PrintDriverVersion();
			sql.InitializeSqlConnection();
			sql.DisableAutoCommit();
			sql.ShowTables();
			/*==QUERIES====================================================*/
			sql.DoQuery("SELECT * FROM CARS");
		} finally {
			sql.endConnection();
		}
	}
}
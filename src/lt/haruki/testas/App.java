package lt.haruki.testas;

import java.sql.*;

public class App {

	/*== VARIABLES ===========================*/
	private static Sql sql;
	/*========================================*/
	
	public static void main(String args[]) throws Exception {
		
		sql = new Sql("KasaiSQL", "localhost", "UTF8", "C:/Users/Darbinis/Desktop/TESTAS.FDB", "SYSDBA", "testas");
		sql();
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
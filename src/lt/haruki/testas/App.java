package lt.haruki.testas;

public class App {

	/*== VARIABLES ===========================*/
	private static Sql sql;
	/*========================================*/
	
	public static void main(String args[]) throws Exception {
		
		sql = new Sql("KasaiSQL", "localhost", "UTF8", "C:/Users/Darbinis/Desktop/TESTAS.FDB", "SYSDBA", "testas", "cars");
		
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
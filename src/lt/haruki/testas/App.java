package lt.haruki.testas;

public class App {

	/*== CONSTANTS ===========================*/
	public static final String TITLE = "KasaiSQL";
	public static final String VERSION = "SNAPSHOT-0.0.2";
	/*== VARIABLES ===========================*/
	private static Sql sql;
	/*========================================*/
	
	public static void main(String args[]) throws Exception {
		
		sql = new Sql(TITLE + "-" + VERSION, "localhost", "UTF8", "C:/Users/Darbinis/Desktop/TESTAS.FDB", "SYSDBA", "testas", "cars");
		
		try {
			sql.PrintDriverVersion();
			sql.InitializeSqlConnection();
			sql.DisableAutoCommit();
			sql.ShowTables();
			/*==QUERIES====================================================*/
			sql.DoQuery("SELECT a.car_id, a.car_model, a.garage_id, b.diena, b.status"
					+ " FROM CARS a LEFT OUTER JOIN days b USING (car_id)");
			sql.DoQuery("SELECT car_id, count(case a.status when 'D' then 1 else null end)"
					+ " FROM days a GROUP BY car_id");
			sql.DoQuery("SELECT count(case a.status when 'I' then 1 else null end)"
					+ " FROM days a GROUP BY car_id");
			/*=============================================================*/
		} catch(Exception e) {
			System.out.println("Something gone terribly wrong!");
		} finally {
			sql.endConnection();
		}
		
	}
}
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
			sql.DoQuery("SELECT a.car_id, a.car_model, a.garage_id, b.diena, b.status"
					+ " FROM CARS a LEFT OUTER JOIN days b USING (car_id)");
			sql.DoQuery("SELECT count(case a.status when 'D' then 1 else null end)"
					+ " FROM days a WHERE car_id = 'EEA 112'");
			sql.DoQuery("SELECT count(case a.status when 'I' then 1 else null end)"
					+ " FROM days a WHERE car_id = 'EEA 112'");
			/*=============================================================*/
		} catch(Exception e) {
			System.out.println("Something gone terribly wrong!");
		} finally {
			sql.endConnection();
		}
		
	}
}
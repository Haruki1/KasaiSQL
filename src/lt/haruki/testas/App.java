package lt.haruki.testas;

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
	
	public static NewConnectionDialog cDialog;
	public Handler handler;
	
	/*========================================*/
	
	public static void main(String args[]) {
		
		cDialog = new NewConnectionDialog();
		AssignVariables(cDialog);
		
	}
	
	
	private static void AssignVariables(NewConnectionDialog cDialog) {
		hostname = cDialog.getHostnameField().getText();
		encoding = cDialog.getEncodingField().getText();
		database = cDialog.getDatabaseField().getText();
		user = cDialog.getUserField().getText();
		password = cDialog.getPasswordField().getText();
	}
	
	public static void ResetVariables() {
		hostname = encoding = database = user = password = "";
	}
	
	protected static void UseSql() {
		sql = new Sql(TITLE + "-" + VERSION, hostname, encoding, database, user, password);
		
		sql.PrintDriverVersion();
		sql.InitializeSqlConnection();
		sql.DisableAutoCommit();
		sql.ShowTables();
		/*==QUERIES====================================================*/
		sql.DoQuery("SELECT a.car_id, a.car_model, a.garage_id, b.diena, b.status FROM CARS a LEFT OUTER JOIN days b USING (car_id)");
		/*=============================================================*/
		sql.endConnection();
	}
	
}
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
		AssignVariables();
		
	}
	
	
	public static void AssignVariables() {
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
		
		sql.DoQuery("SELECT a.car_id, a.car_model, a.garage_id,\r\n" + 
				"sum(case when b.STATUS = 'D' then 1 else 0 end) as Dirbta,\r\n" + 
				"sum(case when b.STATUS = 'TA' then 1 else 0 end) as TechnineApziura,\r\n" + 
				"sum(case when b.STATUS = 'R' then 1 else 0 end) as Remontas,\r\n" + 
				"sum(case when b.STATUS = 'RZ' then 1 else 0 end) as Rezervuota,\r\n" + 
				"sum(case when b.STATUS = 'BV' then 1 else 0 end) as BeVairuotojo,\r\n" + 
				"sum(case when b.STATUS = 'I' then 1 else 0 end) as Iseigine,\r\n" + 
				"sum(case when b.STATUS = 'LR' then 1 else 0 end) as LaukiaRemonto\r\n" + 
				"FROM CARS a left outer JOIN days b\r\n" + 
				"on b.car_id = a.car_id\r\n" + 
				"group by a.car_id, a.car_model, a.garage_id");
		
		/*
		for(int i = 0; i < carid.length; i++) {
			sql.DoQuery("INSERT INTO CARS (CAR_ID, CAR_MODEL, GARAGE_ID) VALUES ('"+ carid[i] +"', '"+ carmodel[i] +"', '"+ garageid[i] +"');");
		}
		
		for(int j = 0; j < status.length; j++) {
			for(int i = 0; i < status[0].length; i++) {
				sql.DoQuery("INSERT INTO DAYS (CAR_ID, DIENA, STATUS) VALUES ('"+carid[j]+"','"+(i+1)+".03.2017','"+status[j][i]+"');");
			}
		}
		*/
		
		/*=============================================================*/
		sql.endConnection();
	}
	
}
package lt.haruki.testas;

import java.sql.*;

public class App {
	
	/*== CONNECTION SETTINGS ==================================================*/
	public final static String DB_DRIVER_CLASS = "org.firebirdsql.jdbc.FBDriver";
	public final static String DB_DRIVER_NAME = "jdbc:firebirdsql";
	public final static String DB_HOSTNAME = "localhost";
	public final static String DB_ENCODING = "utf8";
	public final static String DB_NAME = "C:/Users/Darbinis/Desktop/testas.fdb";
	public final static String DB_USER = "SYSDBA";
	public final static String DB_PASSWORD = "testas";
	public final static String TABLE_NAME = "cars";
	/*=========================================================================*/
	
	public final static String DB_URL = DB_DRIVER_NAME + ":" + DB_HOSTNAME + ":" + DB_NAME + "?lc_ctype=" + DB_ENCODING;

	/*== VARIABLES ===========================*/
	private static Driver d = null;
	private static Connection c = null;
	private static DatabaseMetaData dbMetaData;
	private static Statement s = null;
	private static ResultSetMetaData rsm = null;
	private static ResultSet rs = null;
	private static Window window = null;
	/*========================================*/
	private static Object[][] content;
	private static Object[] header;
	private static int tWidth = 0;
	private static int tHeight = 0;
	private static int j = 0;
	private static String query = "";
	
	
	public static void main (String args[]) throws Exception {
		
		window = new Window(640, 480, "Kasai SQL-SNAPSHOT 0.0.1");
		sql();
		window.drawTable(content, header);
	}
	
	public static void sql() {
		try {
			RegisterSqlDriver();
			PrintDriverVersion();
			InitializeSqlConnection();
			DisableAutoCommit();
			try {
				dbMetaData = c.getMetaData ();

				if (dbMetaData.supportsTransactions ())
					System.out.println ("Transactions are supported.");
				else
					System.out.println ("Transactions are not supported.");

				ResultSet tables = dbMetaData.getTables (null, null, "%", new String[] {"TABLE"});
				System.out.print("Existing tables: ");
				window.setLabel("Existing tables: ");
				while (tables.next()) {
					System.out.print(tables.getString("TABLE_NAME") + " ");
					window.extendLabel(tables.getString("TABLE_NAME") + " ");
				}
				System.out.println();
				tables.close ();
			} catch (SQLException e) {
				System.out.println ("Unable to extract database meta data.");
				ShowSQLException(e);
			}
			/*==QUERIES====================================================*/
			DoQuery("SELECT * FROM " + TABLE_NAME);
		} finally {
			System.out.println ("Closing database resources and rolling back any changes we made to the database.");

			try {if (rs!=null) rs.close ();} catch(SQLException e) {ShowSQLException(e);}
			
			try {if (s!=null) s.close ();} catch(SQLException e) {ShowSQLException(e);}

			try {if (c!=null) c.rollback ();} catch(SQLException e) {ShowSQLException(e);}
			
			try {if (c!=null) c.close ();} catch(SQLException e) {ShowSQLException(e);}
		}
	}
	
	private static void DoQuery(String query) {
		try {
			s = c.createStatement ();
			rs = s.executeQuery(query);
			rsm = rs.getMetaData();
			tWidth = rsm.getColumnCount();
			while(rs.next()) {
				tHeight++;
			}
			content = new Object[tHeight][tWidth];
			header = new Object[tWidth];
			rs = dbMetaData.getColumns(null, null, TABLE_NAME, "%");
			for (int i = 0; i < tWidth; i++) {
				header[i] = rsm.getColumnName(i+1);
			}
			rs = s.executeQuery(query);
			System.out.println("-------------------------------------------------------------");
			System.out.format("| %12s | %12s | %12s | %12s |\n", header);
			System.out.println("-------------------------------------------------------------");
			while(rs.next()) {
				for(int i = 0; i < tWidth; i++) {
					content[j][i] = rs.getString(rsm.getColumnName(i+1));
					System.out.format("| %12s ",rs .getString(rsm.getColumnName(i+1)));
				}
				j++;
				System.out.print("|\n");
			}
			System.out.println("-------------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println ("Unable to execute query!");
			ShowSQLException(e);
		}
	}

	private static void RegisterSqlDriver() {
		try {
			Class.forName (DB_DRIVER_CLASS);
		} catch (java.lang.ClassNotFoundException e) {
			System.out.println ("Firebird JCA-JDBC driver was not found!");
			System.out.println (e.getMessage());
			return;
		}
	}
	
	private static void PrintDriverVersion() {
		try {
			d = DriverManager.getDriver (DB_URL);
			System.out.println ("Firebird JCA-JDBC driver version " +
							d.getMajorVersion () +
							"." +
							d.getMinorVersion () +
							" registered with driver manager.");
		} catch (SQLException e) {
			System.out.println ("Unable to find Firebird JCA-JDBC driver among the registered drivers.");
			ShowSQLException (e);
			return;
		}
	}
	
	private static void InitializeSqlConnection() {
		try {
			c = DriverManager.getConnection (DB_URL, DB_USER, DB_PASSWORD);
			System.out.println ("Connection established.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println ("Unable to establish a connection through the driver manager.");
			ShowSQLException(e);
			return;
		}
	}
	
	private static void DisableAutoCommit() {
		try {
			c.setAutoCommit (false);
			System.out.println ("Auto-commit is disabled.");
		} catch (SQLException e) {
			System.out.println ("Unable to disable autocommit.");
			ShowSQLException(e);
			return;
		}
	}
	
	private static void ShowSQLException(java.sql.SQLException e) {
		SQLException next = e;
		while (next != null) {
			System.out.println (next.getMessage ());
			System.out.println ("Error Code: " + next.getErrorCode ());
			System.out.println ("SQL State: " + next.getSQLState ());
			next = next.getNextException();
		}
	}
	
}
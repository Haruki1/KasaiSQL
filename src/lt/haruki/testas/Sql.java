package lt.haruki.testas;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Sql {
	
	/*== CONNECTION CONSTANTS ==================================================*/
	public final String DB_DRIVER_CLASS = "org.firebirdsql.jdbc.FBDriver";
	public final String DB_DRIVER_NAME = "jdbc:firebirdsql";
	public final String DB_HOSTNAME;
	public final String DB_ENCODING;
	public final String DB_NAME;
	public final String DB_USER;
	public final String DB_PASSWORD;
	public final String DB_TABLE_NAME;
	
	public final String DB_URL;
	/*=========================================================================*/
	
	private Driver d = null;
	private Connection c = null;
	private DatabaseMetaData dbMetaData;
	private Statement s = null;
	private ResultSetMetaData rsm = null;
	private ResultSet rs = null;
	
	private Window window;
	
	public Object[][] content;
	public Object[] header;
	private int tWidth = 0;
	private int tHeight = 0;
	private int j = 0;
	String firstWord;
	
	public Sql(String title, final String DB_HOSTNAME, final String DB_ENCODING, final String DB_NAME, final String DB_USER, final String DB_PASSWORD, final String DB_TABLE_NAME) {
		RegisterSqlDriver();
		window = new Window(1024, 768, title);
		window.AddElementsToWindow();
		this.DB_HOSTNAME = DB_HOSTNAME;
		this.DB_ENCODING = DB_ENCODING;
		this.DB_NAME = DB_NAME;
		this.DB_USER = DB_USER;
		this.DB_PASSWORD = DB_PASSWORD;
		this.DB_TABLE_NAME = DB_TABLE_NAME;
		DB_URL = DB_DRIVER_NAME + ":" + DB_HOSTNAME + ":" + DB_NAME + "?lc_ctype=" + DB_ENCODING;
	}
	
	public Object[] getHeader() {
		if(header == null)
			return null;
		return header;
	}
	
	public Object[] getContent() {
		if(content == null)
			return null;
		return content;
	}
	
	public void DoQuery(String query) {
		
		firstWord = query.substring(0, query.indexOf(" "));
		
		if(firstWord.equalsIgnoreCase("select")) {
			try {
				s = c.createStatement();
				rs = s.executeQuery(query);
				rsm = rs.getMetaData();
				tWidth = rsm.getColumnCount();
				while(rs.next()) {
					tHeight++;
				}
				content = new Object[tHeight][tWidth];
				header = new Object[tWidth];
				for (int i = 0; i < tWidth; i++) {
					header[i] = rsm.getColumnName(i+1);
				}
				rs = s.executeQuery(query);
				for(int i = 0; i < tWidth; i++)
					System.out.print("-------------");
				System.out.println("-");
				for(Object s: header) {
					System.out.format("|%12s", s);
				}
				System.out.println("|");
				for(int i = 0; i < tWidth; i++)
					System.out.print("-------------");
				System.out.println("-");
				while(rs.next()) {
					for(int i = 0; i < tWidth; i++) {
						content[j][i] = rs.getString(rsm.getColumnName(i+1));
						System.out.format("|%12s",rs .getString(rsm.getColumnName(i+1)));
					}
					j++;
					System.out.print("|\n");
				}
				for(int i = 0; i < tWidth; i++)
					System.out.print("-------------");
				System.out.println("-");
			} catch (SQLException e) {
				System.out.println ("Unable to execute query! Wrong syntax?");
				ShowSQLException(e);
			}
			window.drawTable(content, header);
			tWidth = 0; tHeight = 0; j = 0;
			content = null; header = null;
		} else if(firstWord.equalsIgnoreCase("update") || firstWord.equalsIgnoreCase("insert")) {
			try {
				s = c.createStatement();
				rs = s.executeQuery(query);
			} catch(SQLException e) {
				System.out.println("Unable to update records!");
				ShowSQLException(e);
			}
		} else {
			//NOTHING!
		}
		
		
	}

	public void RegisterSqlDriver() {
		try {
			Class.forName (DB_DRIVER_CLASS);
		} catch (java.lang.ClassNotFoundException e) {
			System.out.println ("Firebird JCA-JDBC driver was not found!");
			System.out.println (e.getMessage());
			return;
		}
	}
	
	public void PrintDriverVersion() {
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
	
	public void InitializeSqlConnection() {
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
	
	public void DisableAutoCommit() {
		try {
			c.setAutoCommit (false);
			System.out.println ("Auto-commit has been disabled.");
		} catch (SQLException e) {
			System.out.println ("Unable to disable autocommit.");
			ShowSQLException(e);
			return;
		}
	}
	
	public void ShowSQLException(java.sql.SQLException e) {
		SQLException next = e;
		while (next != null) {
			System.out.println (next.getMessage ());
			System.out.println ("Error Code: " + next.getErrorCode ());
			System.out.println ("SQL State: " + next.getSQLState ());
			next = next.getNextException();
		}
	}
	
	public void ShowTables() {
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
	}
	
	public void endConnection() {
		System.out.println ("Closing database resources and rolling back any changes we made to the database.");

		try {if (rs!=null) rs.close ();} catch(SQLException e) {}
		
		try {if (s!=null) s.close ();} catch(SQLException e) {}

		try {if (c!=null) c.rollback ();} catch(SQLException e) {}
		
		try {if (c!=null) c.close ();} catch(SQLException e) {}
	}
}

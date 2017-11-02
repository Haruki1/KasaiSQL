package lt.haruki.testas;

public final class Handler {
	
	public Window window;
	public NewConnectionDialog connectionDialog;
	public Sql sql;
	
	public Handler(Window window, NewConnectionDialog connectionDialog, Sql sql) {
		this.window = window;
		this.connectionDialog = connectionDialog;
		this.sql = sql;
	}
	
}

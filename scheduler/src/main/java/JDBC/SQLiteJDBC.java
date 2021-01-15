package JDBC;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;

public class SQLiteJDBC {
	public static Connection ConnectToDB() {
		  Connection c = null;
		  try {
			  Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:/Users/zhengxiaoye/git/java_scheduler/scheduler/db/schedule.db");
		      c.setAutoCommit(false);
		      System.out.println("[Server-database] Open database successfully");
		  }catch ( Exception e ) {
			  e.printStackTrace();
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		  }
		  return c;
	  }
}

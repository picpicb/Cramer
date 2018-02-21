package Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

		 private static final String URL = "jdbc:mysql://localhost/bdd_sdzee"; //192.168.1.25 //picpicb.ddns.net:5000
		 private static final String USER = "root"; //bddcramer / prodCramer
		 private static final String PASSWORD = "root"; //cramer
		 private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
		 private static Connection connect;
		 static {
			 try {
				 Class.forName(DRIVER_NAME).newInstance();
				 System.out.println("* Driver loaded.");
			 }
			 catch (ClassNotFoundException e) {
				 System.err.println("* ERROR: Driver " + DRIVER_NAME + "not found");
			 }
			 catch (InstantiationException e) {
				 System.err.println("* ERROR: Impossible to create aninstance of " + DRIVER_NAME);
				 System.err.println(e.getMessage());
			 }
			 catch (IllegalAccessException e) {
				 System.err.println("* ERROR: Impossible to create aninstance of " + DRIVER_NAME);
				 System.err.println(e.getMessage());
			 }
		 }
		 
		
		public static Connection getConnection(){
			if(connect == null){
				try {
					connect = DriverManager.getConnection(URL, USER, PASSWORD);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
			return connect;	
		}
	}



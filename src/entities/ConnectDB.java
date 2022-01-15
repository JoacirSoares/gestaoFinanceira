package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectDB {
  
	static Connection connection = null;
	static String databaseName = "";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	
	static String username = "root";
	static String password = "janetenuma2002";
	
	
	public static void main(String[]args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Object newInstance = Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection =  DriverManager.getConnection(url, username, password);
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `mydb`.`conta` (``name`) VALUES('Joacir');");
		
		int status = ps.executeUpdate();
		
		if(status != 0) {
			System.out.println("DATABASE was Connection");
			System.out.println("Record WAS inserted");
		}
	}
}

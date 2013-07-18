package org.nic.lotto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.nic.lotto.model.LottoNumberSet;
import org.nic.lotto.model.User;


public class DB_ConnectionHelper {
	
	
	private static Connection conn = null;
	 
	  // Hostname
	  private static String dbHost = "127.0.0.1";
	 
	  // Port -- Standard: 3306
	  private static String dbPort = "3306";
	 
	  // Datenbankname
	  private static String database = "lotto_db";
	 
	  // Datenbankuser
	  private static String dbUser = "root";
	 
	  // Datenbankpasswort
	  private static String dbPassword = ""; 
	  
//	  private static Object[][] lottoTable; 
	  
	 
	  private DB_ConnectionHelper() {
	    try {
	 
	      // Datenbanktreiber für ODBC Schnittstellen laden.
	      // Für verschiedene ODBC-Datenbanken muss dieser Treiber
	      // nur einmal geladen werden.
	      Class.forName("com.mysql.jdbc.Driver");
	 
	      // Verbindung zur ODBC-Datenbank 'pong_db' herstellen.
	      // Es wird die JDBC-ODBC-Brücke verwendet.
	      conn = DriverManager.getConnection(	"jdbc:mysql://" + dbHost + ":"
	    		  								+ dbPort + "/" + database + "?" + "user=" + dbUser + "&"
	    		  								+ "password=" + dbPassword);
	    } catch (ClassNotFoundException e) {
	      System.out.println("Treiber nicht gefunden");
	    } catch (SQLException e) {
	      System.out.println("Connect nicht moeglich");
	    }
	  }
	 
	  // Singleton Design-Pattern für das Connection-Handling
	  private static Connection getInstance()
	  {
	    if(conn == null)
	    	new DB_ConnectionHelper();
	    	return conn;
	  }

	  public static ObservableList<LottoNumberSet> getLottoZiehungen()
	  {
	    conn = getInstance();
	 
	    ObservableList<LottoNumberSet> lottoResultList = FXCollections.observableArrayList();
	    
	    if(conn != null)
	    {
	      // Anfrage-Statement erzeugen.
	      Statement query;
 
	      try {
	        query = conn.createStatement();
	        
//	        int rowCount;
	 
	        // Ergebnistabelle erzeugen und abholen.
	        String sql = "SELECT date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl FROM ziehungen "
	            + "ORDER BY ID ";
	        ResultSet result = query.executeQuery(sql);
	        
	        // Anzahl der Zeilen aus dem ResultSet auslesen
//	        result.last();
//	        rowCount = result.getRow();
//	        result.beforeFirst();
	        
	        
	        // Ergebnissätze durchfahren.
	        int index = 0;
	        while (result.next()) {

	        	String date = result.getString("date");
	        	int[] numbers = new int[6];
	        	numbers[0] = result.getInt("zahl_1");
	        	numbers[1] = result.getInt("zahl_2");
	        	numbers[2] = result.getInt("zahl_3");
	        	numbers[3] = result.getInt("zahl_4");
	        	numbers[4] = result.getInt("zahl_5");
	        	numbers[5] = result.getInt("zahl_6");
	        	int szahl = result.getInt("szahl");

	        	LottoNumberSet lottoSet = new LottoNumberSet();
	        	
	        	lottoSet.setDate(date);
	        	lottoSet.setNumbers(numbers);
	        	lottoSet.setSzahl(szahl);
	    
	        	lottoResultList.add(lottoSet);

	        	index++;
	        }
	        System.out.println("Anzahl Einträge: " + index);
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	    }
	    
	    return lottoResultList;
	  }
	  
	  public static ObservableList<User> getUsers()
	  {
	    conn = getInstance();
	 
	    ObservableList<User> usersResultList = FXCollections.observableArrayList();
	    
	    if(conn != null)
	    {
	      // Anfrage-Statement erzeugen.
	      Statement query;
 
	      try {
	        query = conn.createStatement();
	        
//	        int rowCount;
	 
	        // Ergebnistabelle erzeugen und abholen.
	        String sql = "SELECT name, money FROM benutzer "
	            + "ORDER BY ID ";
	        ResultSet result = query.executeQuery(sql);
	        
	        // Ergebnissätze durchfahren.
	        int index = 0;
	        while (result.next()) {

	        	String name = result.getString(1);
	        	double value = result.getDouble(2);

	        	User user = new User(name);
	        	
	        	user.setTipps(getLottoTippsForUser(name));
	        	user.setMoney(value);
	        	
	        	usersResultList.add(user);

	        	index++;
	        }
	        System.out.println("Anzahl Einträge: " + index);
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	    }
	    
	    return usersResultList;
	  }
	  
	  
	  public static ObservableList<LottoNumberSet> getLottoTippsForUser(final String name)
	  {
		  conn = getInstance();
			 
		    ObservableList<LottoNumberSet> lottoResultList = FXCollections.observableArrayList();
		    
		    if(conn != null)
		    {
		      // Anfrage-Statement erzeugen.
		      Statement query;
	 
		      try {
		        query = conn.createStatement();
		        

		 
		        // Ergebnistabelle erzeugen und abholen.
		        String sql = "SELECT date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl FROM tipps JOIN benutzer ON tipps.ID = benutzer.ID "
		            +   "WHERE benutzer.name = \"" + name +"\"" 
		        	+	" ORDER BY date DESC";
		        ResultSet result = query.executeQuery(sql);
		               
		        
		        // Ergebnissätze durchfahren.
		        int index = 0;
		        while (result.next()) {

		        	String date = result.getString("date");
		        	int[] numbers = new int[6];
		        	numbers[0] = result.getInt("zahl_1");
		        	numbers[1] = result.getInt("zahl_2");
		        	numbers[2] = result.getInt("zahl_3");
		        	numbers[3] = result.getInt("zahl_4");
		        	numbers[4] = result.getInt("zahl_5");
		        	numbers[5] = result.getInt("zahl_6");
		        	int szahl = result.getInt("szahl");

		        	LottoNumberSet lottoSet = new LottoNumberSet();
		        	
		        	lottoSet.setDate(date);
		        	lottoSet.setNumbers(numbers);
		        	lottoSet.setSzahl(szahl);
		    
		        	lottoResultList.add(lottoSet);

		        	index++;
		        }
		        System.out.println("Anzahl Einträge: " + index);
		      } catch (SQLException e) {
		    	  e.printStackTrace();
		      }
		    }
		    
		    return lottoResultList;
	  }
	  
	  public static ObservableList<String> getTippEntrysForUser(final String name)
	  {
		  conn = getInstance();
			 
		    ObservableList<String> lottoEntryList = FXCollections.observableArrayList();
		    
		    if(conn != null)
		    {
		      // Anfrage-Statement erzeugen.
		      Statement query;
	 
		      try {
		        query = conn.createStatement();
		        

		 
		        // Ergebnistabelle erzeugen und abholen.
		        String sql = "SELECT zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl, matches FROM tipps JOIN benutzer ON tipps.ID = benutzer.ID "
		            +   "WHERE benutzer.name = \"" + name +"\"" 
		        	+	" ORDER BY date DESC";
		        ResultSet result = query.executeQuery(sql);
		               
		        
		        // Ergebnissätze durchfahren.
		        int index = 0;
		        while (result.next()) {

		        	int[] numbers = new int[6];
		        	numbers[0] = result.getInt("zahl_1");
		        	numbers[1] = result.getInt("zahl_2");
		        	numbers[2] = result.getInt("zahl_3");
		        	numbers[3] = result.getInt("zahl_4");
		        	numbers[4] = result.getInt("zahl_5");
		        	numbers[5] = result.getInt("zahl_6");
		        	int szahl = result.getInt("szahl");
		        	String matches = result.getString("matches");

		        	String sep = ", ";
		        	StringBuilder sb = new StringBuilder();
		        	sb.append(numbers[0]).append(sep).append(numbers[1]).
		        		append(sep).append(numbers[2]).append(sep).
		        		append(numbers[3]).append(sep).append(numbers[4]).
		        		append(sep).append(numbers[5]).append(sep).append(szahl);
		        	
		        	if(matches!=""||matches!=null)
		        	{
		        		sb.append(" | ").append(matches);
		        	}
		    
		        	lottoEntryList.add(sb.toString());

		        	index++;
		        }
		        System.out.println("Anzahl Einträge: " + index);
		      } catch (SQLException e) {
		    	  e.printStackTrace();
		      }
		    }
		    
		    return lottoEntryList;
	  }
	  
	  
	 
	  /**
	   * Fügt einen neuen Datensatz hinzu
	   */
	  public static void insertNumbersIntoZiehungen(final String date, final int zahl_1, 
			  final int zahl_2, final int zahl_3, final int zahl_4, final int zahl_5, 
			  final int zahl_6, final int szahl)
	  {
	    conn = getInstance();
	 
	    if(conn != null)
	    {
	      try {
	 
	        // Insert-Statement erzeugen (Fragezeichen werden später ersetzt).
	        String sql = "INSERT INTO ziehungen(date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl) " +
	                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT IGNORE";
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setString(1, date);
	        preparedStatement.setInt(2, zahl_1);
	        preparedStatement.setInt(3, zahl_2);
	        preparedStatement.setInt(4, zahl_3);
	        preparedStatement.setInt(5, zahl_4);
	        preparedStatement.setInt(6, zahl_5);
	        preparedStatement.setInt(7, zahl_6);
	        preparedStatement.setInt(8, szahl);
	        // SQL ausführen.
	        preparedStatement.executeUpdate();
	 
	        // Es wird der letzte Datensatz abgefragt
	        String lastScore = "SELECT date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl " +
	                           "FROM ziehungen " +
	                           "ORDER BY date DESC LIMIT 1";
	        ResultSet result = preparedStatement.executeQuery(lastScore);
	 
	        // Wenn ein Datensatz gefunden wurde, wird auf diesen zugegriffen
	        if(result.next())
	        {
	        	System.out.println(result.getString(1) + " " +
	        			result.getInt(2) + " " +
	        			result.getInt(3) + " " +
	        			result.getInt(4) + " " +
	        			result.getInt(5) + " " +
	        			result.getInt(6) + " " +
	        			result.getInt(7) + " " +
	        			result.getInt(8));
	        }
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	    }
	  }
	  
	  /**
	   * Fügt einen neuen Datensatz hinzu
	   */
	  public static void insertNewUser(final User user)
	  {
	    conn = getInstance();
	 
	    if(conn != null)
	    {
	      try {
	 
	        // Insert-Statement erzeugen (Fragezeichen werden später ersetzt).
	        String sql = "INSERT INTO benutzer(name, money) " +
	                     "VALUES(?, ?)";
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setString(1, user.getName());
	        preparedStatement.setDouble(2, user.getMoney());

	        // SQL ausführen.
	        preparedStatement.executeUpdate();
	 
	        // Es wird der letzte Datensatz abgefragt
	        String lastUser = "SELECT ID, name, money " +
	                           "FROM benutzer " +
	                           "ORDER BY ID DESC LIMIT 1";
	        ResultSet result = preparedStatement.executeQuery(lastUser);
	 
	        // Wenn ein Datensatz gefunden wurde, wird auf diesen zugegriffen
	        if(result.next())
	        {
	        	System.out.println(result.getInt(1) + " " +
	        			result.getString(2) + " " + result.getDouble(3)
	        			);
	        }
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	    }
	  }
	  
	  /**
	   * Fügt einen neuen Datensatz hinzu
	   */
	  public static void insertNumbersIntoTipps(String actualUser, final String date, final int zahl_1, 
			  final int zahl_2, final int zahl_3, final int zahl_4, final int zahl_5,
			  final int zahl_6, final int szahl, final String matches)
	  {
	    conn = getInstance();
	 
	    if(conn != null)
	    {
	      try {
	    	actualUser = "\"" + actualUser + "\"";
	    	String idStr = "SELECT ID FROM benutzer WHERE name = " + actualUser;
	    	PreparedStatement idStatement = conn.prepareStatement(idStr);
	    	ResultSet idResult = idStatement.executeQuery(idStr);
	    	
	    	int id = 0;
	    	if(idResult.next())
	    	{
	    		id = idResult.getInt(1);
	    	}
	    	
	        // Insert-Statement erzeugen (Fragezeichen werden später ersetzt).
	        String sql = "INSERT INTO tipps(ID, date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl, matches) " +
	                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, id);
	        preparedStatement.setString(2, date);
	        preparedStatement.setInt(3, zahl_1);
	        preparedStatement.setInt(4, zahl_2);
	        preparedStatement.setInt(5, zahl_3);
	        preparedStatement.setInt(6, zahl_4);
	        preparedStatement.setInt(7, zahl_5);
	        preparedStatement.setInt(8, zahl_6);
	        preparedStatement.setInt(9, szahl);
	        preparedStatement.setString(10, matches);
	        // SQL ausführen.
	        preparedStatement.executeUpdate();
	 
	        // Es wird der letzte Datensatz abgefragt
	        String lastScore = "SELECT date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl, matches " +
	                           "FROM tipps " +
	                           "ORDER BY date DESC LIMIT 1";
	        ResultSet result = preparedStatement.executeQuery(lastScore);
	 
	        // Wenn ein Datensatz gefunden wurde, wird auf diesen zugegriffen
	        if(result.next())
	        {
	        	System.out.println(result.getString(1) + " " +
	        			result.getInt(2) + " " +
	        			result.getInt(3) + " " +
	        			result.getInt(4) + " " +
	        			result.getInt(5) + " " +
	        			result.getInt(6) + " " +
	        			result.getInt(7) + " " +
	        			result.getInt(8) + " " +
	        			result.getString(9));
	        }
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	    }
	  }
	  
	  public static void updateUserMoney(final String userName, final double newValue)
	  {
		  conn = getInstance();
		  
		  if(conn!=null)
		  {
			  try {
				String sql = "UPDATE benutzer SET money = " + newValue + " WHERE name = \"" + userName + "\" ";
				
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
//				preparedStatement.setDouble(1, newValue);
//				preparedStatement.setString(2, userName);
//				
				preparedStatement.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  }
	  }

	  public static void deleteUser()
	  {
//		   TODO implement delete user statement
	  }
	  
	  public static void deleteUserTipp()
	  {
//		  TODO implement delete user tipps statement
	  }
	  
}



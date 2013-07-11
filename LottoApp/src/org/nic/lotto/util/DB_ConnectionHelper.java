package org.nic.lotto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
	  
	  private static Object[][] lottoTable; 
	  
	 
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
	 
//	  /**
//	   * Schreibt die Namensliste in die Konsole
//	   */
//	  public static void printScoreList()
//	  {
//	    conn = getInstance();
//	 
//	    if(conn != null)
//	    {
//	      // Anfrage-Statement erzeugen.
//	      Statement query;
//	      try {
//	        query = conn.createStatement();
//	 
//	        // Ergebnistabelle erzeugen und abholen.
//	        String sql = "SELECT player_score, cpu_score, cpu_level FROM score_db "
//	            + "ORDER BY timestamp";
//	        ResultSet result = query.executeQuery(sql);
//	
//	        // Ergebnissätze durchfahren.
//	        while (result.next()) {
//	          int player_score = result.getInt("player_score"); // Alternativ: result.getString(1);
//	          int cpu_score = result.getInt("cpu_score");
//	          String cpu_level = result.getString("cpu_level");// Alternativ: result.getString(2);
//	          String score = player_score + ":" + cpu_score + " Level: " + cpu_level;
//	          System.out.println(score);
//	        }
//	      } catch (SQLException e) {
//	        e.printStackTrace();
//	      }
//	    }
//	  }
//	  
//	  
	  public static Object[][] getLottoZiehungen()
	  {
	    conn = getInstance();
	 
	    if(conn != null)
	    {
	      // Anfrage-Statement erzeugen.
	      Statement query;
 
	      try {
	        query = conn.createStatement();
	        
	        int rowCount;
	 
	        // Ergebnistabelle erzeugen und abholen.
	        String sql = "SELECT date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl FROM ziehungen "
	            + "ORDER BY date DESC";
	        ResultSet result = query.executeQuery(sql);
	        
	        // Anzahl der Zeilen aus dem ResultSet auslesen
	        result.last();
	        rowCount = result.getRow();
	        result.beforeFirst();
	        
	        lottoTable = new Object[rowCount][8]; // Object-Container für die Datensätze der score_db

	        // Ergebnissätze durchfahren.
	        int index = 0;
	        while (result.next()) {

	        	String date = result.getString("date");
	        	int zahl_1 = result.getInt("zahl_1");
	        	int zahl_2 = result.getInt("zahl_2");
	        	int zahl_3 = result.getInt("zahl_3");
	        	int zahl_4 = result.getInt("zahl_4");
	        	int zahl_5 = result.getInt("zahl_5");
	        	int zahl_6 = result.getInt("zahl_6");
	        	int szahl = result.getInt("szahl");


	        	lottoTable[index][0] = date; // Object-Container befüllen
	        	lottoTable[index][1] = zahl_1;
	        	lottoTable[index][2] = zahl_2;
	        	lottoTable[index][3] = zahl_3;
	        	lottoTable[index][4] = zahl_4;
	        	lottoTable[index][5] = zahl_5;
	        	lottoTable[index][6] = zahl_6;
	        	lottoTable[index][7] = szahl;

	        	index++;
	        }
	        System.out.println("Anzahl Einträge: " + index);
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	    }
	    
	    return lottoTable;
	  }
	  
	  public static Object[][] getLottoTipps()
	  {
	    conn = getInstance();
	 
	    if(conn != null)
	    {
	      // Anfrage-Statement erzeugen.
	      Statement query;
 
	      try {
	        query = conn.createStatement();
	        
	        int rowCount;
	 
	        // Ergebnistabelle erzeugen und abholen.
	        String sql = "SELECT date, zahl_1, zahl_2, zahl_3, zahl_4, zahl_5, zahl_6, szahl FROM tipps "
	            + "ORDER BY date DESC";
	        ResultSet result = query.executeQuery(sql);
	        
	        // Anzahl der Zeilen aus dem ResultSet auslesen
	        result.last();
	        rowCount = result.getRow();
	        result.beforeFirst();
	        
	        lottoTable = new Object[rowCount][8]; // Object-Container für die Datensätze der score_db

	        // Ergebnissätze durchfahren.
	        int index = 0;
	        while (result.next()) {

	        	String date = result.getString("date");
	        	int zahl_1 = result.getInt("zahl_1");
	        	int zahl_2 = result.getInt("zahl_2");
	        	int zahl_3 = result.getInt("zahl_3");
	        	int zahl_4 = result.getInt("zahl_4");
	        	int zahl_5 = result.getInt("zahl_5");
	        	int zahl_6 = result.getInt("zahl_6");
	        	int szahl = result.getInt("szahl");


	        	lottoTable[index][0] = date; // Object-Container befüllen
	        	lottoTable[index][1] = zahl_1;
	        	lottoTable[index][2] = zahl_2;
	        	lottoTable[index][3] = zahl_3;
	        	lottoTable[index][4] = zahl_4;
	        	lottoTable[index][5] = zahl_5;
	        	lottoTable[index][6] = zahl_6;
	        	lottoTable[index][7] = szahl;

	        	index++;
	        }
	        System.out.println("Anzahl Einträge: " + index);
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	    }
	    
	    return lottoTable;
	  }
//	  
//	  
//	 
//	  /**
//	   * Fügt einen neuen Datensatz hinzu
//	   */
//	  public static void insertScore(int player_score, int cpu_score, String cpu_level)
//	  {
//	    conn = getInstance();
//	 
//	    if(conn != null)
//	    {
//	      try {
//	 
//	        // Insert-Statement erzeugen (Fragezeichen werden später ersetzt).
//	        String sql = "INSERT INTO score_db(player_score, cpu_score, cpu_level) " +
//	                     "VALUES(?, ?, ?)";
//	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//	        // Erstes Fragezeichen durch "player_score" Parameter ersetzen
//	        preparedStatement.setInt(1, player_score);
//	        // Zweites Fragezeichen durch "cpu_score" Parameter ersetzen
//	        preparedStatement.setInt(2, cpu_score);
//	        // Drittes Fragezeichen durch "cpu_level" Parameter ersetzen
//	        preparedStatement.setString(3, cpu_level);
//	        // SQL ausführen.
//	        preparedStatement.executeUpdate();
//	 
//	        // Es wird der letzte Datensatz abgefragt
//	        String lastScore = "SELECT player_score, cpu_score, cpu_level " +
//	                           "FROM score_db " +
//	                           "ORDER BY player_score DESC LIMIT 1";
//	        ResultSet result = preparedStatement.executeQuery(lastScore);
//	 
//	        // Wenn ein Datensatz gefunden wurde, wird auf diesen zugegriffen
//	        if(result.next())
//	        {
//	          System.out.println(result.getInt(1) + ":" +
//	              result.getInt(2) + " Level: " +
//	              result.getString(3));
//	        }
//	      } catch (SQLException e) {
//	        e.printStackTrace();
//	      }
//	    }
//	  }
//	 
//	  /**
//	   * Aktualisiert den Datensatz mit der übergebenen actorId
//	   */
//	  public static void updateScore(int player_score, int cpu_score, String cpu_level, int score_id)
//	  {
//	    conn = getInstance();
//	 
//	    if(conn != null)
//	    {
//	      try {
//	 
//	        String querySql = "SELECT player_score, cpu_score, cpu_level " +
//	                          "FROM score_db " +
//	                          "WHERE score_id = ?";
//	         
//	        // PreparedStatement erzeugen.
//	        PreparedStatement preparedQueryStatement = conn.prepareStatement(querySql);
//	        preparedQueryStatement.setInt(1, score_id);
//	        ResultSet result = preparedQueryStatement.executeQuery();
//	 
//	        if(result.next())
//	        {
//	          // Vorher
//	          System.out.println("VORHER: " + result.getInt(1) + ":" +
//	                                           result.getInt(2) + " Level: " +
//	                                           result.getString(3));
//	        }
//	 
//	        // Ergebnistabelle erzeugen und abholen.
//	        String updateSql = "UPDATE score_db " +
//	                           "SET player_score = ?, cpu_score = ?, cpu_level = ? " +
//	                           "WHERE score_id = ?";
//	        PreparedStatement preparedUpdateStatement = conn.prepareStatement(updateSql);
//	        // Erstes Fragezeichen durch "player_score" Parameter ersetzen
//	        preparedUpdateStatement.setInt(1, player_score);
//	        // Zweites Fragezeichen durch "cpu_score" Parameter ersetzen
//	        preparedUpdateStatement.setInt(2, cpu_score);
//	        // Drittes Fragezeichen durch "cpu_level" Parameter ersetzen
//	        preparedUpdateStatement.setString(3, cpu_level);
//	        // Viertes Fragezeichen durch "score_id" Parameter ersetzen
//	        preparedUpdateStatement.setInt(4, score_id);
//	        // SQL ausführen
//	        preparedUpdateStatement.executeUpdate();
//	         
//	        // Es wird der letzte Datensatz abgefragt
//	        result = preparedQueryStatement.executeQuery();
//	 
//	        if(result.next())
//	        {
//	          System.out.println("NACHHER: " + result.getInt(1) + ":" +
//	                                            result.getInt(2) + " Level: " +
//	                                            result.getString(3));
//	        }
//	 
//	      } catch (SQLException e) {
//	        e.printStackTrace();
//	      }
//	    }
//	  }
//	  
//	  // Einträge löschen
//	  public static void deleteScoreEntry(int score_id) 
//	  {
//		  
//		  conn = getInstance();
//			 
//		    if(conn != null)
//		    {
//		      try {
//		    	  String deleteSql =	"DELETE FROM score_db " + 
//		    			  				"WHERE score_id = ?";
//		    	  
//		    	  PreparedStatement preparedDeleteStatement = conn.prepareStatement(deleteSql);
//		    	  // Ersetzen des ? durch Parameter "score_id"
//		    	  preparedDeleteStatement.setInt(1, score_id);
//		    	  
//		    	  preparedDeleteStatement.executeUpdate();
//		    	  
//		    	  
//		      } catch (SQLException e) {
//		    	  e.printStackTrace();
//		      }
//		    }
//		    
//		  
//	  }
//	  
//	  
//	  
}



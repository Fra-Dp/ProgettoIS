package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    //variabili membro
    public static String urL="jdbc:mysql://localhost:3306/";
    public static String dbName="sistema_gestionale_di_task_didattici_con_badge";
    public static String driver="com.mysql.cj.jdbc.Driver";
    //Nome e password per l'accesso
    public static String userName="root";
    public static String password="5s(37kjU$71z";

    /**
     * Metodo che crea la connessione dal driver
     * @return connessione
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        //creo oggetto connection
        Connection conn=null;
        Class.forName(driver);
        //ottengo la connessione dal DriverManager passando come parametri le credenziali e il nome del db
        conn= DriverManager.getConnection(urL+dbName,userName,password);
        return conn;
    }

    /**
     * Metodo che chiude la connessione con il DB
     * @param c
     * @throws SQLException
     */
    public static void closeConnection(Connection c)throws SQLException{
        System.out.println("Chiudo connessione...");
        c.close();
    }

    /**
     * ResultSet - ritorna i risultati di una query
     * @param query
     * @return resultSet
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ResultSet selectQuery(String query)throws ClassNotFoundException, SQLException{
        //invoco getConnection
        Connection conn=getConnection();
        //Creo uno statement, che contiene un comando SQL
        Statement statement=conn.createStatement();
        //Creo un oggetto ResultSet che mantiene un cursore che punta alla riga corrente di dati
        ResultSet ret=statement.executeQuery(query);
        return ret;
    }

    /**
     * Query per inserimento ed update
     * @param query
     * @return esito
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int updateQuery(String query) throws ClassNotFoundException, SQLException{

        //Invoco getConnection
        Connection conn = getConnection(); //prendo la connesione

        //Creo uno statement - necessario per effettuare query
        java.sql.Statement  statement = conn.createStatement(); //creo statement

        //Effettuo la query
        int ret = statement.executeUpdate(query);

        //Chiudo la connessione
        closeConnection(conn);

        return ret;
    }
}
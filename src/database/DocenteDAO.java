package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Data Access Object (DAO) per la gestione dei dati relativi ai docenti nel database.
 * <p>
 * Questa classe fornisce metodi per:
 * -Caricare i dati di un docente tramite email istituzionale
 * -Inserire un nuovo docente nel database
 * -Recuperare l'elenco completo dei docenti registrati
 * <p>
 * Le operazioni avvengono attraverso query SQL sulla tabella {@code docente},
 * e utilizzano la classe di utilità {@link DBManager} per l'esecuzione.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class DocenteDAO {
    private String indirizzoEmailIstituzionale;
    private String nome;
    private String password;
    private String cognome;

    /**
     * Costruttore di default.
     */
    public DocenteDAO() {
        super();
    }

    /**
     * Carica i dati del docente dal database in base all'indirizzo email.
     *
     * @return 1 se il docente è stato trovato e caricato con successo,
     *         0 se il docente non esiste nel database,
     *        -1 in caso di errore durante l'accesso al database
     */
    public int caricaDaDB() {
        String query = "SELECT * FROM docente WHERE IndirizzoEmailIstituzionale = " + "'" + this.indirizzoEmailIstituzionale + "'";

        try{
            ResultSet rs = DBManager.selectQuery(query);

            if (rs.next()){
                this.setIndirizzoEmailIstituzionale(rs.getString("IndirizzoEmailIstituzionale"));
                this.setNome(rs.getString("Nome"));
                this.setCognome(rs.getString("Cognome"));
                this.setPassword(rs.getString("Password"));
                return  1;
            }else {
                return  0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return  -1;
        }
    }

    /**
     * Inserisce un nuovo docente nel database con i dati forniti.
     *
     * @param email    email istituzionale del docente
     * @param nome     nome del docente
     * @param cognome  cognome del docente
     * @param password password di accesso
     * @return numero di righe inserite (tipicamente 1), oppure {@code -1} in caso di errore
     */
    public int scriviSuDB(String email, String nome, String cognome, String password) {
        int ret;

        String query = "INSERT INTO docente(IndirizzoEmailIstituzionale, Nome, Cognome, Password) " +
                "VALUES ('" + email + "', '" + nome + "', '" + cognome +"', '"+ password +"')";
        try{
            ret = DBManager.updateQuery(query);

        }catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            ret = -1; //errore di scrittura
        }
        return ret;
    }

    /**
     * Recupera la lista completa di tutti i docenti presenti nel database.
     *
     * @return lista di oggetti {@code DocenteDAO} contenenti i dati dei docenti,
     *         oppure {@code null} in caso di errore
     */
    public ArrayList<DocenteDAO> getListaDocenti() {
        ArrayList<DocenteDAO> listaDocenti = new ArrayList<>();
        String query = "SELECT * FROM docente";
        try{
            ResultSet rs = DBManager.selectQuery(query);
            while (rs.next()){
                DocenteDAO docenteDAO = new DocenteDAO();
                docenteDAO.setIndirizzoEmailIstituzionale(rs.getString("IndirizzoEmailIstituzionale"));
                docenteDAO.setNome(rs.getString("Nome"));
                docenteDAO.setCognome(rs.getString("Cognome"));
                docenteDAO.setPassword(rs.getString("Password"));
                listaDocenti.add(docenteDAO);
            }
        } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
           return null;
        }
        return listaDocenti;
    }

    // Getter e Setter

    /**
     * Restituisce l'indirizzo email istituzionale del docente.
     *
     * @return l'indirizzo email
     */
    public String getIndirizzoEmailIstituzionale() {
        return indirizzoEmailIstituzionale;
    }

    /**
     * Imposta l'indirizzo email istituzionale del docente.
     *
     * @param indirizzoEmailIstituzionale l'email da impostare
     */
    public void setIndirizzoEmailIstituzionale(String indirizzoEmailIstituzionale) {
        this.indirizzoEmailIstituzionale = indirizzoEmailIstituzionale;
    }

    /**
     * Restituisce il nome del docente.
     *
     * @return il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del docente.
     *
     * @param nome il nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome del docente.
     *
     * @return il cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome del docente.
     *
     * @param cognome il cognome da impostare
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce la password del docente.
     *
     * @return la password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password del docente.
     *
     * @param password la password da impostare
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

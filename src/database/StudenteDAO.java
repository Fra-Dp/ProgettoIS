package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) per la gestione degli studenti nel database.
 * <p>
 * La classe {@code StudenteDAO} fornisce metodi per:
 * <p>
 *-Caricare i dati di uno studente tramite il suo indirizzo email
 *-Scrivere un nuovo studente nella tabella {@code studente}
 * -Recuperare l'elenco di tutti gli studenti presenti nel sistema
 * <p>
 * Ogni istanza rappresenta un singolo studente, con attributi personali e collegamento a una classe virtuale.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class StudenteDAO {
    private String indirizzoEmailIstituzionale;
    private String nome;
    private String cognome;
    private String password;
    private String classeVirtualeCodiceUnivoco;
    private ProfiloPersonaleDAO profiloPersonaleAssociato;

    /**
     * Costruttore di default.
     */
    public StudenteDAO() {
        super();
    }

    /**
     * Carica i dati dello studente dal database in base all'indirizzo email.
     *
     * @return 1 se il caricamento ha successo,
     *         0 se nessun risultato è stato trovato,
     *        -1 in caso di errore durante l'accesso al database
     */
    public int caricaDaDB() {
        String query = "SELECT * FROM studente WHERE IndirizzoEmailIstituzionale =" + "'" + this.indirizzoEmailIstituzionale + "'";

        try{
            ResultSet rs = DBManager.selectQuery(query);

            if (rs.next()) {
                this.setIndirizzoEmailIstituzionale(rs.getString("IndirizzoEmailIstituzionale"));
                this.setNome(rs.getString("Nome"));
                this.setCognome(rs.getString("Cognome"));
                this.setPassword(rs.getString("Password"));
                this.setClasseVirtualeCodiceUnivoco(rs.getString("classe_virtuale_CodiceUnivoco"));
                return  1; //SUCCESSO
            } else {
                return  0; // Nessuno Risultato
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return  -1; // Errore
        }
    }

    /**
     * Inserisce un nuovo studente nel database.
     *
     * @param email    indirizzo email istituzionale dello studente
     * @param nome     nome dello studente
     * @param cognome  cognome dello studente
     * @param password password dello studente
     * @return numero di righe modificate (tipicamente 1), oppure -1 in caso di errore
     */
    public int scriviSuDB(String email, String nome , String cognome, String password) {
        int ret;
        String classeValue = (this.classeVirtualeCodiceUnivoco == null)
                ? "NULL"
                : "'" + this.classeVirtualeCodiceUnivoco + "'";

        String query = "INSERT INTO studente(IndirizzoEmailIstituzionale, Nome, Cognome, Password, classe_virtuale_CodiceUnivoco) " +
                "VALUES ('" + email + "', '" + nome + "', '" + cognome.replace("'", "''") +"', '"+ password.replace("'", "''") + "', " + classeValue + ")";
        try{
            ret = DBManager.updateQuery(query);

        }catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            ret = -1; //errore di scrittura
        }
        return ret;
    }

    /**
     * Recupera l'elenco completo degli studenti registrati nel database.
     *
     * @return lista di oggetti {@code StudenteDAO}, oppure {@code null} in caso di errore
     */
    public List<StudenteDAO> getListaStudenti(){

        ArrayList<StudenteDAO> listaStudenti = new ArrayList<>();
        String query = "SELECT * FROM studente";
        try {
            ResultSet rs = DBManager.selectQuery(query);
            while (rs.next()) {
                StudenteDAO studenteDAO = new StudenteDAO();
                studenteDAO.setIndirizzoEmailIstituzionale(rs.getString("IndirizzoEmailIstituzionale"));
                studenteDAO.setNome(rs.getString("Nome"));
                studenteDAO.setCognome(rs.getString("Cognome"));
                studenteDAO.setPassword(rs.getString("Password"));
                studenteDAO.setClasseVirtualeCodiceUnivoco(rs.getString("classe_virtuale_CodiceUnivoco"));
                listaStudenti.add(studenteDAO);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return listaStudenti;
    }

    // Metodi getter e setter

    /**
     * Imposta l'indirizzo email istituzionale dello studente.
     *
     * @param indirizzoEmailIstituzionale l'email da impostare
     */
    public void setIndirizzoEmailIstituzionale(String indirizzoEmailIstituzionale) {
        this.indirizzoEmailIstituzionale = indirizzoEmailIstituzionale;
    }

    /**
     * Imposta il nome dello studente.
     *
     * @param nome il nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Imposta il cognome dello studente.
     *
     * @param cognome il cognome da impostare
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce la password dello studente.
     *
     * @return la password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dello studente.
     *
     * @param password la password da impostare
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Imposta il codice univoco della classe virtuale associata allo studente.
     *
     * @param classeVirtualeCodiceUnivoco codice univoco della classe
     */
    public void setClasseVirtualeCodiceUnivoco(String classeVirtualeCodiceUnivoco) {
        this.classeVirtualeCodiceUnivoco = classeVirtualeCodiceUnivoco;
    }

    /**
     * Restituisce il cognome dello studente.
     *
     * @return il cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Restituisce l'indirizzo email istituzionale dello studente.
     *
     * @return l'email istituzionale
     */
    public String getIndirizzoEmailIstituzionale() {
        return indirizzoEmailIstituzionale;
    }

    /**
     * Restituisce il nome dello studente.
     *
     * @return il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il codice univoco della classe virtuale associata allo studente.
     *
     * @return codice della classe virtuale
     */
    public String getClasseVirtualeCodiceUnivoco() {
        return classeVirtualeCodiceUnivoco;
    }

    public ProfiloPersonaleDAO getProfiloPersonaleAssociato() {
        return profiloPersonaleAssociato;
    }

    public void setProfiloPersonaleAssociato(ProfiloPersonaleDAO profiloPersonaleAssociato) {
        this.profiloPersonaleAssociato = profiloPersonaleAssociato;
    }
}

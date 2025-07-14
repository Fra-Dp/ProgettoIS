package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe DAO (Data Access Object) per la gestione dell'entità Classe Virtuale nel database.
 * <p>
 * Questa classe fornisce metodi per interrogare e manipolare i dati relativi alle
 * classi virtuali memorizzate nella tabella {@code classe_virtuale}, come il recupero
 * delle classi assegnate a un docente o il caricamento dei dettagli di una classe
 * specifica tramite codice univoco.
 * <p>
 * Le operazioni di accesso sono eseguite tramite query SQL su {@link DBManager} e
 * restituiscono oggetti {@code ClasseVirtualeDAO} come rappresentazione dei dati estratti
 * <p>
 *  Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class ClasseVirtualeDAO {
    private String codiceUnivoco;
    private String nome;

    /**
     * Costruttore vuoto. Utilizzato per creare un'istanza senza inizializzazione automatica dei dati.
     */
    public ClasseVirtualeDAO() {
        super();
    }

    /**
     * Carica i dati associati alla classe virtuale dal database utilizzando il {@code codiceUnivoco}.
     * <p>
     * Se la classe è presente nel database, vengono popolati i campi {@code codiceUnivoco} e {@code nome}.
     * In caso contrario o in caso di errore di connessione, viene restituito un codice di stato negativo.
     *
     * @return {@code 1} se i dati sono stati caricati con successo,
     *         {@code 0} se non è stata trovata alcuna corrispondenza,
     *         {@code -1} in caso di eccezione SQL o errore di connessione
     */
    public int caricaDaDB() {
        String query = "SELECT * FROM classe_virtuale WHERE CodiceUnivoco = " + "'" + this.codiceUnivoco + "'";
        try {
            ResultSet rs = DBManager.selectQuery(query);
            if (rs.next()) {
                this.setCodiceUnivoco(rs.getString("CodiceUnivoco"));
                this.setNome(rs.getString("Nome"));
                return  1;
            } else {
                return  0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return  -1;
        }
    }

    /**
     * Recupera la lista delle classi virtuali assegnate a un determinato docente.
     * <p>
     * Effettua una query sulla tabella {@code classe_virtuale} per trovare tutte
     * le classi dove il campo {@code docente_IndirizzoEmailIstituzionale} corrisponde all'indirizzo fornito.
     *
     * @param emailDocente l'email istituzionale del docente
     * @return una lista di oggetti {@code ClasseVirtualeDAO} contenenti i dati delle classi assegnate,
     *         oppure {@code null} in caso di errore
     */
    public ArrayList<ClasseVirtualeDAO> getListaClassiDelDocente(String emailDocente) {

        ArrayList<ClasseVirtualeDAO> listaClassi = new ArrayList<>();
        String query = "SELECT * FROM classe_virtuale WHERE docente_IndirizzoEmailIstituzionale = '"+ emailDocente + "'";
        try{
            ResultSet rs = DBManager.selectQuery(query);
            while (rs.next()){
                ClasseVirtualeDAO classe = new ClasseVirtualeDAO();

                classe.setCodiceUnivoco(rs.getString("CodiceUnivoco"));
                classe.setNome(rs.getString("Nome"));
                listaClassi.add(classe);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return listaClassi;
    }

    /**
     * Restituisce il codice univoco della classe virtuale.
     *
     * @return codice univoco come stringa
     */
    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    /**
     * Imposta il codice univoco della classe virtuale.
     *
     * @param codiceUnivoco il nuovo valore da assegnare
     */
    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    /**
     * Restituisce il nome della classe virtuale.
     *
     * @return nome della classe
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome della classe virtuale.
     *
     * @param nome nome da assegnare alla classe
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
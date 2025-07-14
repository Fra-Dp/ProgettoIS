package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Data Access Object (DAO) per la gestione del profilo personale dello studente nel database.
 * <p>
 * Questa classe consente di:
 * -Caricare un profilo personale esistente dal database
 * -Inserire un nuovo profilo personale per uno studente
 * -Gestire i dati relativi a: totale punti ottenuti, numero di task svolti ed email istituzionale dello studente
 * <p>
 * Le operazioni vengono eseguite tramite query SQL sulla tabella {@code profilo_personale},
 * utilizzando il supporto del gestore di connessione {@link DBManager}.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco</p>
 */
public class ProfiloPersonaleDAO {
    private int totalePuntiOttenuti;
    private int numeroTaskSvolti;
    private String indirizzoEmailIstituzionale;
    private ArrayList<AttivitaDAO> listaAttivita;
    private ArrayList<RiconoscimentoDAO> listaRiconoscimento;

    /**
     * Costruttore di default. Inizializza punteggio e numero di task a zero.
     */
    public ProfiloPersonaleDAO() {
        super();
        this.numeroTaskSvolti = 0;
        this.totalePuntiOttenuti = 0;
    }

    /**
     * Carica i dati del profilo personale di uno studente dal database,
     * utilizzando l'indirizzo email istituzionale come chiave.
     *
     * @return {@code 1} se il profilo è stato caricato correttamente,
     *         {@code 0} se non esiste alcun profilo associato all'email fornita,
     *         {@code -1} in caso di errore durante l'accesso al database
     */
    public int caricaDaDB() {
        String query = "SELECT * FROM Profilo_Personale WHERE studente_IndirizzoEmailIstituzionale = " + "'" + this.indirizzoEmailIstituzionale + "'";
        try {
            ResultSet rs = DBManager.selectQuery(query);
            if (rs.next()) {
                this.indirizzoEmailIstituzionale = rs.getString("studente_IndirizzoEmailIstituzionale");
                this.totalePuntiOttenuti = rs.getInt("TotalePuntiOttenuti");
                this.numeroTaskSvolti = rs.getInt("NumeroTaskSvolti");
                return 1;
            } else {
                return 0;
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Inserisce un nuovo record di profilo personale nel database per uno studente specificato.
     *
     * @param email l'indirizzo email istituzionale dello studente
     * @return {@code 1} se l'inserimento ha avuto successo,<br>
     *         {@code -1} in caso di errore SQL o connessione fallita
     */
    public int scriviSuDB(String email) {
        int ret;
        String query = "INSERT INTO profilo_personale(TotalePuntiOttenuti, NumeroTaskSvolti,studente_IndirizzoEmailIstituzionale) " +
                "VALUES ('" + this.totalePuntiOttenuti + "', '" + this.numeroTaskSvolti + "', '" + email + "')";
        try{
            ret = DBManager.updateQuery(query);

        }catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            ret = -1; //errore di scrittura
        }
        return ret;
    }

    //get e set

    /**
     * Restituisce il totale dei punti ottenuti dallo studente.
     *
     * @return punti totali
     */
    public int getTotalePuntiOttenuti() {
        return totalePuntiOttenuti;
    }

    /**
     * Imposta il totale dei punti ottenuti dallo studente.
     *
     * @param totalePuntiOttenuti punti totali
     */
    public void setTotalePuntiOttenuti(int totalePuntiOttenuti) {
        this.totalePuntiOttenuti = totalePuntiOttenuti;
    }

    /**
     * Restituisce il numero di task svolti dallo studente.
     *
     * @return numero di task svolti
     */
    public int getNumeroTaskSvolti() {
        return numeroTaskSvolti;
    }

    /**
     * Imposta il numero di task svolti dallo studente.
     *
     * @param numeroTaskSvolti numero di task svolti
     */
    public void setNumeroTaskSvolti(int numeroTaskSvolti) {
        this.numeroTaskSvolti = numeroTaskSvolti;
    }

    /**
     * Restituisce l'indirizzo email istituzionale dello studente.
     *
     * @return indirizzo email
     */
    public String getIndirizzoEmailIstituzionale() {
        return indirizzoEmailIstituzionale;
    }

    /**
     * Imposta l'indirizzo email istituzionale dello studente.
     *
     * @param indirizzoEmailIstituzionale indirizzo email
     */
    public void setIndirizzoEmailIstituzionale(String indirizzoEmailIstituzionale) {
        this.indirizzoEmailIstituzionale = indirizzoEmailIstituzionale;
    }

    /**
     * Restituisce la lista delle attività svolte dallo studente.
     * Ogni oggetto {@link AttivitaDAO} rappresenta l'associazione tra uno studente e un task didattico,
     * includendo informazioni come la consegna e l'eventuale punteggio ottenuto.
     *
     * @return una lista di oggetti {@link AttivitaDAO} associati al profilo dello studente
     */
    public ArrayList<AttivitaDAO> getListaAttivita() {
        return listaAttivita;
    }

    /**
     * Imposta la lista delle attività associate al profilo dello studente.
     * Sostituisce l'elenco attuale con quello fornito.
     *
     * @param listaAttivita lista di oggetti {@link AttivitaDAO} da associare
     */
    public void setListaAttivita(ArrayList<AttivitaDAO> listaAttivita) {
        this.listaAttivita = listaAttivita;
    }

    /**
     * Restituisce la lista dei riconoscimenti ottenuti dallo studente.
     * Ogni oggetto {@link RiconoscimentoDAO} contiene informazioni sul badge ricevuto
     * e la data di ottenimento.
     *
     * @return una lista di oggetti {@link RiconoscimentoDAO}
     */
    public ArrayList<RiconoscimentoDAO> getListaRiconoscimento() {
        return listaRiconoscimento;
    }

    /**
     * Imposta la lista dei riconoscimenti associati al profilo dello studente.
     *
     * @param listaRiconoscimento lista di oggetti {@link RiconoscimentoDAO} da assegnare
     */
    public void setListaRiconoscimento(ArrayList<RiconoscimentoDAO> listaRiconoscimento) {
        this.listaRiconoscimento = listaRiconoscimento;
    }

}

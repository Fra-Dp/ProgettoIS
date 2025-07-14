package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) per la gestione dei task didattici nel database.
 * <p>
 * La classe {@code TaskDidatticoDAO} fornisce metodi per:
 * -Caricare le informazioni di un task esistente tramite il titolo
 * -Creare e salvare un nuovo task nella tabella {@code task_didattico}
 * -Recuperare i task assegnati a una determinata classe virtuale
 * <p>
 * Ogni istanza rappresenta un singolo task didattico.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class TaskDidatticoDAO {
    private String titolo;
    private String descrizione;
    private LocalDate dataDiScadenza;
    private int numeroMassimoDiPuntiAssegnabili;
    private ClasseVirtualeDAO classeVirtualeAssociata;

    /**
     * Costruttore di default.
     */
    public TaskDidatticoDAO(){
        super();
    }

    /**
     * Carica dal database i dati del task utilizzando il titolo come chiave.
     *
     * @return {@code 1} se il task è stato trovato e caricato correttamente,
     *         {@code 0} se non esiste alcun task con il titolo indicato,
     *         {@code -1} in caso di errore di accesso al database
     */
    public int caricaDaDB() {
        String query = "SELECT * FROM task_didattico WHERE titolo = " + "'" + this.titolo + "'";

        try {
            ResultSet rs = DBManager.selectQuery(query);
            if (rs.next()) {
                this.setTitolo(rs.getString("titolo"));
                this.setDescrizione(rs.getString("descrizione"));
                this.setDataDiScadenza(rs.getDate("DataDiScadenza").toLocalDate());
                this.setNumeroMassimoDiPuntiAssegnabili(rs.getInt("NumeroMassimoDiPuntiAssegnabili"));
                this.classeVirtualeAssociata.setCodiceUnivoco(rs.getString("classe_virtuale_CodiceUnivoco"));
                return 1;
            } else {
                return 0;
            }
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Inserisce un nuovo task didattico nel database.
     *
     * @param titolo                       titolo del task
     * @param descrizione                  descrizione del task
     * @param dataDiScadenza               data entro cui deve essere completato
     * @param numeroMassimoDiPuntiAssegnabili numero massimo di punti assegnabili
     * @param classeVirtualeCodiceUnivoco  codice della classe virtuale a cui è associato
     * @return numero di righe inserite (1 in caso di successo), oppure -1 in caso di errore
     */
    public int creaTask(String titolo, String descrizione, LocalDate dataDiScadenza, int numeroMassimoDiPuntiAssegnabili, String classeVirtualeCodiceUnivoco) {

        String query = "INSERT INTO task_didattico (" +
                "Titolo, Descrizione, DataDiScadenza, NumeroMassimoDiPuntiAssegnabili, classe_virtuale_CodiceUnivoco" +
                ") VALUES (" +
                "'" + titolo.replace("'", "''")+ "', " +
                "'" + descrizione.replace("'", "''") + "', " +
                "'" + dataDiScadenza + "', " +
                numeroMassimoDiPuntiAssegnabili + ", " +
                "'" + classeVirtualeCodiceUnivoco + "'" +
                ")";
        try {
            return  DBManager.updateQuery(query);
        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * Restituisce la lista dei task assegnati a una determinata classe virtuale e non ancora consegnati.
     *
     * @param codiceClasse il codice univoco della classe virtuale
     * @return lista di {@link TaskDidatticoDAO} assegnati a quella classe
     */
    public List<TaskDidatticoDAO> getTaskAssegnatiByClasse(String codiceClasse){
        ArrayList<TaskDidatticoDAO> taskAssegnati = new ArrayList<>();
        String query = "SELECT DISTINCT t.* FROM task_didattico t " +
                "JOIN attivita a ON a.task_didattico_Titolo = t.Titolo " +
                " WHERE a.Consegnato = 0 AND t.Classe_virtuale_CodiceUnivoco = '" + codiceClasse + "'";

        try{
            ResultSet rs = DBManager.selectQuery(query);
            while (rs.next()) {
                TaskDidatticoDAO t = new TaskDidatticoDAO();
                t.setTitolo(rs.getString("Titolo"));
                t.setDescrizione(rs.getString("Descrizione"));
                t.setDataDiScadenza(rs.getDate("DataDiScadenza").toLocalDate());
                t.setNumeroMassimoDiPuntiAssegnabili(rs.getInt("NumeroMassimoDiPuntiAssegnabili"));
                taskAssegnati.add(t);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return taskAssegnati;
    }

    // Getter e Setter

    /**
     * Restituisce il titolo del task.
     * @return titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il titolo del task.
     * @param titolo il titolo da assegnare
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce la descrizione del task.
     * @return descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la descrizione del task.
     * @param descrizione la descrizione da assegnare
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisce la data di scadenza del task.
     * @return data di scadenza
     */
    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    /**
     * Imposta la data di scadenza del task.
     * @param dataDiScadenza la data da assegnare
     */
    public void setDataDiScadenza(LocalDate dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    /**
     * Restituisce il numero massimo di punti assegnabili per il task.
     * @return punti massimi
     */
    public int getNumeroMassimoDiPuntiAssegnabili() {
        return numeroMassimoDiPuntiAssegnabili;
    }

    /**
     * Imposta il numero massimo di punti assegnabili.
     * @param numeroMassimoDiPuntiAssegnabili il numero da assegnare
     */
    public void setNumeroMassimoDiPuntiAssegnabili(int numeroMassimoDiPuntiAssegnabili) {
        this.numeroMassimoDiPuntiAssegnabili = numeroMassimoDiPuntiAssegnabili;
    }

    /**
     * Restituisce il riferimento della classe virtuale associata.
     *
     * @return codice classe virtuale
     */
    public ClasseVirtualeDAO getClasseVirtualeAssociata() {
        return classeVirtualeAssociata;
    }

    /**
     * Imposta la classe virtuale associata.
     *
     * @param classeVirtualeAssociata il codice da assegnare
     */
    public void setClasseVirtualeAssociata(ClasseVirtualeDAO classeVirtualeAssociata) {
        this.classeVirtualeAssociata = classeVirtualeAssociata;
    }
}
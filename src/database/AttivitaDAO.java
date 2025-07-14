package database;

import dto.StudenteDTO;

import dto.TaskDidatticoDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code  AttivitaDAO} rappresenta l'entità associativa tra un {@link ProfiloPersonaleDAO}
 *  * e un {@link TaskDidatticoDAO}, modellando l'assegnazione di un task a uno studente.
 * <p>
 * Ogni istanza include un riferimento al profilo personale dello studente,
 * al task didattico associato e lo stato di consegna dell'attività.
 * <p>
 * Le responsabilità principali di questa classe includono:
 * -Assegnare un task a tutti gli studenti di una classe virtuale
 * -Recuperare i task assegnati a uno studente
 * -Recuperare l’elenco degli studenti che hanno consegnato almeno un task
 * -Recuperare i task già consegnati da uno studente specifico
 *
 * <p>
 * I dati restituiti sono tipicamente convertiti in oggetti {@link TaskDidatticoDTO} o {@link StudenteDTO}
 * per essere trasmessi al livello di presentazione tramite Controller.
 * <p>
 * Tutte le query sono eseguite utilizzando il {@link DBManager}, e in caso di errore viene restituito -1 o null.
 *
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class AttivitaDAO {
    private ProfiloPersonaleDAO profiloPersonaleAssociato;
    private TaskDidatticoDAO taskAssociato;
    private boolean consegnato;

    /**
     * Costuttore di default della classe
     */
    public AttivitaDAO() {
        super();
    }

    /**
     * Costruttore completo della classe.
     *
     * @param profiloPersonaleAssociato oggetto {@link ProfiloPersonaleDAO} associato all'attività
     * @param taskAssociato             oggetto {@link TaskDidatticoDAO} da assegnare
     * @param consegnato                stato di consegna del task (true se consegnato, false altrimenti)
     */
    public AttivitaDAO(ProfiloPersonaleDAO profiloPersonaleAssociato, TaskDidatticoDAO taskAssociato, boolean consegnato) {
        this.profiloPersonaleAssociato = profiloPersonaleAssociato;
        this.taskAssociato = taskAssociato;
        this.consegnato = consegnato;
    }

    /**
     * Assegna un task a tutti gli studenti appartenenti a una classe virtuale.
     * Ogni studente riceve un'associazione nella tabella {@code Attivita} con stato di consegna inizialmente false.
     *
     * @param titolo       titolo del task
     * @param codiceClasse codice univoco della classe virtuale
     * @return numero di righe inserite nel database, oppure -1 in caso di errore
     */
    public  int associaTaskAStudenti(String titolo, String codiceClasse){
        String query = "INSERT INTO Attivita (" +
                "profilo_personale_studente_IndirizzoEmailIstituzionale, task_didattico_Titolo, Consegnato) " +
                "SELECT s.IndirizzoEmailIstituzionale, '" + titolo.replace("'", "''") + "', 0 " +
                "FROM studente s " +
                "WHERE s.classe_virtuale_CodiceUnivoco = '" + codiceClasse.replace("'", "''") + "'";
        try {
            return DBManager.updateQuery(query);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Restituisce l'elenco dei task non ancora consegnati dallo studente.
     *
     * @param emailStudente indirizzo email dello studente
     * @return lista di oggetti {@code AttivitaDAO} con stato consegna false
     */
    public List<AttivitaDAO> selezionaTaskAssegnati(String emailStudente) {
        List<AttivitaDAO> listaAttivita = new ArrayList<>();

        String query = "SELECT t.Titolo, t.Descrizione, t.DataDiScadenza, t.NumeroMassimoDiPuntiAssegnabili, pp.NumeroTaskSvolti, pp.TotalePuntiOttenuti, a.Consegnato " +
                "FROM profilo_personale pp  JOIN attivita a " +
                "ON pp.studente_IndirizzoEmailIstituzionale = a.profilo_personale_studente_IndirizzoEmailIstituzionale " +
                "JOIN task_didattico t ON a.task_didattico_Titolo = t.Titolo " +
                "WHERE a.profilo_personale_studente_IndirizzoEmailIstituzionale = '" + emailStudente.replace("'", "''") +"'" +
                "AND a.Consegnato = 0";

        return getListaAttivitaDAO(emailStudente, listaAttivita, query);
    }

    /**
     * Restituisce l'elenco dei task già consegnati dallo studente.
     *
     * @param emailStudente indirizzo email dello studente
     * @return lista di oggetti {@code AttivitaDAO} con stato consegna true
     */
    public List<AttivitaDAO> ottieniTaskConsegnatiDaStudente(String emailStudente) {
        List<AttivitaDAO> listaAttivita = new ArrayList<>();

        String query = "SELECT t.Titolo, t.Descrizione, t.DataDiScadenza, t.NumeroMassimoDiPuntiAssegnabili, pp.NumeroTaskSvolti, pp.TotalePuntiOttenuti, a.Consegnato " +
                "FROM profilo_personale pp  JOIN attivita a " +
                "ON pp.studente_IndirizzoEmailIstituzionale = a.profilo_personale_studente_IndirizzoEmailIstituzionale " +
                "JOIN task_didattico t ON a.task_didattico_Titolo = t.Titolo " +
                "WHERE a.profilo_personale_studente_IndirizzoEmailIstituzionale = '" + emailStudente.replace("'", "''") +"'" +
                "AND a.Consegnato = 1";

        return getListaAttivitaDAO(emailStudente, listaAttivita, query);

    }

    /**
     * Recupera e costruisce la lista di attività {@code AttivitaDAO} in base a una query SQL specifica.
     *
     * @param emailStudente indirizzo email dello studente
     * @param listaAttivita lista da riempire con i risultati
     * @param query         query SQL da eseguire
     * @return lista di {@code AttivitaDAO} ottenute dal database
     */
    private List<AttivitaDAO> getListaAttivitaDAO(String emailStudente, List<AttivitaDAO> listaAttivita, String query) {
        try {
            ResultSet rs = DBManager.selectQuery(query);

            while (rs.next()) {
                // Costruzione oggetti Task e Profilo
                TaskDidatticoDAO task = new TaskDidatticoDAO();
                task.setTitolo(rs.getString("Titolo"));
                task.setDescrizione(rs.getString("Descrizione"));
                task.setDataDiScadenza(rs.getDate("DataDiScadenza").toLocalDate());
                task.setNumeroMassimoDiPuntiAssegnabili(rs.getInt("NumeroMassimoDiPuntiAssegnabili"));

                ProfiloPersonaleDAO profilo = new ProfiloPersonaleDAO();
                profilo.setIndirizzoEmailIstituzionale(emailStudente);
                profilo.setNumeroTaskSvolti(rs.getInt("NumeroTaskSvolti"));
                profilo.setTotalePuntiOttenuti(rs.getInt("TotalePuntiOttenuti"));

                boolean statoConsegnato = rs.getBoolean("Consegnato");

                listaAttivita.add(new AttivitaDAO(profilo,task, statoConsegnato));

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listaAttivita;
    }

    /**
     * Restituisce l'elenco degli studenti di una classe che hanno consegnato almeno un task.
     *
     * @param codiceClasse codice univoco della classe virtuale
     * @return lista di oggetti {@link StudenteDAO} che hanno consegnato almeno un'attività
     */
    public ArrayList<StudenteDAO> getStudentiConTaskConsegnato(String codiceClasse) {
        ArrayList<StudenteDAO> studentiConsegna = new ArrayList<>();

        String query = "SELECT DISTINCT s.IndirizzoEmailIstituzionale, s.Nome, s.Cognome, s.classe_virtuale_CodiceUnivoco " +
                "FROM studente s " +
                "JOIN profilo_personale p ON s.IndirizzoEmailIstituzionale = p.studente_IndirizzoEmailIstituzionale " +
                "JOIN attivita a ON p.studente_IndirizzoEmailIstituzionale = a.profilo_personale_studente_IndirizzoEmailIstituzionale " +
                "WHERE a.Consegnato = 1 " +
                "AND s.classe_virtuale_CodiceUnivoco = '" + codiceClasse.replace("'", "''") + "'";

        try {
            ResultSet rs = DBManager.selectQuery(query);
            while (rs.next()) {
                StudenteDAO studente = new StudenteDAO();
                studente.setNome(rs.getString("Nome"));
                studente.setCognome(rs.getString("Cognome"));
                studente.setIndirizzoEmailIstituzionale(rs.getString("IndirizzoEmailIstituzionale"));
                studentiConsegna.add(studente);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return studentiConsegna;
    }

    // Getter e Setter

    /**
     * Restituisce l'email dello studente associata a questa attività.
     *
     * @return email dello studente
     */
    public ProfiloPersonaleDAO getProfiloPersonaleAssociato() {
        return profiloPersonaleAssociato;
    }

    /**
     * Imposta l'email dello studente associata a questa attività.
     *
     * @param profiloPersonaleAssociato indirizzo email da assegnare
     */
    public void setProfiloPersonaleAssociato(ProfiloPersonaleDAO profiloPersonaleAssociato) {
        this.profiloPersonaleAssociato = profiloPersonaleAssociato;
    }

    /**
     * Restituisce il titolo del task associato a questa attività.
     *
     * @return titolo del task
     */
    public TaskDidatticoDAO getTaskAssociato() {
        return taskAssociato;
    }

    /**
     * Imposta il titolo del task associato a questa attività.
     *
     * @param taskAssociato titolo del task da assegnare
     */
    public void setTaskAssociato(TaskDidatticoDAO taskAssociato) {
        this.taskAssociato = taskAssociato;
    }

    /**
     * Indica se il task è stato consegnato.
     *
     * @return true se consegnato, false altrimenti
     */
    public boolean isConsegnato() {
        return consegnato;
    }

    /**
     * Imposta lo stato di consegna del task.
     *
     * @param consegnato true se il task è stato consegnato, false altrimenti
     */
    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }
}

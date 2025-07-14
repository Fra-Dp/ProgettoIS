package entity;

import database.StudenteDAO;
import dto.StudenteDTO;
import database.AttivitaDAO;
import database.TaskDidatticoDAO;
import dto.TaskDidatticoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una classe virtuale all'interno della piattaforma didattica.
 * <p>
 * Ogni classe è identificata da un codice univoco e un nome, e mantiene
 * riferimenti agli studenti iscritti e ai task didattici assegnati.
 * Questa classe fornisce funzionalità per:
 *  -Creare nuovi task didattici
 *  -Recuperare la lista di studenti con task consegnati
 *  -Ottenere la lista dei task attualmente assegnati
 *
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class EntityClasseVirtuale {

    private String codiceUnivoco;
    private String nome;
    private ArrayList<EntityStudente> studentiIscritti;
    private ArrayList<EntityTaskDidattico> taskAssegnati;

    /**
     * Costruttore di default. Inizializza l'oggetto senza valori assegnati.
     */
    public EntityClasseVirtuale() {
        super();
    }

    /**
     * Costruttore parametrico che inizializza la classe virtuale con codice e nome.
     *
     * @param codiceUnivoco il codice univoco della classe
     * @param nome il nome assegnato alla classe
     */
    public EntityClasseVirtuale(String codiceUnivoco, String nome) {
        this.codiceUnivoco = codiceUnivoco;
        this.nome = nome;
    }

    /**
     * Restituisce la lista degli studenti della classe che hanno consegnato almeno un task.
     *
     * @return lista di studenti come {@link StudenteDTO}
     */
    public ArrayList<StudenteDTO> getStudentiConTaskConsegnato() {
        ArrayList<StudenteDTO> listaStudentiConsegnato = new ArrayList<>();
        AttivitaDAO attivitaDAO = new AttivitaDAO();
        for (StudenteDAO studenteDAO :  attivitaDAO.getStudentiConTaskConsegnato(this.codiceUnivoco)) {
            String nome = studenteDAO.getNome();
            String cognome = studenteDAO.getCognome();
            String emailStudente = studenteDAO.getIndirizzoEmailIstituzionale();
            StudenteDTO temp = new StudenteDTO(nome, cognome, emailStudente);
            listaStudentiConsegnato.add(temp);
        }
        return listaStudentiConsegnato;
    }

    /**
     * Crea e assegna un nuovo task alla classe virtuale.
     * Il task viene anche salvato nel database.
     *
     * @param titolo titolo del task
     * @param descrizione descrizione del task
     * @param dataDiScadenza data di scadenza per la consegna
     * @param numeroMaxPunti numero massimo di punti assegnabili
     * @param codiceClasse codice della classe a cui assegnare il task
     * @return un oggetto {@link EntityTaskDidattico} se la creazione è andata a buon fine, {@code null} altrimenti
     */
    public EntityTaskDidattico creaTask(String titolo, String descrizione, LocalDate dataDiScadenza, int numeroMaxPunti , String codiceClasse){
        EntityTaskDidattico taskDidattico = new EntityTaskDidattico();
        int ret = taskDidattico.creaTask(titolo,descrizione,dataDiScadenza,numeroMaxPunti,codiceClasse);
        if (ret != -1){
            taskDidattico.setTitolo(titolo);
            taskDidattico.setDescrizione(descrizione);
            taskDidattico.setDataDiScadenza(dataDiScadenza);
            taskDidattico.setNumMaxDiPuntiAssegnabili(numeroMaxPunti);
            this.taskAssegnati.add(taskDidattico);
            return taskDidattico;
        }
        return null;
    }

    /**
     * Restituisce la lista aggiornata dei task didattici assegnati alla classe.
     * I dati sono recuperati dal database tramite DAO.
     *
     * @return lista di {@link TaskDidatticoDTO}
     */
    public List<TaskDidatticoDTO> getListaTaskAssegnati() {
        ArrayList<TaskDidatticoDTO> listaTaskAssegnati = new ArrayList<>();
        TaskDidatticoDAO taskDidattico = new TaskDidatticoDAO();
        for(TaskDidatticoDAO task :taskDidattico.getTaskAssegnatiByClasse(this.codiceUnivoco)){
            String titolo = task.getTitolo();
            String descrizione = task.getDescrizione();
            LocalDate dataDiScadenza = task.getDataDiScadenza();
            int numeroMaxDiPuntiAssegnabili = task.getNumeroMassimoDiPuntiAssegnabili();

            TaskDidatticoDTO temp = new TaskDidatticoDTO(titolo,descrizione,dataDiScadenza,numeroMaxDiPuntiAssegnabili);
            listaTaskAssegnati.add(temp);
        }
        return listaTaskAssegnati;
    }

    // Getter e Setter

    /**
     * Restituisce il codice univoco della classe.
     *
     * @return codice univoco
     */
    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    /**
     * Imposta il codice univoco della classe.
     *
     * @param codiceUnivoco codice identificativo
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
     * @param nome nome della classe
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la lista degli studenti iscritti
     *
     * @return lista di {@link EntityStudente}
     */
    public List<EntityStudente> getStudentiIscritti() {
        return studentiIscritti;
    }

    /**
     * Imposta la lista degli studenti iscritti alla classe.
     *
     * @param studentiIscritti lista di studenti
     */
    public void setStudentiIscritti(List<EntityStudente> studentiIscritti) {
        this.studentiIscritti = (ArrayList<EntityStudente>) studentiIscritti;
    }

    /**
     * Restituisce la lista dei task assegnati associati all'oggetto.
     * Non esegue query al database.
     *
     * @return lista di task
     */
    public List<EntityTaskDidattico> getTaskAssegnati() {
        return taskAssegnati;
    }

    /**
     * Imposta la lista dei task assegnati alla classe.
     *
     * @param taskAssegnati lista di {@link EntityTaskDidattico}
     */
    public void setTaskAssegnati(List<EntityTaskDidattico> taskAssegnati) {
        this.taskAssegnati = (ArrayList<EntityTaskDidattico>) taskAssegnati;
    }
}

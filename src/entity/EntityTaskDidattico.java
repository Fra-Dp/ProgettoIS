package entity;

import database.AttivitaDAO;
import database.TaskDidatticoDAO;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Rappresenta un task didattico associato a una classe virtuale.
 * <p>
 * Ogni task è caratterizzato da un titolo, una descrizione, una data di scadenza
 * e un numero massimo di punti assegnabili.
 * Questa classe fornisce anche metodi per creare il task nel database e associarlo
 * automaticamente agli studenti della classe.
 * <p>
 * La creazione e l'associazione dei task si basano sull'interazione con i rispettivi DAO
 * {@link TaskDidatticoDAO} e {@link AttivitaDAO}, i quali gestiscono la persistenza e i legami nel database.
 * <p>
 *
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class EntityTaskDidattico {

    private String titolo;
    private String descrizione;
    private LocalDate dataDiScadenza; // il formato è del tipo "yyyy-mm-gg"
    private int numMaxDiPuntiAssegnabili;
    private ArrayList<EntityAttivita> listaAttivita;

    /**
     * Costruttore vuoto di default. Inizializza un oggetto senza valori.
     * Da richiamare solo in ClasseVirtuale
     */
    public EntityTaskDidattico() {
        super();
    }

    /**
     * Costruttore parametrico. Inizializza un oggetto EntityTaskDidattico con tutti gli attributi.
     * Da richiamare solo in classe virtuale.
     *
     * @param titolo titolo del task
     * @param descrizione descrizione del contenuto od obiettivo del task
     * @param dataDiScadenza data entro cui il task deve essere completato
     * @param numMaxDiPuntiAssegnabili numero massimo di punti che il task può assegnare
     */
    public EntityTaskDidattico(String titolo, String descrizione, LocalDate dataDiScadenza, int numMaxDiPuntiAssegnabili) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataDiScadenza = dataDiScadenza;
        this.numMaxDiPuntiAssegnabili = numMaxDiPuntiAssegnabili;
    }

    /**
     * Associa un task esistente a tutti gli studenti della classe specificata nel database.
     *
     * @param titolo titolo del task da associare
     * @param codiceClasse codice identificativo della classe
     * @return 1 se l'associazione ha avuto successo, -1 in caso contrario
     */
    public int associaTaskAStudenti(String titolo, String codiceClasse){
        AttivitaDAO attivita = new AttivitaDAO();
        return attivita.associaTaskAStudenti(titolo,codiceClasse);
    }

    /**
     * Inserisce un nuovo task nel database e ne registra i dettagli.
     *
     * @param titolo titolo del task
     * @param descrizione descrizione del task
     * @param dataDiScadenza data di scadenza del task
     * @param numeroMaxPunti punteggio massimo assegnabile
     * @param codiceClasse codice della classe a cui il task è assegnato
     * @return 1 se la creazione ha avuto successo, -1 in caso di errore
     */
    public int creaTask(String titolo, String descrizione, LocalDate dataDiScadenza, int numeroMaxPunti , String codiceClasse){
        TaskDidatticoDAO taskDidatticoDAO = new TaskDidatticoDAO();
        int esito = taskDidatticoDAO.creaTask(titolo,descrizione,dataDiScadenza,numeroMaxPunti,codiceClasse);
        if(esito != -1){
            this.setTitolo(titolo);
            this.setDescrizione(descrizione);
            this.setDataDiScadenza(dataDiScadenza);
            this.setNumMaxDiPuntiAssegnabili(numeroMaxPunti);
        }
        return esito;
    }

    /**
     * Restituisce il titolo del task.
     *
     * @return titolo del task
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il titolo del task.
     *
     * @param titolo nuovo titolo da assegnare
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce la descrizione del task.
     *
     * @return descrizione testuale
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la descrizione del task.
     *
     * @param descrizione nuova descrizione da assegnare
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisce la data di scadenza del task.
     *
     * @return data di scadenza
     */
    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    /**
     * Imposta la data di scadenza del task.
     *
     * @param dataDiScadenza data entro cui il task va completato
     */
    public void setDataDiScadenza(LocalDate dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    /**
     * Restituisce il numero massimo di punti assegnabili per questo task.
     *
     * @return numero di punti massimi assegnabili
     */
    public int getNumMaxDiPuntiAssegnabili() {
        return numMaxDiPuntiAssegnabili;
    }

    /**
     * Imposta il numero massimo di punti assegnabili per questo task.
     *
     * @param numMaxDiPuntiAssegnabili numero massimo di punti assegnabili
     */
    public void setNumMaxDiPuntiAssegnabili(int numMaxDiPuntiAssegnabili) {
        this.numMaxDiPuntiAssegnabili = numMaxDiPuntiAssegnabili;
    }

    /**
     * Restituisce la lista delle attività svolte dallo studente.
     * Ogni attività rappresenta l'associazione tra uno studente e un task didattico,
     * con informazioni sullo stato della consegna.
     *
     * @return una lista di oggetti {@link EntityAttivita} associati al profilo personale
     */
    public ArrayList<EntityAttivita> getListaAttivita() {
        return listaAttivita;
    }

    /**
     * Imposta la lista delle attività associate al profilo personale dello studente.
     * Sostituisce completamente la lista esistente con quella fornita.
     *
     * @param listaAttivita una lista di oggetti {@link EntityAttivita} da associare al profilo
     */
    public void setListaAttivita(ArrayList<EntityAttivita> listaAttivita) {
        this.listaAttivita = listaAttivita;
    }
}

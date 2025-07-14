package entity;


import database.AttivitaDAO;
import database.ProfiloPersonaleDAO;
import database.RiconoscimentoDAO;
import dto.TaskDidatticoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta il profilo personale di uno studente all'interno del sistema.
 * <p>
 * Il profilo personale mantiene informazioni sulle attività svolte dallo studente,
 * come i punti ottenuti, il numero di task completati.
 * Interagisce con i componenti DAO per la lettura e scrittura dei dati persistenti
 * relativi al profilo, all’avanzamento dei task e al riconoscimento di badge.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class EntityProfiloPersonale {
    private int totalePuntiOttenuti;
    private int numeroTaskSvolti;
    private String emailStudente;
    private ArrayList<EntityAttivita> listaAttivita;
    private ArrayList<EntityRiconoscimento> listaRiconoscimento;


    /**
     * Costruttore di default.
     * Inizializza il profilo personale con punteggio e numero task a zero,
     * email vuota e liste di task inizializzate.
     */
    public EntityProfiloPersonale() {
        this.totalePuntiOttenuti = 0;
        this.numeroTaskSvolti = 0;
    }

    /**
     * Crea il profilo personale di uno studente nel database.
     *
     * @param email indirizzo email dello studente
     * @return 1 se l’operazione ha avuto successo, -1 in caso di errore
     */
    public int creaProfiloPersonale(String email) {
        ProfiloPersonaleDAO profiloPersonaleDAO = new ProfiloPersonaleDAO();
        int ret =  profiloPersonaleDAO.scriviSuDB(email);
        if(ret != -1){
            this.setEmailStudente(email);
        }
        return ret;
    }

    /**
     * Carica i dati del profilo personale associato all’indirizzo email specificato.
     * Aggiorna i campi interni con i valori estratti dal database.
     *
     * @param email indirizzo email dello studente
     * @return 1 se il caricamento è avvenuto correttamente, -1 altrimenti
     */
    public int ottieniDaDB(String email) {
        ProfiloPersonaleDAO profiloPersonaleDAO = new ProfiloPersonaleDAO();
        profiloPersonaleDAO.setIndirizzoEmailIstituzionale(email);
        int esito = profiloPersonaleDAO.caricaDaDB();
        if(esito != -1){
            this.setNumeroTaskSvolti(profiloPersonaleDAO.getNumeroTaskSvolti());
            this.setTotalePuntiOttenuti(profiloPersonaleDAO.getTotalePuntiOttenuti());
            this.setEmailStudente(email);
        }
        return esito;
    }

    /**
     * Verifica se il badge specificato è già assegnato allo studente.
     * Se non presente, procede con l'assegnazione tramite DAO.
     *
     * @param nomeBadge nome del badge da assegnare
     * @return 1 se il badge è stato assegnato, 0 se era già presente, -1 in caso di errore
     */
    public int assegnaBadgeSeNonPresente(String nomeBadge) {
        RiconoscimentoDAO riconoscimentoDAO = new RiconoscimentoDAO();
        if (!riconoscimentoDAO.studenteHaGiaBadge(this.emailStudente, nomeBadge)) {
            return riconoscimentoDAO.assegnaBadge(this.emailStudente, nomeBadge);
        }
        return 0; // badge già presente
    }

    /**
     * Carica tutte le attività associate allo studente dal database, distinguendo tra
     * quelle completate e quelle ancora da completare.
     * <p>
     * Popola la lista locale delle attività {@code listaAttivita} dell'oggetto corrente.
     * I dati vengono recuperati attraverso il DAO {@link AttivitaDAO}, trasformati in
     * oggetti {@link EntityAttivita} e classificati in base allo stato di consegna.
     *
     * @param emailStudente indirizzo email istituzionale dello studente di riferimento
     */
    public void caricaAttivitaDaDB(String emailStudente) {
        AttivitaDAO attivitaDAO = new AttivitaDAO();

        List<AttivitaDAO> attivitaAssegnate = attivitaDAO.selezionaTaskAssegnati(emailStudente);
        List<AttivitaDAO> attivitaCompletate = attivitaDAO.ottieniTaskConsegnatiDaStudente(emailStudente);

        ArrayList<EntityAttivita> listaFinale = new ArrayList<>();

        for (AttivitaDAO a : attivitaAssegnate) {
            EntityTaskDidattico task = new EntityTaskDidattico(
                    a.getTaskAssociato().getTitolo(),  a.getTaskAssociato().getDescrizione(),  a.getTaskAssociato().getDataDiScadenza(),  a.getTaskAssociato().getNumeroMassimoDiPuntiAssegnabili()
            );
            listaFinale.add(new EntityAttivita(this, task, false));
        }

        for (AttivitaDAO a : attivitaCompletate) {
            EntityTaskDidattico task = new EntityTaskDidattico(
                    a.getTaskAssociato().getTitolo(),  a.getTaskAssociato().getDescrizione(),  a.getTaskAssociato().getDataDiScadenza(),  a.getTaskAssociato().getNumeroMassimoDiPuntiAssegnabili()
            );
            listaFinale.add(new EntityAttivita(this, task, true));
        }

        this.setListaAttivita(listaFinale);
    }

    /**
     * Restituisce la lista dei task attualmente assegnati allo studente ma non ancora consegnati.
     * <p>
     * I dati vengono ricavati dalla lista locale di {@link EntityAttivita}, filtrando
     * quelle il cui flag {@code consegnato} è impostato su {@code false}.
     *
     * @return lista di oggetti {@link TaskDidatticoDTO} rappresentanti i task assegnati e non completati
     */
    public List<TaskDidatticoDTO> ottieniTaskAssegnati() {
        List<TaskDidatticoDTO> assegnati = new ArrayList<>();
        for (EntityAttivita a : this.listaAttivita) {
            if (!a.isConsegnato()) {
                assegnati.add(new TaskDidatticoDTO(
                        a.getTaskAssociato().getTitolo(), a.getTaskAssociato().getDescrizione(),
                        a.getTaskAssociato().getDataDiScadenza(),a.getTaskAssociato().getNumMaxDiPuntiAssegnabili()
                ));
            }
        }
        return assegnati;
    }

    /**
     * Restituisce la lista dei task completati dallo studente.
     * <p>
     * La lista viene costruita filtrando le attività presenti nella lista locale
     * in cui il flag {@code consegnato} risulta {@code true}.
     *
     * @return lista di {@link TaskDidatticoDTO} relativi ai task completati
     */
    public List<TaskDidatticoDTO> ottieniTaskConsegnatiDaStudente() {
        List<TaskDidatticoDTO> completati = new ArrayList<>();
        for (EntityAttivita a : this.listaAttivita) {
            if (a.isConsegnato()) {
                completati.add(new TaskDidatticoDTO(
                        a.getTaskAssociato().getTitolo(), a.getTaskAssociato().getDescrizione(),
                        a.getTaskAssociato().getDataDiScadenza(),a.getTaskAssociato().getNumMaxDiPuntiAssegnabili()
                ));
            }
        }
        return completati;
    }

    //get e set

    /**
     * Restituisce il totale dei punti ottenuti dallo studente.
     *
     * @return punteggio totale
     */
    public int getTotalePuntiOttenuti() {
        return totalePuntiOttenuti;
    }

    /**
     * Imposta il totale dei punti ottenuti dallo studente.
     *
     * @param totalePuntiOttenuti valore da assegnare
     */
    public void setTotalePuntiOttenuti(int totalePuntiOttenuti) {
        this.totalePuntiOttenuti = totalePuntiOttenuti;
    }

    /**
     * Restituisce il numero totale di task svolti dallo studente.
     *
     * @return numero di task completati
     */
    public int getNumeroTaskSvolti() {
        return numeroTaskSvolti;
    }

    /**
     * Imposta il numero di task svolti dallo studente.
     *
     * @param numeroTaskSvolti numero da assegnare
     */
    public void setNumeroTaskSvolti(int numeroTaskSvolti) {
        this.numeroTaskSvolti = numeroTaskSvolti;
    }

    /**
     * Restituisce l'email dello studente a cui è associato il profilo.
     *
     * @return indirizzo email
     */
    public String getEmailStudente() {
        return emailStudente;
    }

    /**
     * Imposta l'email dello studente a cui è associato il profilo.
     *
     * @param emailStudente email da assegnare
     */
    public void setEmailStudente(String emailStudente) {
        this.emailStudente = emailStudente;
    }

    /**
     * Restituisce la lista delle attività svolte dallo studente.
     * Ogni oggetto {@link EntityAttivita} rappresenta l'associazione tra uno studente e un task didattico,
     * includendo informazioni come la consegna e l'eventuale punteggio ottenuto.
     *
     * @return una lista di oggetti {@link EntityAttivita} associati al profilo dello studente
     */
    public ArrayList<EntityAttivita> getListaAttivita() {
        return listaAttivita;
    }

    /**
     * Imposta la lista delle attività associate al profilo dello studente.
     * Sostituisce l'elenco attuale con quello fornito.
     *
     * @param listaAttivita lista di oggetti {@link EntityAttivita} da associare
     */
    public void setListaAttivita(ArrayList<EntityAttivita> listaAttivita) {
        this.listaAttivita = listaAttivita;
    }

    /**
     * Restituisce la lista dei riconoscimenti ottenuti dallo studente.
     * Ogni oggetto {@link EntityRiconoscimento} contiene informazioni sul badge ricevuto
     * e la data di ottenimento.
     *
     * @return una lista di oggetti {@link EntityRiconoscimento}
     */
    public ArrayList<EntityRiconoscimento> getListaRiconoscimento() {
        return listaRiconoscimento;
    }

    /**
     * Imposta la lista dei riconoscimenti associati al profilo dello studente.
     *
     * @param listaRiconoscimento lista di oggetti {@link EntityRiconoscimento} da assegnare
     */
    public void setListaRiconoscimento(ArrayList<EntityRiconoscimento> listaRiconoscimento) {
        this.listaRiconoscimento = listaRiconoscimento;
    }
}

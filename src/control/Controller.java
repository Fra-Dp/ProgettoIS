package control;
import dto.ClasseVirtualeDTO;
import dto.StudenteDTO;
import dto.TaskDidatticoDTO;
import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *  La classe {@code Controller} agisce da intermediario tra l'interfaccia utente (Boundary) e la logica applicativa (Entity).
 * <p>
 * Fornisce i metodi necessari alla gestione del flusso dati e delle operazioni principali della piattaforma,
 * senza occuparsi direttamente della persistenza o della gestione dello stato interno delle entità.
 * <p>
 * Attualmente è implementata come singleton tramite {@code getInstance()}
 * <p>
 *
 * Le responsabilità principali del Controller includono:
 * - Gestione della registrazione e autenticazione di studenti e docenti
 * - Coordinamento della creazione dei task e loro associazione automatica agli studenti
 * - Recupero delle classi e dei task assegnati
 * - Assegnazione automatica dei badge al login dello studente, in base a criteri di performance
 * <p>
 * Tutte le entità vengono interrogate tramite oggetti {@code Entity}, mentre i dati in uscita vengono trasferiti
 * alla boundary usando oggetti {@code DTO} (Data Transfer Object) per evitare esposizione diretta del modello.
 * <p>
 *
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class Controller {
    private final EntityPiattaformaDigitale piattaformaDigitale = EntityPiattaformaDigitale.getInstance();
    private static Controller singletonController = null;

    /**
     * Costruttore privato della classe {@code Controller}, utilizzato per implementare il pattern Singleton.
     * <p>
     * All'interno del costruttore viene inizializzato lo stato interno della piattaforma,
     * recuperando e impostando gli elenchi aggiornati di docenti e studenti iscritti.
     * <p>
     * Questo consente di avere a disposizione in memoria gli utenti registrati,
     * evitando chiamate ridondanti al database nei metodi successivi.
     * <p>
     * Il costruttore è invocato una sola volta tramite il metodo {@link #getInstance()}.
     */
    private Controller() {

        piattaformaDigitale.setDocentiIscritti(piattaformaDigitale.getDocentiIscritti());
        piattaformaDigitale.setStudentiIscritti(piattaformaDigitale.getStudentiIscritti());

    }

    /**
     * Restituisce l'istanza singleton del Controller.
     *
     * @return l'unica istanza di {@code Controller}
     */
    public static Controller getInstance() {
        if (singletonController == null) {
            singletonController = new Controller();
        }
        return singletonController;
    }

    /**
     * Registra un nuovo studente nel sistema e crea automaticamente il relativo profilo personale.
     *
     * @param nome      Nome dello studente
     * @param cognome   Cognome dello studente
     * @param email     Email istituzionale (es. francesco@studente.it)
     * @param password  Password per l'accesso
     * @return Messaggio di successo o errore relativo all'operazione
     */
    public  String registraStudente(String nome, String cognome, String email, String password) {
        int result = piattaformaDigitale.registraStudente(nome, cognome, email, password);

        if (result == -1) {
            return "Non è stato possibile registrare lo studente, prova con un altra email!";
        }else {
            EntityStudente studente = piattaformaDigitale.getStudenteByEmail(email);
            int success = studente.creaProfiloPersonale();
            if (success == 1) {
                return "Lo studente è stato inserito con successo ";
            }
            return "Non è stato possibile associare un profiloPersonale";
        }
    }

    /**
     * Registra un nuovo docente nel sistema.
     *
     * @param nome      Nome del docente
     * @param cognome   Cognome del docente
     * @param email     Email istituzionale (es. diPinto@docente.it)
     * @param password  Password per l'accesso
     * @return Messaggio di successo o errore
     */
    public  String registraDocente(String nome, String cognome, String email, String password) {
        int result = piattaformaDigitale.registraDocente(nome, cognome, email, password);
        if (result == -1) {
            return "Non è stato possibile registrare il docente";
        }else {
            return "Il docente è stato inserito con successo ";
        }
    }

    /**
     * Tenta l’accesso al profilo personale dello studente tramite email e password.
     * Se l'autenticazione va a buon fine, procede con l'assegnazione automatica dei badge
     * in base ai risultati ottenuti dallo studente.
     *
     * @param email    Email istituzionale dello studente
     * @param password Password fornita per l’autenticazione
     * @return Messaggio che indica l'esito dell'accesso:
     *         - "Accesso Effettuato con successo e Badge assegnati" se login e assegnazione badge sono riusciti
     *         - "Errore nell'assegnazione del Badge" se l'accesso è riuscito ma l’assegnazione dei badge ha fallito
     *         - "Password Incorretta" se la password non è valida
     *         - "Errore Utente non trovato" se l'email non è presente nel sistema
     */
    public  String accessoAlProfiloPersonale(String email , String password) {
        EntityStudente studente = piattaformaDigitale.accessoAlProfiloPersonale(email);
        if (studente != null) {
                if (password.equals(studente.getPassword())) {
                    int esitoProfilo = studente.recuperaProfiloPersonale();
                    int risultato = assegnazioneAutomaticaBadge(esitoProfilo, studente.getProfiloPersonale());
                    if (risultato == -1) {
                        return "Errore nell'assegnazione del Badge";
                    }
                    return "Accesso Effettuato con successo e Badge assegnati";
                }
                return "Password Incorretta";
            }
        return "Errore Utente non trovato";
    }
    
    /**
     * Esegue l'assegnazione automatica dei badge allo studente in base ai dati del profilo personale.
     * I badge vengono assegnati solo se soddisfano i criteri e non sono già stati assegnati.
     * <p>
     * I criteri di assegnazione sono:
     * - "Ottimo Inizio" se lo studente ha almeno 100 punti
     * - "Macinatore di Task" se ha completato almeno 10 task
     * - "Esperto Totale" se ha almeno 200 punti e ha completato almeno 20 task
     *
     * @param esito Esito del caricamento del profilo personale dal database (deve essere 1 per continuare)
     * @param profiloPersonale Oggetto {@code EntityProfiloPersonale} contenente i dati aggiornati dello studente
     * @return {@code 1} se tutti i badge sono stati assegnati correttamente o erano già presenti,
     *         {@code 0} se i badge erano già presenti e nessuno è stato assegnato,
     *         {@code -1} se almeno una delle assegnazioni è fallita,
     *         oppure {@code esito} originale se il profilo non è stato caricato correttamente
     */
    private int assegnazioneAutomaticaBadge(int esito, EntityProfiloPersonale profiloPersonale) {
        if (esito == 1) {
            int esito1 = 1;
            int esito2 = 1;
            int esito3 = 1;
            int punti = profiloPersonale.getTotalePuntiOttenuti();
            int task = profiloPersonale.getNumeroTaskSvolti();
            // Badge da assegnare (puoi metterli fissi o leggerli da DAO)
            if (punti >= 100) {

                esito1 = profiloPersonale.assegnaBadgeSeNonPresente("Ottimo Inizio");
            }

            if (task >= 10){

                esito2 = profiloPersonale.assegnaBadgeSeNonPresente("Macinatore di Task");
            }
            if (punti >= 200 && task >= 20) {

                esito3 = profiloPersonale.assegnaBadgeSeNonPresente("Esperto Totale");
            }

            if (esito1 == -1 || esito2 == -1 || esito3 == -1 ) return -1;
            else if (esito1 == 0 || esito2 == 0 || esito3 == 0 ) return 0;
            else return 1;
        }
        return esito;
    }

    /**
     * Tenta l’accesso alla sezione delle classi virtuali per un docente.
     *
     * @param email     Email istituzionale del docente
     * @param password  Password inserita
     * @return Messaggio di esito dell’autenticazione
     */
    public  String accessoAlleClassiVirtuali(String email , String password) {

        EntityDocente docente = piattaformaDigitale.accessoAlleClassiVirtuali(email);
        if (docente != null) {
            if (password.equals(docente.getPassword())) {

                return "Accesso Effettuato con successo";
            }else{
                return "Password Incorretta";
            }
        }
        return "Errore Utente non trovato";
    }

    /**
     * Restituisce la lista di task attualmente assegnati allo studente specificato.
     *
     * @param emailStudente Email dello studente
     * @return Lista di task non ancora completati
     */
    public  List<TaskDidatticoDTO> ottieniTaskAssegnatiStudente(String emailStudente) {
        EntityStudente studenteAttuale = piattaformaDigitale.getStudenteByEmail(emailStudente);
        return studenteAttuale.getProfiloPersonale().ottieniTaskAssegnati();
    }

    /**
     * Restituisce la lista dei task attualmente assegnati da una classe.
     *
     * @param codiceClasse Codice identificativo della classe virtuale
     * @return Lista di task assegnati alla classe
     */
    public  List<TaskDidatticoDTO> ottieniTaskAssegnatiClasse(String codiceClasse, String emailDocente){
        EntityClasseVirtuale classeVirtuale = impostaTaskClasseAttuale(codiceClasse, emailDocente);
        List<EntityTaskDidattico> taskListAssegnati = classeVirtuale.getTaskAssegnati();
        List<TaskDidatticoDTO> dtoList = new ArrayList<>();
        for (EntityTaskDidattico task : taskListAssegnati) {
            dtoList.add(new TaskDidatticoDTO(task.getTitolo(), task.getDescrizione(), task.getDataDiScadenza(), task.getNumMaxDiPuntiAssegnabili()));
        }
        return dtoList;
    }

    /**
     * Restituisce l'elenco delle classi virtuali gestite da un docente.
     *
     * @param emailDocente Email del docente
     * @return Lista delle classi virtuali gestite
     */
    public  List<ClasseVirtualeDTO> ottieniClassiDelDocente(String emailDocente) {
        EntityDocente docenteAttuale =  piattaformaDigitale.getDocenteByEmail(emailDocente);
        docenteAttuale.setListaClassi(docenteAttuale.getListaClassiDaDB());
        ArrayList<EntityClasseVirtuale> entityClasseVirtualeArrayList =  docenteAttuale.getListaClassi();
        ArrayList<ClasseVirtualeDTO> dtoList = new ArrayList<>();
        for (EntityClasseVirtuale classeVirtuale : entityClasseVirtualeArrayList) {
            dtoList.add(new ClasseVirtualeDTO(classeVirtuale.getCodiceUnivoco(),classeVirtuale.getNome()));
        }
        return dtoList;
    }

    /**
     * Crea un nuovo task didattico per una specifica classe virtuale
     * e lo assegna automaticamente a tutti gli studenti iscritti alla classe.
     *
     * @param titolo            titolo del task
     * @param descrizione       descrizione del task
     * @param dataDiScadenza    data di scadenza del task
     * @param numeroMaxPunti    punteggio massimo assegnabile
     * @param codiceClasse      codice univoco della classe destinataria
     * @param emailDocente      email del docente che assegna il task
     * @return messaggio di esito dell'operazione
     */
    public  String creaTask(String titolo, String descrizione, LocalDate dataDiScadenza, int numeroMaxPunti , String codiceClasse, String emailDocente){
        EntityClasseVirtuale classeAttuale = impostaTaskClasseAttuale(codiceClasse, emailDocente);
        EntityTaskDidattico task = classeAttuale.creaTask(titolo, descrizione, dataDiScadenza,numeroMaxPunti,classeAttuale.getCodiceUnivoco());
            if (  task != null) {

                int esitoAssociazione = task.associaTaskAStudenti(titolo, codiceClasse);
                if (esitoAssociazione != -1){
                    return "Task creato con successo";
                }else{
                    return "Task non trovato o errore nel DB";
                }
            }
        return "Errore impossibile creare la task con questi dati, prova a cambiare titolo";
    }

    /**
     * Carica e imposta la lista dei task didattici assegnati alla classe identificata dal codice fornito,
     * appartenente al docente specificato. La lista di {@link TaskDidatticoDTO} viene recuperata dal database
     * e convertita in una lista di {@link EntityTaskDidattico}, che viene poi associata all'istanza di classe virtuale.
     *
     * @param codiceClasse  codice univoco della classe virtuale
     * @param emailDocente  email istituzionale del docente proprietario della classe
     * @return l'oggetto {@link EntityClasseVirtuale} con la lista aggiornata dei task assegnati
     */
    private EntityClasseVirtuale impostaTaskClasseAttuale(String codiceClasse, String emailDocente) {
        EntityDocente docenteAttuale = piattaformaDigitale.getDocenteByEmail(emailDocente);
        EntityClasseVirtuale classeAttuale = docenteAttuale.getClasseByCodiceUnivoco(codiceClasse);
        List<TaskDidatticoDTO> listaDTO = classeAttuale.getListaTaskAssegnati();
        List<EntityTaskDidattico> listaEntity = new ArrayList<>();
        for (TaskDidatticoDTO dto : listaDTO) {
            listaEntity.add(new EntityTaskDidattico(dto.getTitolo(), dto.getDescrizione(), dto.getDataDiScadenza(), dto.getNumMaxDiPuntiAssegnabili()));
        }
        classeAttuale.setTaskAssegnati(listaEntity); //necessario per tenere la lista dei task locale aggiornata.
        return classeAttuale;
    }

    /**
     * Restituisce l'elenco degli studenti che hanno completato almeno un task
     * all'interno di una determinata classe virtuale.
     *
     * @param codiceClasse codice della classe virtuale
     * @param emailDocente email del docente
     * @return lista di studenti con almeno una consegna
     */
    public List<StudenteDTO> ottieniStudentiConAlmenoUnTaskConsegnato(String codiceClasse, String emailDocente) {
        EntityDocente  docenteAttuale = piattaformaDigitale.getDocenteByEmail(emailDocente);
        EntityClasseVirtuale classeAttuale = docenteAttuale.getClasseByCodiceUnivoco(codiceClasse);

        return classeAttuale.getStudentiConTaskConsegnato();
    }

    /**
     * Restituisce l'elenco dei task completati da uno studente specifico.
     *
     * @param emailStudente email istituzionale dello studente
     * @return lista di task già consegnati
     */
    public List<TaskDidatticoDTO> ottieniTaskConsegnatiDaStudente(String emailStudente) {
        EntityStudente studenteAttuale = piattaformaDigitale.getStudenteByEmail(emailStudente);
        List<TaskDidatticoDTO> entityList = studenteAttuale.getProfiloPersonale().ottieniTaskConsegnatiDaStudente();
        List<dto.TaskDidatticoDTO> dtoList = new ArrayList<>();
        for (TaskDidatticoDTO task : entityList) {
            dtoList.add(new dto.TaskDidatticoDTO(task.getTitolo(), task.getDescrizione(), task.getDataDiScadenza(), task.getNumMaxDiPuntiAssegnabili()));
        }
        return dtoList;
    }
}

package entity;

import database.DocenteDAO;
import database.StudenteDAO;

import java.util.ArrayList;

/**
 * Rappresenta il contenitore principale della piattaforma digitale,
 * che gestisce l'elenco degli studenti e dei docenti iscritti.
 * <p>
 * Implementa il pattern Singleton per garantire l'esistenza di una sola
 * istanza condivisa in tutto il sistema.
 * <p>
 * Permette operazioni come registrazione, accesso e recupero degli utenti
 * mediante indirizzo email. Integra le funzionalità DAO per il salvataggio
 * e recupero dei dati da e verso il database
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class EntityPiattaformaDigitale {

    private static EntityPiattaformaDigitale instance = null;
    private ArrayList<EntityDocente> docentiIscritti;
    private ArrayList<EntityStudente> studentiIscritti;

    /**
     * Costruttore privato per implementare il pattern Singleton.
     */
    private EntityPiattaformaDigitale() {
        this.docentiIscritti = new ArrayList<>();
        this.studentiIscritti = new ArrayList<>();
    }

    /**
     * Restituisce l'unica istanza della piattaforma digitale.
     *
     * @return istanza Singleton della piattaforma
     */
    public static EntityPiattaformaDigitale getInstance() {
        if (instance == null) {
            instance = new EntityPiattaformaDigitale();
        }
        return instance;
    }

    /**
     * Registra un nuovo studente nella piattaforma e lo salva nel database.
     *
     * @param nome     nome dello studente
     * @param cognome  cognome dello studente
     * @param email    email istituzionale dello studente
     * @param password password di accesso
     * @return 1 se l'operazione è andata a buon fine, -1 altrimenti
     */
    public int registraStudente(String nome, String cognome, String email, String password) {
        EntityStudente studente = new EntityStudente();

        int ret = studente.scriviSuDB(email, nome, cognome, password);
        if (ret != -1) {
            studente.setNome(nome);
            studente.setCognome(cognome);
            studente.setPassword(password);
            studente.setEmail(email);
            studentiIscritti.add(studente);
        }
        return ret;
    }

    /**
     * Registra un nuovo docente nella piattaforma e lo salva nel database.
     *
     * @param nome     nome del docente
     * @param cognome  cognome del docente
     * @param email    email istituzionale del docente
     * @param password password di accesso
     * @return 1 se l'operazione ha successo, -1 in caso di errore
     */
    public int registraDocente(String nome, String cognome, String email, String password) {
        EntityDocente docente = new EntityDocente();
        int ret = docente.scriviSuDB(email, nome, cognome, password);
        if (ret != -1) {
            docente.setNome(nome);
            docente.setCognome(cognome);
            docente.setPassword(password);
            docente.setEmail(email);
            docentiIscritti.add(docente);
        }
        return ret;
    }

    /**
     * Effettua l’accesso di un docente alle classi virtuali gestite,
     * caricandone i dati dal database.
     *
     * @param email indirizzo email del docente
     * @return oggetto docente se il caricamento è riuscito, null altrimenti
     */
    public EntityDocente accessoAlleClassiVirtuali(String email) {
        EntityDocente docente = getDocenteByEmail(email);
        int esito = docente.accessoAlleClassiVirtuali(email);
        if (esito == 1) {
            return docente;
        } else return null;
    }

    /**
     * Effettua l’accesso al profilo personale di uno studente.
     *
     * @param email indirizzo email dello studente
     * @return oggetto studente se il caricamento ha avuto successo, null altrimenti
     */
    public EntityStudente accessoAlProfiloPersonale(String email) {
        EntityStudente studente = getStudenteByEmail(email);
        if (studente != null) {
            int esito = studente.accessoAlProfiloPersonale(email);
            if (esito == 1) {
                return studente;
            }
        } return null;
    }

    /**
     * Restituisce un docente iscritto cercandolo tramite l'indirizzo email
     * all'interno della lista interna della piattaforma digitale.
     *
     * @param email email del docente
     * @return docente corrispondente, oppure null se non trovato
     */
    public EntityDocente getDocenteByEmail(String email) {
        for (EntityDocente docente : this.docentiIscritti) {
            if (docente.getEmail().equals(email)) {
                return docente;
            }
        }
        return null;
    }

    /**
     * Restituisce uno studente iscritto cercandolo tramite l'indirizzo email
     * all'interno della lista interna della piattaforma digitale.
     *
     * @param email email dello studente
     * @return studente corrispondente, oppure null se non trovato
     */
    public EntityStudente getStudenteByEmail(String email) {
        for (EntityStudente studente : this.studentiIscritti) {
            if (studente.getEmail().equals(email)) {
                return studente;
            }
        }
        return null;
    }

    /**
     * Restituisce la lista aggiornata di tutti i docenti iscritti,
     * recuperandola dal database.
     *
     * @return lista di docenti presenti nel sistema
     */
    public ArrayList<EntityDocente> getDocentiIscritti() {
        ArrayList<EntityDocente> listaDocentiIscritti = new ArrayList<>();
        DocenteDAO docenteDAO = new DocenteDAO();
        ArrayList<DocenteDAO> listDocenteDAO = docenteDAO.getListaDocenti();
        for (DocenteDAO docenteDAO1 : listDocenteDAO) {
            String emailDocente = docenteDAO1.getIndirizzoEmailIstituzionale();
            String nome = docenteDAO1.getNome();
            String cognome = docenteDAO1.getCognome();
            String password = docenteDAO1.getPassword();

            EntityDocente temp = new EntityDocente(nome, cognome, emailDocente,password);

            listaDocentiIscritti.add(temp);
        }
        return listaDocentiIscritti;
    }

    /**
     * Restituisce la lista aggiornata di tutti gli studenti iscritti,
     * recuperandola dal database.
     *
     * @return lista di studenti presenti nel sistema
     */
    public ArrayList<EntityStudente> getStudentiIscritti() {
        ArrayList<EntityStudente> listaStudentiIscritti = new ArrayList<>();
        StudenteDAO studenteDAO = new StudenteDAO();
        ArrayList<StudenteDAO> listStudentiDAO = (ArrayList<StudenteDAO>) studenteDAO.getListaStudenti();
        for (StudenteDAO studenteDAO1 : listStudentiDAO) {
            String emailStudente = studenteDAO1.getIndirizzoEmailIstituzionale();
            String nomeStudente = studenteDAO1.getNome();
            String cognomeStudente = studenteDAO1.getCognome();
            String passwordStudente = studenteDAO1.getPassword();
            String classeStudente = studenteDAO1.getClasseVirtualeCodiceUnivoco();

            EntityStudente temp = new EntityStudente(nomeStudente, cognomeStudente,emailStudente, passwordStudente, classeStudente);
            listaStudentiIscritti.add(temp);
        }
        return listaStudentiIscritti;
    }

    /**
     * Imposta la lista dei docenti attualmente registrati.
     *
     * @param docentiIscritti lista di docenti
     */
    public void setDocentiIscritti(ArrayList<EntityDocente> docentiIscritti) {
        this.docentiIscritti = docentiIscritti;
    }

    /**
     * Imposta la lista degli studenti attualmente registrati.
     *
     * @param studentiIscritti lista di studenti
     */
    public void setStudentiIscritti(ArrayList<EntityStudente> studentiIscritti) {
        this.studentiIscritti = studentiIscritti;
    }
}

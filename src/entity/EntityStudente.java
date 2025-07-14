package entity;

import database.StudenteDAO;

/**
 * Rappresenta un'entità studente nel sistema.
 * <p>
 * Ogni studente è caratterizzato da nome, cognome, indirizzo email istituzionale,
 * password e, facoltativamente, un'associazione a una classe virtuale.
 * Dispone inoltre di un {@link EntityProfiloPersonale}, che traccia l'attività
 * e i progressi dello studente.
 * <p>
 * La classe fornisce metodi per accedere e modificare i dati dello studente,
 * interfacciandosi con il livello DAO per la persistenza nel database.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class EntityStudente {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String codiceClasseAssociata; //può essere null --> può non appartenere a una classe
    private EntityProfiloPersonale profiloPersonale;

    /**
     * Costruttore di default. Inizializza un nuovo studente con un profilo personale vuoto.
     */
    public EntityStudente() {
        this.profiloPersonale = new EntityProfiloPersonale();
    }

    /**
     * Costruttore parametrico. Inizializza lo studente con i dati specificati.
     *
     * @param nome                  nome dello studente
     * @param cognome               cognome dello studente
     * @param email                 indirizzo email istituzionale
     * @param password              password dell'account
     * @param codiceClasseAssociata codice della classe virtuale a cui è iscritto (può essere null)
     */
    public EntityStudente(String nome, String cognome, String email, String password,String codiceClasseAssociata) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.codiceClasseAssociata = codiceClasseAssociata;
        if (recuperaProfiloPersonale() == -1) this.profiloPersonale.creaProfiloPersonale(this.email);
    }

    /**
     * Carica i dati dello studente dal database utilizzando l'indirizzo email.
     *
     * @param email indirizzo email dello studente
     * @return 1 se il caricamento è andato a buon fine, -1 in caso di errore
     */
    public int accessoAlProfiloPersonale(String email){
        StudenteDAO studenteDAO = new StudenteDAO();
        studenteDAO.setIndirizzoEmailIstituzionale(email);
        int ret = studenteDAO.caricaDaDB();
        if (ret != -1){
            this.nome = studenteDAO.getNome();
            this.password = studenteDAO.getPassword();
            this.cognome = studenteDAO.getCognome();
            this.email = email;
            this.codiceClasseAssociata = studenteDAO.getClasseVirtualeCodiceUnivoco();
        }
        return ret;
    }

    /**
     * Scrive i dati dello studente nel database.
     *
     * @param email    indirizzo email
     * @param nome     nome dello studente
     * @param cognome  cognome dello studente
     * @param password password dello studente
     * @return 1 se l'operazione è andata a buon fine, -1 in caso di errore
     */
    public int scriviSuDB(String email, String nome, String cognome, String password) {
        StudenteDAO studenteDAO = new StudenteDAO();
        studenteDAO.setClasseVirtualeCodiceUnivoco(null);
        int ret = studenteDAO.scriviSuDB(email,nome, cognome, password);
        if(ret != -1){
            this.setEmail(email);
            this.setNome(nome);
            this.setCognome(cognome);
            this.setPassword(password);
        }
        return ret;
    }

    /**
     * Crea un nuovo profilo personale per lo studente e lo associa all'email corrente.
     *
     * @return 1 se la creazione ha avuto successo, -1 in caso di errore
     */
    public int creaProfiloPersonale(){
        int ret = this.profiloPersonale.creaProfiloPersonale(this.email);
        if(ret != -1){
            this.profiloPersonale.setEmailStudente(this.email);
        }
        return ret;
    }

    /**
     * Recupera i dati del profilo personale dallo studente tramite DAO
     * e carica le attivita relative a quel profilo.
     *
     * @return 1 se il caricamento ha avuto successo, -1 in caso di errore
     */
    public int recuperaProfiloPersonale(){
        EntityProfiloPersonale profiloPersonaleRecuperato = new EntityProfiloPersonale();
        int esito = profiloPersonaleRecuperato.ottieniDaDB(this.email);
        if(esito != -1){
            this.profiloPersonale= profiloPersonaleRecuperato;
            this.profiloPersonale.caricaAttivitaDaDB(this.email);
        }
        return esito;
    }

    //  Getter e Setter

    /**
     * Restituisce il nome dello studente.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dello studente.
     *
     * @param nome nome da assegnare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dello studente.
     *
     * @return cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dello studente.
     *
     * @param cognome cognome da assegnare
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'indirizzo email dello studente.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'indirizzo email dello studente.
     *
     * @param email nuovo indirizzo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Imposta la password dello studente.
     *
     * @param password nuova password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce la password dello studente.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Restituisce l'oggetto {@link EntityProfiloPersonale} associato allo studente.
     *
     * @return profilo personale
     */
    public EntityProfiloPersonale getProfiloPersonale() {
        return profiloPersonale;
    }

    /**
     * Restituisce il codice della classe virtuale a cui lo studente è iscritto.
     *
     * @return codice della classe virtuale o null se non assegnato
     */
    public String getCodiceClasseAssociata() {
        return this.codiceClasseAssociata;
    }

    /**
     * Imposta la classe associato allo studente.
     *
     * @param codiceClasseAssociata nuovo codice della classe associata.
     */
    public void setCodiceClasseAssociata(String codiceClasseAssociata) {
        this.codiceClasseAssociata = codiceClasseAssociata;
    }

}

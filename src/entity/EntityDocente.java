package entity;

import database.ClasseVirtualeDAO;
import database.DocenteDAO;
import java.util.ArrayList;

/**
 * Rappresenta un docente registrato nel sistema della piattaforma didattica.
 * Ogni docente è identificato da nome, cognome, email e password, e può gestire
 * una lista di classi virtuali.
 * <p>
 * La creazione delle classi è gestita direttamente dal docente attraverso
 * un contenimento stretto: se il docente viene rimosso, anche le sue classi
 * virtuali collegate devono essere eliminate per mantenere la coerenza dei dati.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class EntityDocente {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private ArrayList<EntityClasseVirtuale> listaClassi;

    /**
     * Costruttore di default che inizializza l'oggetto docente senza parametri.
     */
    public EntityDocente() {
        super();
    }

    /**
     * Costruttore che inizializza l'oggetto docente con i dati forniti.
     *
     * @param nome      il nome del docente
     * @param cognome   il cognome del docente
     * @param email     l'indirizzo email del docente
     * @param password  la password associata all'account del docente
     */
    public EntityDocente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.listaClassi = new ArrayList<>();
    }

    /**
     * Registra il docente nel database.
     *
     * @param email     email da registrare
     * @param nome      nome del docente
     * @param cognome   cognome del docente
     * @param password  password di accesso
     * @return 1 se il salvataggio ha avuto successo, -1 in caso di errore
     */
    public int scriviSuDB(String email, String nome, String cognome, String password){
        DocenteDAO docenteDAO = new DocenteDAO();
        int ret = docenteDAO.scriviSuDB(email,nome,cognome,password);
        if (ret != -1) {
            this.setNome(docenteDAO.getNome());
            this.setCognome(docenteDAO.getCognome());
            this.setPassword(docenteDAO.getPassword());
            this.setEmail(docenteDAO.getIndirizzoEmailIstituzionale());
        }
        return ret;
    }

    /**
     * Effettua il login del docente e carica i suoi dati dal database.
     *
     * @param email email del docente da cercare
     * @return 1 se i dati sono stati caricati correttamente, -1 in caso contrario
     */
    public int accessoAlleClassiVirtuali(String email){
        DocenteDAO docenteDAO = new DocenteDAO();
        docenteDAO.setIndirizzoEmailIstituzionale(email);
        int ret = docenteDAO.caricaDaDB();
        if (ret != -1) {
            this.setNome(docenteDAO.getNome());
            this.setPassword(docenteDAO.getPassword());
            this.setCognome(docenteDAO.getCognome());
            this.setEmail(docenteDAO.getIndirizzoEmailIstituzionale());
        }
        return ret;
    }

    /**
     * Restituisce la lista delle classi virtuali associate al docente,
     * caricandole direttamente dal database.
     *
     * @return lista di {@link EntityClasseVirtuale} gestite dal docente
     */
    public ArrayList<EntityClasseVirtuale> getListaClassiDaDB() {
        ArrayList<EntityClasseVirtuale> classiVirtualiDocente = new ArrayList<>();
        ClasseVirtualeDAO classeVirtualeDAO=  new ClasseVirtualeDAO();
        ArrayList<ClasseVirtualeDAO> classiDocente =  classeVirtualeDAO.getListaClassiDelDocente(this.email);
        for (ClasseVirtualeDAO classi : classiDocente) {
            String codiceUnivoco = classi.getCodiceUnivoco();
            String nomeClasse = classi.getNome();

            EntityClasseVirtuale temp = new EntityClasseVirtuale(codiceUnivoco, nomeClasse);
            classiVirtualiDocente.add(temp);

        }
        return classiVirtualiDocente;
    }

    /**
     * Restituisce una classe virtuale a partire dal suo codice univoco.
     *
     * @param codiceUnivoco codice identificativo della classe
     * @return la classe corrispondente oppure {@code null} se non trovata
     */
    public EntityClasseVirtuale getClasseByCodiceUnivoco(String codiceUnivoco) {
        for (EntityClasseVirtuale classe : this.listaClassi) {
            if (classe.getCodiceUnivoco().equalsIgnoreCase(codiceUnivoco)) {
                return classe;
            }
        }
        return null;
    }

    // Getter e Setter

    /**
     * Restituisce il nome del docente.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del docente.
     *
     * @param nome nome da assegnare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome del docente.
     *
     * @return cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome del docente.
     *
     * @param cognome cognome da assegnare
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'indirizzo email del docente.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'indirizzo email del docente.
     *
     * @param email email da assegnare
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce la password del docente.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password del docente.
     *
     * @param password password da assegnare
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce la lista interna delle classi associate all'istanza docente.
     *
     * @return lista di classi virtuali
     */
    public ArrayList<EntityClasseVirtuale> getListaClassi() {
        return listaClassi;
    }

    /**
     * Imposta la lista delle classi virtuali gestite dal docente.
     *
     * @param listaClassi lista di {@link EntityClasseVirtuale}
     */
    public void setListaClassi(ArrayList<EntityClasseVirtuale> listaClassi) {
        this.listaClassi = listaClassi;
    }
}

package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * La classe BadgeDAO rappresenta un oggetto che interagisce con il database
 * per recuperare e gestire i dati di un badge. Un badge è identificato dal suo nome
 * e può contenere una descrizione e un flag che indica se è associata
 * un'immagine. La classe fornisce metodi per caricare i dati di un badge dal database e
 * per accedere e modificare le proprietà del badge.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class BadgeDAO {
    private String nome;
    private String descrizione;
    private Boolean immagine;
    private ArrayList<RiconoscimentoDAO> listaRiconoscimento;

    /**
     * Costruttore di default. Crea un oggetto BadgeDAO senza inizializzare
     * alcuna proprietà.
     */
    public BadgeDAO() {
        super();
    }

    /**
     * Carica i dettagli del badge dal database in base al nome del badge.
     * Se il badge esiste, i suoi dati verranno recuperati e impostati negli
     * attributi della classe. In caso contrario, verrà stampato un messaggio
     * che segnala l'assenza del badge nel database.
     */
    public int caricaDaDB(){
        String query = "SELECT * FROM Badge WHERE Nome = '" + this.nome + "'";
        try{
            ResultSet rs = DBManager.selectQuery(query);
            if (rs.next()) {
                this.nome = rs.getString("Nome");
                this.setDescrizione(rs.getString("Descrizione"));
                this.setImmagine(rs.getBoolean("Immagine"));

                return 1;
            } else {
                return 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Restituisce il nome del badge.
     *
     * @return Il nome del badge.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del badge.
     *
     * @param nome Il nome del badge da impostare.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la descrizione del badge.
     *
     * @return La descrizione del badge.
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la descrizione del badge.
     *
     * @param descrizione La descrizione del badge da impostare.
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisce un valore booleano che indica se il badge ha un'immagine associata.
     *
     * @return {@code true} se il badge ha un'immagine, {@code false} altrimenti.
     */
    public Boolean getImmagine() {
        return immagine;
    }

    /**
     * Imposta se il badge ha un'immagine associata.
     *
     * @param immagine <code>true</code> se il badge ha un'immagine, <code>false</code> altrimenti.
     */
    public void setImmagine(Boolean immagine) {
        this.immagine = immagine;
    }

    /**
     * Restituisce la lista dei riconoscimenti ottenuti,
     * rappresentati da oggetti {@link RiconoscimentoDAO}.
     *
     * @return una lista di oggetti {@link RiconoscimentoDAO} associati
     */
    public ArrayList<RiconoscimentoDAO> getListaRiconoscimento() {
        return listaRiconoscimento;
    }

    /**
     * Imposta la lista dei riconoscimenti ottenuti,
     * rappresentata da oggetti {@link RiconoscimentoDAO}.
     *
     * @param listaRiconoscimento una lista di oggetti {@link RiconoscimentoDAO} da assegnare
     */
    public void setListaRiconoscimento(ArrayList<RiconoscimentoDAO> listaRiconoscimento) {
        this.listaRiconoscimento = listaRiconoscimento;
    }
}

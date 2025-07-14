package entity;

import java.util.ArrayList;

/**
 * Rappresenta un badge assegnato a uno studente o utente all'interno del sistema.
 * <p>
 * Ogni badge ha un nome, una descrizione, una data di ottenimento e un'indicazione
 * se è accompagnato da un'immagine o meno.
 * I badge possono essere utilizzati per riconoscere risultati o traguardi raggiunti.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class EntityBadge {
    private String nome;
    private String descrizione;
    private boolean immagine;
    private ArrayList<EntityRiconoscimento> listaRiconoscimento;

    /**
     * Costruttore che inizializza un badge con il solo nome.
     *
     * @param nome nome identificativo del badge
     */
    public EntityBadge(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il nome del badge.
     *
     * @return nome del badge
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del badge.
     *
     * @param nome nuovo nome del badge
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la descrizione del badge.
     *
     * @return descrizione testuale
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la descrizione del badge.
     *
     * @param descrizione nuova descrizione testuale
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Verifica se il badge è associato ha un'immagine.
     *
     * @return true se ha immagine, false altrimenti
     */
    public boolean isImmagine() {
        return immagine;
    }

    /**
     * Imposta se il badge è accompagnato da un'immagine.
     *
     * @param immagine valore booleano indicante la presenza dell'immagine
     */
    public void setImmagine(boolean immagine) {
        this.immagine = immagine;
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

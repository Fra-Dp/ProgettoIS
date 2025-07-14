package entity;

import java.time.LocalDate;

/**
 * Rappresenta un riconoscimento (badge) ottenuto da uno studente.
 * Ogni oggetto di questa classe contiene le informazioni relative all'indirizzo email
 * dello studente che ha ricevuto il badge, al nome del badge stesso e alla data di ottenimento.
 * <p>
 * Questa entità può essere utilizzata per visualizzare, salvare o elaborare i riconoscimenti
 * degli studenti all'interno del sistema.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 * <p>
 * NOTA: questa classe non è effettivamente utilizzata al momento ma potrebbe tornare utile per futuri sviluppi.
 */
public class EntityRiconoscimento {
    private EntityProfiloPersonale profiloPersonaleAssociato;
    private EntityBadge badgeAssociato;
    private LocalDate dataDiOttenimento;

    /**
     * Costruttore di default. Inizializza un oggetto riconoscimento vuoto.
     */
    public EntityRiconoscimento(){
        super();
    }

    /**
     * Restituisce il profilo personale dello studente a cui è stato assegnato il badge.
     *
     * @return l'email dello studente
     */
    public EntityProfiloPersonale getProfiloPersonaleAssociato() {
        return profiloPersonaleAssociato;
    }

    /**
     * Imposta il profilo personale dello studente che ha ottenuto il badge.
     *
     * @param profiloPersonaleAssociato l'email dello studente
     */
    public void setProfiloPersonaleAssociato(EntityProfiloPersonale profiloPersonaleAssociato) {
        this.profiloPersonaleAssociato = profiloPersonaleAssociato;
    }

    /**
     * Restituisce il nome del badge ottenuto dallo studente.
     *
     * @return il nome del badge
     */
    public EntityBadge getBadgeAssociato() {
        return badgeAssociato;
    }

    /**
     * Imposta il nome del badge ottenuto dallo studente.
     *
     * @param badgeAssociato il nome del badge
     */
    public void setBadgeAssociato(EntityBadge badgeAssociato) {
        this.badgeAssociato = badgeAssociato;
    }

    /**
     * Restituisce la data in cui il badge è stato ottenuto.
     *
     * @return la data di ottenimento
     */
    public LocalDate getDataDiOttenimento() {
        return dataDiOttenimento;
    }

    /**
     * Imposta la data in cui il badge è stato ottenuto dallo studente.
     *
     * @param dataDiOttenimento la data di ottenimento del badge
     */
    public void setDataDiOttenimento(LocalDate dataDiOttenimento) {
        this.dataDiOttenimento = dataDiOttenimento;
    }
}

package entity;

/**
 * Rappresenta l'entità {@code Attivita} nel modello a oggetti dell'applicazione.
 *
 * <p>
 * Questa classe mappa una riga della tabella {@code attivita} del database,
 * che rappresenta l'associazione tra uno studente e un task didattico,
 * e indica se il task è stato consegnato o meno.
 * <p>
 * Utilizzo: questa classe viene utilizzata come rappresentazione
 * diretta dell'entità per operazioni CRUD o mapping da/verso il database.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 * <p>
 * NOTA: questa classe non è effettivamente utilizzata al momento ma potrebbe tornare utile per futuri sviluppi
 */
public class EntityAttivita {
    private EntityProfiloPersonale profiloPersonaleAssociato;
    private EntityTaskDidattico taskAssociato;
    private boolean consegnato;

    /**
     * Costruttore di default.
     */
    public EntityAttivita(){
        super();
    }

    /**
     * Costruttore completo della classe {@link EntityAttivita}.
     * <p>
     * Crea un'attività associando un {@link EntityTaskDidattico} a un {@link EntityProfiloPersonale},
     * e specificando lo stato di consegna.
     *
     * @param profiloPersonale oggetto {@link EntityProfiloPersonale} dello studente a cui è assegnata l'attività
     * @param task oggetto {@link EntityTaskDidattico} che rappresenta il task didattico associato
     * @param consegnato true se il task è stato consegnato dallo studente, false altrimenti
     */
    public EntityAttivita(EntityProfiloPersonale profiloPersonale,EntityTaskDidattico task, boolean consegnato) {
        this.profiloPersonaleAssociato = profiloPersonale;
        this.taskAssociato = task;
        this.consegnato = consegnato;
    }

    /**
     * Restituisce l'indirizzo email dello studente.
     *
     * @return email istituzionale dello studente
     */
    public EntityProfiloPersonale getProfiloPersonaleAssociato() {
        return profiloPersonaleAssociato;
    }

    /**
     * Imposta l'indirizzo email dello studente.
     *
     * @param profiloPersonaleAssociato l'indirizzo email da assegnare
     */
    public void setProfiloPersonaleAssociato(EntityProfiloPersonale profiloPersonaleAssociato) {
        this.profiloPersonaleAssociato = profiloPersonaleAssociato;
    }

    /**
     * Restituisce il titolo del task associato.
     *
     * @return titolo del task
     */
    public EntityTaskDidattico getTaskAssociato() {
        return taskAssociato;
    }

    /**
     * Imposta il titolo del task associato.
     *
     * @param taskAssociato il titolo del task
     */
    public void setTaskAssociato(EntityTaskDidattico taskAssociato) {
        this.taskAssociato = taskAssociato;
    }

    /**
     * Verifica se il task è stato consegnato.
     *
     * @return {@code true} se il task è stato consegnato, {@code false} altrimenti
     */
    public boolean isConsegnato() {
        return consegnato;
    }

    /**
     * Imposta lo stato di consegna del task.
     *
     * @param consegnato {@code true} se consegnato, {@code false} altrimenti
     */
    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }
}

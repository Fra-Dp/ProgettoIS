package dto;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) per rappresentare un task didattico.
 * <p>
 * Questa classe incapsula i dati relativi a un task didattico,
 * come titolo che è l'identificativo, la descrizione, la data di scadenza
 * e il numero massimo di punti assegnabili. È utilizzata per
 * trasferire informazioni tra i vari livelli dell'applicazione
 * (es. Tra il livello di accesso ai dati e l'interfaccia utente)
 * senza esporre la logica interna delle entità del database.
 * <p>
 * Immutabilità: La classe è immutabile perché tutti i campi sono {@code final}
 * e non ci sono metodi setter.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class TaskDidatticoDTO {
    private final String titolo;
    private final String descrizione;
    private final LocalDate dataDiScadenza;
    private final int numMaxDiPuntiAssegnabili;

    /**
     * Costruttore per creare un oggetto {@code StudenteDTO}.
     *
     * @param titolo titolo identificativo del task
     * @param descrizione  descrizione del task
     * @param dataDiScadenza data di scadenza del task
     * @param puntiMassimi  numero massimo di punti assegnabili del task
     */
    public TaskDidatticoDTO(String titolo, String descrizione, LocalDate dataDiScadenza, int puntiMassimi) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataDiScadenza = dataDiScadenza;
        this.numMaxDiPuntiAssegnabili = puntiMassimi;
    }

    // Getter

    /**
     * Restituisce il titolo del task
     *
     * @return titolo come {@code String}
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Restituisce la descrizione del task
     *
     * @return descrizione come {@code String}
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce la data di scadenza del task
     *
     * @return data di scadenza come {@code LocalDate}
     */
    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    /**
     * Restituisce il numero massimo di punti assegnabili del task
     *
     * @return il numero massimo di punti assegnabili come {@code int}
     */
    public int getNumMaxDiPuntiAssegnabili() {
        return numMaxDiPuntiAssegnabili;
    }
}
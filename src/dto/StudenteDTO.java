package dto;

/**
 * Data Transfer Object (DTO) per rappresentare uno studente.
 * <p>
 * Questa classe incapsula i dati relativi a uno studente,
 * come l'email identificativa, il nome e il cognome. È utilizzata per
 * trasferire informazioni tra i vari livelli dell'applicazione
 * (es. Tra il livello di accesso ai dati e l'interfaccia utente)
 * senza esporre la logica interna delle entità del database.
 * <p>
 * Immutabilità: La classe è immutabile perché tutti i campi sono {@code final}
 * e non ci sono metodi setter.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class StudenteDTO {
    private final String nome;
    private final String cognome;
    private final String email;

    /**
     * Costruttore per creare un oggetto {@code StudenteDTO}.
     *
     * @param nome nome dello studente
     * @param cognome cognome dello studente
     * @param email email identificativa dello studente
     */
    public StudenteDTO(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    /**
     * Restituisce il nome dello studente.
     *
     * @return nome come {@code String}
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il cognome dello studente.
     *
     * @return cognome come {@code String}
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Restituisce l'email dello studente.
     *
     * @return email come {@code String}
     */
    public String getEmail() {
        return email;
    }
}

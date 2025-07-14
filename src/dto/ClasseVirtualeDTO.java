package dto;

/**
 * Data Transfer Object (DTO) per rappresentare una classe virtuale.
 * <p>
 * Questa classe incapsula i dati relativi a una classe virtuale,
 * come il codice univoco identificativo e il nome. È utilizzata per
 * trasferire informazioni tra i vari livelli dell'applicazione
 * (es. Tra il livello di accesso ai dati e l'interfaccia utente)
 * senza esporre la logica interna delle entità del database.
 * <p>
 * Immutabilità: La classe è immutabile perché tutti i campi sono {@code final}
 * e non ci sono metodi setter.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class ClasseVirtualeDTO {
    private final String codiceUnivoco;
    private final String nome;

    /**
     * Costruttore per creare un oggetto {@code ClasseVirtualeDTO}.
     *
     * @param codiceUnivoco codice univoco identificativo della classe
     * @param nome nome della classe
     */
    public ClasseVirtualeDTO(String codiceUnivoco, String nome) {
        this.codiceUnivoco = codiceUnivoco;
        this.nome = nome;
    }

    /**
     * Restituisce il codice univoco della classe virtuale.
     *
     * @return codice univoco come {@code String}
     */
    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    /**
     * Restituisce il nome della classe virtuale.
     *
     * @return nome come {@code String}
     */
    public String getNome() {
        return nome;
    }
}

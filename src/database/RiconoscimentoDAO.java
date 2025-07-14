package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Data Access Object (DAO) per la gestione dei riconoscimenti (badge) associati agli studenti.
 * <p>
 * Questa classe consente di:
 * -Verificare se uno studente ha già ottenuto un determinato badge
 * -Assegnare un nuovo badge allo studente con la data corrente
 * -Gestire i dati di riconoscimento tramite operazioni di lettura e scrittura nel database
 * <p>
 *
 * Le operazioni avvengono sulla tabella {@code riconoscimento},
 * e fanno riferimento alla relazione tra studente e badge, utilizzando l'indirizzo email come chiave primaria associativa.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class RiconoscimentoDAO {
    private ProfiloPersonaleDAO profiloPersonaleDAO;
    private BadgeDAO badgeAssociato;
    private LocalDate dataDiOttenimento;

    /**
     * Costruttore di default.
     * Crea un'istanza vuota di {@code RiconoscimentoDAO}.
     */
    public RiconoscimentoDAO(){
        super();
    }

    /**
     * Verifica se lo studente ha già ottenuto un badge specifico.
     *
     * @param email      l'indirizzo email istituzionale dello studente
     * @param nomeBadge  il nome del badge da verificare
     * @return {@code true} se il badge è già presente nel database per quello studente,
     *         {@code false} se non è ancora stato assegnato,
     *         {@code true} anche in caso di errore per evitare duplicati non voluti
     */
    public boolean studenteHaGiaBadge(String email, String nomeBadge) {

        String query = "SELECT * FROM riconoscimento WHERE profilo_personale_studente_IndirizzoEmailIstituzionale = '" + email + "' AND badge_Nome = '" + nomeBadge + "'";

        try {
            ResultSet rs = DBManager.selectQuery(query);
            return rs.next();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Assegna un nuovo badge allo studente registrando l’operazione nel database.
     * La data di ottenimento è impostata automaticamente al giorno corrente.
     *
     * @param email      l'indirizzo email dello studente destinatario
     * @param nomeBadge  il nome del badge da assegnare
     * @return {@code 1} se l'operazione è andata a buon fine,<br>
     *         {@code -1} in caso di errore SQL o di connessione
     */
    public int assegnaBadge(String email, String nomeBadge) {
        String query = "INSERT INTO riconoscimento  (profilo_personale_studente_IndirizzoEmailIstituzionale, badge_Nome, DataDiOttenimento) VALUES ('" + email + "', '" + nomeBadge +"', '"+ LocalDate.now()+"')";
        try {
            return DBManager.updateQuery(query);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //  Getter e Setter

    /**
     * Restituisce l'indirizzo email dello studente.
     *
     * @return email istituzionale dello studente
     */
    public ProfiloPersonaleDAO getProfiloPersonaleDAO() {
        return profiloPersonaleDAO;
    }

    /**
     * Imposta l'indirizzo email dello studente.
     *
     * @param profiloPersonaleDAO email istituzionale da associare
     */
    public void setProfiloPersonaleDAO(ProfiloPersonaleDAO profiloPersonaleDAO) {
        this.profiloPersonaleDAO = profiloPersonaleDAO;
    }

    /**
     * Restituisce il nome del badge assegnato.
     *
     * @return nome del badge
     */
    public BadgeDAO getBadgeAssociato() {
        return badgeAssociato;
    }

    /**
     * Imposta il nome del badge.
     *
     * @param badgeAssociato nome del badge da impostare
     */
    public void setBadgeAssociato(BadgeDAO badgeAssociato) {
        this.badgeAssociato = badgeAssociato;
    }

    /**
     * Restituisce la data in cui è stato ottenuto il badge.
     *
     * @return data di ottenimento
     */
    public LocalDate getDataDiOttenimento() {
        return dataDiOttenimento;
    }

    /**
     * Imposta la data di ottenimento del badge.
     *
     * @param dataDiOttenimento data da associare
     */
    public void setDataDiOttenimento(LocalDate dataDiOttenimento) {
        this.dataDiOttenimento = dataDiOttenimento;
    }
}

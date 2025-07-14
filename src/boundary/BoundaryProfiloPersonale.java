package boundary;

import control.Controller;
import dto.TaskDidatticoDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Questa classe rappresenta l'interfaccia grafica del profilo personale di uno studente.
 * Mostra una schermata principale con la possibilità di navigare verso:
 * - Task Assegnati (visualizzati in tabella)
 * - Statistiche (non ancora implementato)
 * - Badge ottenuti (non ancora implementato)
 * - Task completati (non ancora implementato)
 * <p>
 * Il layout utilizzato è un CardLayout che permette di cambiare pannello in base alle azioni dell'utente.
 * <p>
 * Funzionalità principali:
 * - Visualizzazione dei task assegnati con dettagli: Titolo, Descrizione, Scadenza, Punti Max.
 * - Tabella non modificabile che mostra i dati ottenuti dal Controller.
 * - Navigazione tra pannelli tramite pulsanti.
 * - Supporto alla chiusura e inizializzazione della finestra principale.
 * <p>
 * L'email dello studente viene passata come parametro al costruttore per capire chi è lo studente che sta operando su questo profilo personale.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class BoundaryProfiloPersonale extends JFrame {
    //COSTANTE
    Controller controller = Controller.getInstance();
    private static final String NONIMPLEMENTATO ="Funzione non Implementata";
    //Panel
    private JPanel mainPanel;
    private JPanel visualizzaElencoTaskAssegnati;
    private JPanel statistiche;
    private JPanel badgeOttenuti;
    private JPanel visualizzaElencoTaskCompletati;
    private JPanel profiloPersonale;
    //Bottoni
    private JButton statisticheButton;
    private JButton taskAssegnatiButton;
    private JButton badgeOttenutiButton;
    private JButton taskCompletatiButton;
    private JButton tornaAlProfiloPersonaleDaTaskAssegnati;
    private JButton tornaAlProfiloPersonaleDaStatistiche;
    private JButton tornaAlProfiloPersonaleDaBadge;
    private JButton tornaAlProfiloPersonaleDaTaskCompletati;
    //Tabella
    private JTable taskAssegnati;

    /**
     * Costruttore dell'interfaccia grafica del profilo personale.
     * Imposta i pannelli interni, i listener dei pulsanti e carica il layout iniziale.
     *
     * @param emailStudente l'indirizzo email istituzionale dello studente autenticato.
     */
    public BoundaryProfiloPersonale(String emailStudente) {
        setContentPane(mainPanel);
        setTitle("Profilo Personale");
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        mainPanel.add(profiloPersonale, "profiloPersonale");
        mainPanel.add(visualizzaElencoTaskAssegnati, "taskAssegnati");
        mainPanel.add(visualizzaElencoTaskCompletati, "taskCompletati");
        mainPanel.add(statistiche, "statistiche");
        mainPanel.add(badgeOttenuti, "badgeOttenuti");
        taskAssegnatiButton.addActionListener(_ -> {
            mostraTaskAssegnati(emailStudente);
            cardLayout.show(mainPanel, "taskAssegnati");
        });
        taskCompletatiButton.addActionListener(_ ->mostraMessaggio(NONIMPLEMENTATO));
        statisticheButton.addActionListener(_ -> mostraMessaggio(NONIMPLEMENTATO));
        badgeOttenutiButton.addActionListener(_ -> mostraMessaggio(NONIMPLEMENTATO));

        ActionListener tornaAlProfiloListener = _ -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "profiloPersonale");
        };

        tornaAlProfiloPersonaleDaBadge.addActionListener(tornaAlProfiloListener); // NON IMPLEMENTATO
        tornaAlProfiloPersonaleDaStatistiche.addActionListener(tornaAlProfiloListener); //  NON IMPLEMENTATO
        tornaAlProfiloPersonaleDaTaskAssegnati.addActionListener(tornaAlProfiloListener);
        tornaAlProfiloPersonaleDaTaskCompletati.addActionListener(tornaAlProfiloListener); //NON IMPLEMENTATO
    }

    /**
     * Recupera dal controller i task assegnati allo studente identificato dall'email
     * e li visualizza nella tabella. Ogni riga mostra titolo, descrizione, data di scadenza e punti massimi.
     * <p>
     * La tabella è non modificabile e si adatta alla larghezza dei contenuti.
     *
     * @param emailStudente l'indirizzo email dello studente.
     */
    private void mostraTaskAssegnati(String emailStudente) {
        List<TaskDidatticoDTO> taskList = controller.ottieniTaskAssegnatiStudente(emailStudente);
        if (taskList!= null) {
            BoundaryClasseVirtuale.generaTabellaDaListaTask(taskList, taskAssegnati);
        }else{
            mostraMessaggio("Errore nell ottenere la lista dei task");
        }
    }

    /**
     * Mostra un messaggio a schermo all'interno di un popup JOptionPane.
     *
     * @param messaggio il contenuto da visualizzare nel messaggio.
     */
    private void mostraMessaggio(String messaggio) {
        JOptionPane.showMessageDialog(null, messaggio);
    }
}




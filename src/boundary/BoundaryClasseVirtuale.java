package boundary;

import dto.StudenteDTO;
import dto.TaskDidatticoDTO;
import control.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


/**
 * La classe BoundaryClasseVirtuale rappresenta l'interfaccia grafica attraverso cui un docente può monitorare e gestire una classe virtuale.
 * <p>
 * Questa schermata consente al docente di:
 * - Visualizzare i task assegnati agli studenti della classe selezionata
 * - Visualizzare gli studenti che hanno completato un task e per ogni studente quali task sono stati completati
 * - Creare un nuovo task didattico con controlli sulla validità dei campi inseriti
 * - Tornare alla schermata delle classi associate al docente
 * <p>
 * L'interfaccia è costruita usando un layout a schede (CardLayout) che permette di navigare tra:
 * - monitoraggioClasse: vista principale di controllo
 * - taskAssegnati: lista dei task ancora da completare
 * - taskCompletati: lista dei task già svolti dagli studenti
 * - rendimentoMedio e partecipazioneStudenti (attualmente non implementati)
 * - creaTask: form per l'inserimento di un nuovo task
 * <p>
 * Sono implementate tutte le funzionalità principali con validazioni sui dati inseriti:
 * - Controlli su lunghezza e formato di titolo e descrizione
 * - Verifica della correttezza della data di scadenza (deve essere futura)
 * - Verifica del range accettabile per il numero massimo di punti (0–500)
 * <p>
 * La comunicazione con la logica applicativa avviene tramite il Controller.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class BoundaryClasseVirtuale extends JFrame {
    Controller controller = Controller.getInstance();
    //COSTANTI
    private static final String MONITORAGGIOCLASSE = "monitoraggioClasse";
    private static final String STUDENTICONTASKCOMPLETATO = "studentiConTaskCompletato";
    //BOTTONI
    private JButton taskAssegnatiButton;
    private JButton rendimentoMedioButton;
    private JButton livelloPartecipazioneButton;
    private JButton taskCompletatiButton;
    private JButton tornaAlleClassiButton;
    private JButton tornaAllaListaStudentiButton;
    private JButton tornaAlMonitoraggioButton;
    private JButton tornaAlMonitoraggioButton1;
    private JButton tornaAlMonitoraggioButton2;
    private JButton tornaAlMonitoraggioButton3;
    private JButton tornaAlMonitoraggioButton4;
    private JButton creaTaskButton;
    private JButton creaButton;
    //PANEL
    private JPanel mainPanel;
    private JPanel monitoraggioClassePanel;
    private JPanel rendimentoMedio;
    private JPanel taskAssegnati;
    private JPanel taskCompletatiDaSingoloStudente;
    private JPanel partecipazioneStudenti;
    private JPanel creaTask;
    private JPanel studentiConTaskCompletatoPanel;
    //TEXTFIELD
    private JTextField titoloField;
    private JTextField descrizioneField;
    private JTextField dataDiScadenzaField;
    private JTextField numeroMaxPuntiField;
    //TABELLE
    private JTable table1;
    private JTable taskCompletatiTable;
    private JTable studentiConTaskCompletatiTable;


    /**
     * Costruisce e visualizza l'interfaccia della classe virtuale, associata al docente autenticato.
     *
     * @param codiceClasse   Codice univoco della classe da monitorare.
     * @param emailDocente   Email istituzionale del docente autenticato.
     */
    public BoundaryClasseVirtuale(String codiceClasse, String emailDocente) {
        setContentPane(mainPanel);
        setTitle("Classe Virtuale");
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, MONITORAGGIOCLASSE);
        mainPanel.add(monitoraggioClassePanel, MONITORAGGIOCLASSE);
        mainPanel.add(taskAssegnati, "taskAssegnati");
        mainPanel.add(rendimentoMedio, "rendimentoMedio");
        mainPanel.add(taskCompletatiDaSingoloStudente, "taskCompletati");
        mainPanel.add(partecipazioneStudenti, "partecipazioneStudenti");
        mainPanel.add(creaTask, "creaTask");
        mainPanel.add(studentiConTaskCompletatoPanel, STUDENTICONTASKCOMPLETATO);

        ActionListener tornaAlMonitoraggio = _ -> cardLayout.show(mainPanel, MONITORAGGIOCLASSE);

        tornaAlMonitoraggioButton.addActionListener(tornaAlMonitoraggio);
        tornaAlMonitoraggioButton1.addActionListener(tornaAlMonitoraggio);
        tornaAlMonitoraggioButton2.addActionListener(tornaAlMonitoraggio);
        tornaAlMonitoraggioButton3.addActionListener(tornaAlMonitoraggio);
        tornaAlMonitoraggioButton4.addActionListener(tornaAlMonitoraggio);

        tornaAllaListaStudentiButton.addActionListener(_ -> cardLayout.show(mainPanel, STUDENTICONTASKCOMPLETATO));

        taskAssegnatiButton.addActionListener(_ ->{
            mostraTaskAssegnatiClasse(codiceClasse,emailDocente);
            cardLayout.show(mainPanel, "taskAssegnati");
        });

        creaTaskButton.addActionListener(_ -> cardLayout.show(mainPanel, "creaTask"));

        taskCompletatiButton.addActionListener(_ -> {
            mostraStudentiConTaskConsegnati(codiceClasse,emailDocente);
            cardLayout.show(mainPanel, STUDENTICONTASKCOMPLETATO);
        });

        rendimentoMedioButton.addActionListener(_ -> mostraMessaggio("Funzione non Implementata"));

        livelloPartecipazioneButton.addActionListener(_ -> mostraMessaggio("Funzione non Implementata"));

        tornaAlleClassiButton.addActionListener(_ -> {
            dispose();
            new BoundaryDocente(emailDocente);
        });


        studentiConTaskCompletatiTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = studentiConTaskCompletatiTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String emailStudente = (String) studentiConTaskCompletatiTable.getValueAt(selectedRow, 2);
                        mostraTaskConsegnatiStudente(emailStudente);
                        cardLayout.show(mainPanel, "taskCompletati");
                    }
                }
            }
        });


        //Crea TASK
        gestioneCreazioneTask(codiceClasse,emailDocente);
    }

    /**
     * Mostra l'elenco dei task attualmente assegnati alla classe selezionata.
     * I task vengono ottenuti tramite il Controller e visualizzati nella tabella associata.
     *
     * @param codiceClasse codice identificativo della classe virtuale.
     * @param emailDocente indirizzo email istituzionale del docente.
     */
    private void mostraTaskAssegnatiClasse(String codiceClasse,String emailDocente) {
        List<TaskDidatticoDTO> taskList = controller.ottieniTaskAssegnatiClasse(codiceClasse,emailDocente);

        generaTabellaDaListaTask(taskList, table1);
    }

    /**
     * Mostra i task che lo studente identificato dall'email ha consegnato.
     * I task sono ottenuti dal Controller e visualizzati in una tabella dedicata.
     *
     * @param emailStudente indirizzo email dello studente di cui visualizzare i task consegnati.
     */
    private void mostraTaskConsegnatiStudente(String emailStudente) {
        List<TaskDidatticoDTO> taskList = controller.ottieniTaskConsegnatiDaStudente(emailStudente);
        generaTabellaDaListaTask(taskList, taskCompletatiTable);
    }

    /**
     * Mostra l'elenco degli studenti della classe che hanno consegnato almeno un task.
     * I dati sono ottenuti tramite il Controller e visualizzati in una tabella.
     *
     * @param codiceClasse codice univoco della classe da analizzare.
     * @param emailDocente indirizzo email istituzionale del docente.
     */
    private void mostraStudentiConTaskConsegnati(String codiceClasse,String emailDocente) {
        List<StudenteDTO> studenti = controller.ottieniStudentiConAlmenoUnTaskConsegnato(codiceClasse, emailDocente);
        generaTabellaStudenti(studenti, studentiConTaskCompletatiTable);
    }

    /**
     * Controlla e gestisce la creazione di un nuovo task, validando i campi inseriti.
     * Se tutti i dati sono corretti, invia la richiesta al Controller.
     *
     * @param codiceClasse identificativo della classe in cui inserire il task.
     * @param emailDocente indirizzo email del docente che assegna il task.
     */
    private void gestioneCreazioneTask(String codiceClasse, String emailDocente) {
        creaButton.addActionListener(_ -> {
            String titolo = titoloField.getText().trim();
            String descrizione = descrizioneField.getText().trim();
            String dataInput = dataDiScadenzaField.getText().trim();
            String numeroMaxPuntiInseriti = numeroMaxPuntiField.getText().trim();
            // Validazione Titolo
            if (!validaTitolo(titolo)) {
                mostraMessaggio("Il titolo deve essere una stringa alfanumerica (con caratteri speciali ammessi) tra 5 e 50 caratteri.");
                return;
            }
            // Validazione Descrizione
            if (!validaDescrizione(descrizione)) {
                mostraMessaggio("La descrizione deve essere una stringa alfanumerica (con caratteri speciali ammessi) tra 10 e 500 caratteri.");
                return;
            }
            // Validazione e parsing Data
            LocalDate dataDiScadenza;
            try {
                dataDiScadenza = LocalDate.parse(dataInput);
                if (dataDiScadenza.isBefore(LocalDate.now())) {
                    mostraMessaggio("La data di scadenza deve essere successiva alla data odierna.");
                    return;
                }
            } catch (DateTimeParseException dtpe) {
                mostraMessaggio("Formato data non valido. Usa AAAA-MM-GG (es. 2030-12-30).");
                return;
            }
            // Validazione punti
            int numeroMaxPunti;
            try {
                numeroMaxPunti = Integer.parseInt(numeroMaxPuntiInseriti);
                if (numeroMaxPunti < 0 || numeroMaxPunti > 500) {
                    mostraMessaggio("Il numero massimo di punti deve essere compreso tra 0 e 500.");
                    return;
                }
            } catch (NumberFormatException ex) {
                mostraMessaggio("Inserisci un numero valido per i punti.");
                return;
            }

            // Tutti i controlli superati
            String risposta = controller.creaTask(titolo, descrizione, dataDiScadenza, numeroMaxPunti, codiceClasse, emailDocente);
            mostraMessaggio(risposta);
        });
    }

    /**
     * Genera dinamicamente una tabella popolata con i dati provenienti da una lista di TaskDidatticoDAO.
     * <p>
     * Ogni task verrà rappresentato come una riga nella tabella, con colonne per:
     * Titolo, Descrizione, Scadenza e Punti Massimi assegnabili. La tabella risultante
     * non è modificabile dall'utente.
     *
     * @param taskList lista di oggetti da visualizzare nella tabella
     * @param table1 la {@link JTable} da riempire con i dati
     */
    static void generaTabellaDaListaTask(List<TaskDidatticoDTO> taskList, JTable table1) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Titolo", "Descrizione", "Scadenza", "Punti Max"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (TaskDidatticoDTO task : taskList) {
            model.addRow(new Object[]{task.getTitolo(), task.getDescrizione(), task.getDataDiScadenza(), task.getNumMaxDiPuntiAssegnabili()});
        }

        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setFillsViewportHeight(true);
    }

    /**
     * Genera dinamicamente una tabella con le informazioni degli studenti (nome, cognome, email).
     * I dati sono passati come lista di StudenteDTO.
     *
     * <p>
     * Ogni studente verrà rappresentato come una riga nella tabella, con colonne per:
     * nome, cognome, email. La tabella risultante non è modificabile dall'utente.
     * </p>
     * @param studenti lista degli studenti da visualizzare.
     * @param table    JTable da riempire con i dati degli studenti.
     */
    static void generaTabellaStudenti(List<StudenteDTO> studenti, JTable table) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nome", "Cognome", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (StudenteDTO s : studenti) {
            model.addRow(new Object[]{s.getNome(), s.getCognome(), s.getEmail()});
        }

        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    /**
     * Verifica che il titolo sia una stringa valida (lunghezza tra 5 e 50 caratteri, caratteri Unicode ammessi).
     * I caratteri validi sono tutta la punteggiatura, caratteri alfanumerici, le lettere accentate.
     *
     * @param titolo stringa da controllare.
     * @return true se il titolo è valido, false altrimenti.
     */
    private boolean validaTitolo(String titolo) {
        return titolo.matches("^[\\p{L}\\p{N}\\p{Punct}\\s]{5,50}$");
    }

    /**
     * Verifica che la descrizione sia conforme alle specifiche (lunghezza tra 10 e 500 caratteri, caratteri Unicode ammessi).
     * I caratteri validi sono tutta la punteggiatura, caratteri alfanumerici, le lettere accentate.
     *
     * @param descrizione stringa da controllare.
     * @return true se valida, false altrimenti.
     */
    private boolean validaDescrizione(String descrizione) {
        return descrizione.matches("^[\\p{L}\\p{N}\\p{Punct}\\s]{10,500}$");
    }

    /**
     * Mostra un messaggio informativo o di errore in una finestra di dialogo.
     *
     * @param messaggio contenuto del messaggio da mostrare.
     */
    private void mostraMessaggio(String messaggio) {
        JOptionPane.showMessageDialog(null, messaggio);
    }
}

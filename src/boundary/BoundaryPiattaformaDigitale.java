package boundary;

import javax.swing.*;
import java.awt.*;
import control.Controller;

/**
 * Questa classe rappresenta l'interfaccia grafica principale della piattaforma digitale.
 * Consente agli utenti di registrarsi o accedere come Studente o Docente.
 * In base al ruolo selezionato, reindirizza l'utente all'interfaccia appropriata
 * (BoundaryProfiloPersonale o BoundaryDocente).
 * <p>
 * La classe gestisce:
 * - La navigazione tra le schermate (home, login, registrazione)
 * - La validazione dei dati inseriti
 * - Il routing verso le interfacce specifiche
 * <p>
 * Componenti principali:
 * - JPanel mainPanel con CardLayout
 * - JComboBox per selezione ruolo
 * - JTextField e JPasswordField per credenziali
 * - Pulsanti per navigazione e conferma
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class BoundaryPiattaformaDigitale extends JFrame {
    Controller controller = Controller.getInstance();
    //COSTANTI
    private static final String STUDENTE = "Studente";
    private static final String DOCENTE = "Docente";
    private static final String EMAILSTUDENTE = "@studente.it";
    private static final String EMAILDOCENTE = "@docente.it";
    //PANELS
    private JPanel mainPanel;
    private JPanel registerPanel;
    private JPanel loginPanel;
    private JPanel homePanel;
    // Bottoni
    private JButton registratiButton;
    private JButton accediButton;
    private JButton tornaAllaHomeButtonDaRegister;
    private JButton tornaAllaHomeDaLogin;
    private JButton confermaAccessoButton;
    private JButton confermaRegistrazioneButton;
    //CAMPI D'INSERIMENTO LOGIN
    private JPasswordField passwordLoginField1;
    private JTextField emailPrefixField;
    private JTextField emailSuffixField;
    //CAMPI D'INSERIMENTO REGISTRAZIONE
    private JTextField nomeField1;
    private JTextField cognomeField2;
    private JTextField emailRegistrazioneField3;
    private JPasswordField passwordRegistrazioneField2;
    //SCELTA DEL RUOLO
    private JComboBox<String> ruoloComboBox1;
    private JComboBox<String> ruoloComboBoxLogin;

    /**
     * Costruttore della classe. Inizializza la GUI e gestisce
     * il comportamento degli elementi grafici come pulsanti e combo box.
     */
    public BoundaryPiattaformaDigitale() {
        //CardLayout del panello principale
        CardLayout cardLayout1 = (CardLayout) mainPanel.getLayout();
        accediButton.addActionListener(_ ->cardLayout1.show(mainPanel, "Login"));
        registratiButton.addActionListener(_ ->cardLayout1.show(mainPanel, "Registrazione"));

        //pulsanti per tornare indietro
        tornaAllaHomeButtonDaRegister.addActionListener(_ ->cardLayout1.show(mainPanel, "Home"));
        tornaAllaHomeDaLogin.addActionListener(_ ->cardLayout1.show(mainPanel, "Home"));

        mainPanel.add(homePanel, "Home");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Registrazione");

        ruoloComboBox1.addItem(STUDENTE);
        ruoloComboBox1.addItem(DOCENTE);
        ruoloComboBoxLogin.addItem(STUDENTE);
        ruoloComboBoxLogin.addItem(DOCENTE);
        //PARTE REGISTRAZIONE
        gestioneRegistrazione();
        //PARTE LOGIN
        gestioneLogin();


    }

    /**
     * Gestisce la logica di registrazione per studente o docente.
     * Controlla la validità dei dati e richiama il Controller per la registrazione.
     */
    public void gestioneRegistrazione(){
        confermaRegistrazioneButton.addActionListener(_ -> {
            String nome = nomeField1.getText();
            String cognome = cognomeField2.getText();
            String email = emailRegistrazioneField3.getText();
            String password = new String(passwordRegistrazioneField2.getPassword());
            String ruolo = (String) ruoloComboBox1.getSelectedItem();


            if ( !controlloRegistrazione(nome, cognome, password)  || email.isEmpty()  || ruolo == null) {
                mostraMessaggio("Attento a rispettare la lunghezza compresa tra 3 e 30 caratteri e non lasciare campi vuoti!");
                return;
            }
            // Verifica email in base al ruolo
            boolean emailValida = ruolo.equals(DOCENTE) && email.endsWith(EMAILDOCENTE)
                    || (ruolo.equals(STUDENTE) && email.endsWith(EMAILSTUDENTE));

            if (!emailValida || email.length() <= 13 || email.length() > 65  || email.matches("^[a-zA-Z0-9.]+$")) {
                mostraMessaggio("L'email deve essere del tipo nome@" +
                                (ruolo.equals(DOCENTE) ? "docente.it" : "studente.it"));
            }else if (ruolo.equals(STUDENTE)) {
                String risposta = controller.registraStudente(nome, cognome, email, password);
                mostraMessaggio(risposta);
            }else {
                String risposta = controller.registraDocente(nome, cognome, email, password);
                mostraMessaggio(risposta);
            }
        });
    }

    /**
     * Gestisce il processo di login. Verifica i dati inseriti,
     * costruisce l'email e reindirizza all'interfaccia corretta.
     */
    public void gestioneLogin() {
        ruoloComboBoxLogin.addActionListener(_ -> {
            String ruolo = (String) ruoloComboBoxLogin.getSelectedItem();
            if (ruolo != null) {
                String suffix = ruolo.equals(DOCENTE) ? EMAILDOCENTE : EMAILSTUDENTE;
                emailSuffixField.setText(suffix);
            }
        });

        confermaAccessoButton.addActionListener(_ -> {
            String prefix = emailPrefixField.getText();
            String suffix = emailSuffixField.getText();
            String password = new String(passwordLoginField1.getPassword());

            if (inputNonValido(prefix, password)) {
                mostraMessaggio("Compila tutti i campi, potresti aver sbagliato il formato.");
                return;
            }

            String email = prefix + suffix;

            if (suffix.equals(EMAILSTUDENTE)) {
                gestisciAccessoStudente(email, password);
            } else if (suffix.equals(EMAILDOCENTE)) {
                gestisciAccessoDocente(email, password);
            } else {
                mostraMessaggio("Ruolo non riconosciuto.");
            }
        });
    }

    /**
     * Valida i campi di ingresso del login.
     *
     * @param prefix parte iniziale dell'email (senza dominio)
     * @param password la password inserita
     * @return true se uno dei campi è vuoto o contiene caratteri non validi
     */
    private boolean inputNonValido(String prefix, String password) {
        if (prefix.isEmpty() || password.isEmpty()) return true;
        return  !prefix.matches("^[a-zA-Z0-9.]+$");
    }

    /**
     * Gestisce l'accesso per gli studenti utilizzando la logica del Controller. In caso di esito positivo,
     * apre l'interfaccia {@link BoundaryProfiloPersonale}.
     *
     * @param email email completa dello studente
     * @param password password dello studente
     */
    private void gestisciAccessoStudente(String email, String password) {
        String risposta = controller.accessoAlProfiloPersonale(email, password);
        mostraMessaggio(risposta);

        if (risposta.equals("Accesso Effettuato con successo e Badge assegnati")) {
            new BoundaryProfiloPersonale(email);
            chiudiFinestraCorrente();
        }
    }

    /**
     * Gestisce l'accesso per i docenti. In caso di esito positivo,
     * apre l'interfaccia {@link BoundaryDocente}.
     *
     * @param email email completa del docente
     * @param password password del docente
     */
    private void gestisciAccessoDocente(String email, String password) {
        String risposta = controller.accessoAlleClassiVirtuali(email, password);
        mostraMessaggio(risposta);

        if (risposta.equals("Accesso Effettuato con successo")) {
            new BoundaryDocente(email);
            chiudiFinestraCorrente();
        }
    }

    /**
     * Chiude la finestra corrente.
     * Viene usato per chiudere la schermata di login/registrazione
     * dopo un accesso effettuato con successo.
     */
    private void chiudiFinestraCorrente() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        frame.dispose();
    }

    /**
     * Mostra un messaggio a schermo utilizzando una finestra di dialogo.
     *
     * @param messaggio messaggio da visualizzare
     */
    private void mostraMessaggio(String messaggio) {
        JOptionPane.showMessageDialog(null, messaggio);
    }

    /**
     * Esegue i controlli sui campi di registrazione (nome, cognome, password).
     * Ogni campo deve essere compreso tra 3 e 30 caratteri.
     * Nome: stringa con soli caratteri alfabetici.
     * Cognome: stringa con caratteri alfabetici e caratteri speciali come lo spazio e '.
     * Email: stringa con caratteri alfanumerici e con caratteri speciali di ogni tipo.
     *
     * @param nome nome inserito
     * @param cognome cognome inserito
     * @param password password inserita
     * @return true se i dati rispettano i criteri di validazione
     */
    private boolean controlloRegistrazione(String nome, String cognome, String password) {
        //Il nome deve essere >2 e <=30 e deve contenere solo caratteri normali
        if (nome.length() <=2 || nome.length() > 30 || !nome.matches("^[a-zA-Z]+$")) {  return false;} // questo è ASCII
        //controlli sul cognome >2 e <= 30 e deve contente solo caratteri normali
        if (cognome.length() <= 2 || cognome.length() > 30 || !cognome.matches("^[a-zA-Z'\\s]+$")) {  return false;}
        //controlli sulla password lunghezza >2 e <= 30 può contenere tutti i caratteri
        return password.length() > 2 && password.length() <= 30 && password.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]+$");
    }

    /**
     * Restituisce il pannello principale della finestra, utile per impostarlo
     * come contenuto della JFrame nella funzione main.
     *
     * @return JPanel principale contenente tutti i sotto-pannelli.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Metodo main. Avvia l'interfaccia della piattaforma digitale.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        //Crea nuovo oggetto
        BoundaryPiattaformaDigitale piattaforma = new BoundaryPiattaformaDigitale();
        //Imposta il panello principale come contenuto della finestra
        piattaforma.setContentPane(piattaforma.getMainPanel());
        piattaforma.setVisible(true);
        piattaforma.pack();
        piattaforma.setMinimumSize(new Dimension(600, 400));
        piattaforma.setLocationRelativeTo(null);
        piattaforma.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        piattaforma.setTitle("Piattaforma Digitale");
    }
}

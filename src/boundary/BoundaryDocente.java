package boundary;

import dto.ClasseVirtualeDTO;
import control.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Interfaccia grafica per il docente, che mostra l'elenco delle classi virtuali associate.
 * <p>
 * Questa finestra si apre dopo un login docente effettuato con successo.
 * L'interfaccia permette di:
 * - Visualizzare in una tabella tutte le classi create/gestite dal docente.
 * - Aprire una nuova interfaccia {@link BoundaryClasseVirtuale} al doppio clic su una riga della tabella.
 * - Intercettare la pressione del pulsante "Crea Classe" (funzione non ancora implementata).
 * <p>
 * I dati vengono recuperati tramite il controller da database.
 * <p>
 * Autori: Catini Simone, D'Avanzo Francesco, Del Verme Nicola, Di Pinto Francesco
 */
public class BoundaryDocente  extends JFrame {
    Controller controller = Controller.getInstance();

    private JPanel mainPanel;
    private JScrollPane visualizzaElencoClassi;
    private JTable table1;
    private JButton creaClasseButton;

    /**
     * Costruttore principale dell'interfaccia grafica per il docente.
     * Inizializza la finestra e mostra l'elenco delle classi virtuali associate al docente.
     * Prevede l'uso del mouse per rilevare il doppio clic su una classe ed entrare nei dettagli.
     *
     * @param email l'indirizzo email istituzionale del docente autenticato.
     */
    public BoundaryDocente(String email) {
            setContentPane(mainPanel);
            setTitle("Docente");
            setMinimumSize(new Dimension(600, 400));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            // Mostra le classi associate al docente
            mostraClassiAssociate(email);

            setVisible(true);
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && table1.getSelectedRow() != -1) {
                    int row = table1.getSelectedRow();

                    String codiceClasse = table1.getValueAt(row, 0).toString();

                    // Apri la nuova schermata con i dettagli
                    dispose();
                    new BoundaryClasseVirtuale(codiceClasse, email);

                }
            }
        });


        creaClasseButton.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Funzione non implementata"));
    }

    /**
     * Recupera dal controller l'elenco delle classi virtuali gestite dal docente
     * e le mostra all'interno della tabella. Ogni riga visualizza il codice univoco
     * e il nome della classe.
     * <p>
     * La tabella è non modificabile e si adatta alla larghezza dei contenuti.
     *
     * @param emailDocente email del docente per filtrare le classi associate.
     */
    private void mostraClassiAssociate(String emailDocente){
        java.util.List<ClasseVirtualeDTO> classeList = controller.ottieniClassiDelDocente(emailDocente);
        DefaultTableModel model = new DefaultTableModel(new Object[]{"CodiceUnivoco", "Nome"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (ClasseVirtualeDTO classe : classeList) {
            model.addRow(new Object[]{classe.getCodiceUnivoco(), classe.getNome()});
        }

        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setFillsViewportHeight(true);
    }
}

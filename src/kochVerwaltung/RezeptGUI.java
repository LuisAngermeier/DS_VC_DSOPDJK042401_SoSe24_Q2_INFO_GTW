package kochVerwaltung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RezeptGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private RezeptVerwaltung verwaltung;
    private JComboBox<String> rezeptDropdown;
    private JTextArea rezeptAnweisungen;
    private JTextArea zutatenAnzeige;
    private JButton addButton;
    private JButton removeButton;
    private JLabel schrittAnzeige;
    private int aktuelleAnweisungIndex;
    private JButton Weiter;

    public RezeptGUI(RezeptVerwaltung verwaltung) {
        this.verwaltung = verwaltung;
        setTitle("Rezept Verwaltung");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        rezeptDropdown = new JComboBox<>();
        for (Rezept rezept : verwaltung.getRezepte()) {
            rezeptDropdown.addItem(rezept.getTitel());
        }
        rezeptDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aktuelleAnweisungIndex = 0;
                updateAnweisungen();
                updateZutatenAnzeige();
            }
        });

        addButton = new JButton("Rezept hinzufügen");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRezept();
            }
        });

        topPanel.add(rezeptDropdown, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        rezeptAnweisungen = new JTextArea();
        rezeptAnweisungen.setEditable(false);

        zutatenAnzeige = new JTextArea();
        zutatenAnzeige.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(rezeptAnweisungen);
        centerPanel.add(scrollPane);
        
        Weiter = new JButton("Weiter");
        Weiter.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
       		 aktuelleAnweisungIndex++;
             updateAnweisungen();
             updateZutatenAnzeige();
        	}
        });
        scrollPane.setColumnHeaderView(Weiter);
        centerPanel.add(new JScrollPane(zutatenAnzeige));

        schrittAnzeige = new JLabel();
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(schrittAnzeige);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        
        removeButton = new JButton("Rezept entfernen");
        topPanel.add(removeButton, BorderLayout.WEST);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRezept();
            }
        });
        
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateAnweisungen() {
        String selectedRezeptTitel = (String) rezeptDropdown.getSelectedItem();
        Rezept rezept = verwaltung.getRezeptByTitel(selectedRezeptTitel);
        if (rezept != null) {
            if (aktuelleAnweisungIndex < rezept.getAnweisungen().size()) {
                rezeptAnweisungen.setText(rezept.getAnweisungen().get(aktuelleAnweisungIndex));
            } else {
                rezeptAnweisungen.setText("Alle Anweisungen abgeschlossen.");
                aktuelleAnweisungIndex = 0;
            }
            schrittAnzeige.setText("Schritt: " + (aktuelleAnweisungIndex + 1) + " von " + rezept.getAnweisungen().size());
        }
    }

    private void updateZutatenAnzeige() {
        String selectedRezeptTitel = (String) rezeptDropdown.getSelectedItem();
        Rezept rezept = verwaltung.getRezeptByTitel(selectedRezeptTitel);
        if (rezept != null) {
            if (aktuelleAnweisungIndex < rezept.getAnweisungen().size()) {
                StringBuilder zutatenText = new StringBuilder("Zutaten:\n");
                for (String beschreibung : rezept.getBeschreibungen()) {
                    zutatenText.append("- ").append(beschreibung).append("\n");
                }
                zutatenAnzeige.setText(zutatenText.toString());
            } else {
                zutatenAnzeige.setText("");
            }
        }
    }

    private void addRezept() {
        JTextField titelField = new JTextField();
        JTextField vorbereitungszeitField = new JTextField();
        JTextArea beschreibungArea = new JTextArea(5, 20);
        JTextArea anweisungArea = new JTextArea(5, 20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Titel:"));
        panel.add(titelField);
        panel.add(new JLabel("Vorbereitungszeit (in Minuten):"));
        panel.add(vorbereitungszeitField);
        panel.add(new JLabel("Beschreibung (jeweils in neuer Zeile):"));
        panel.add(new JScrollPane(beschreibungArea));
        panel.add(new JLabel("Anweisung (jeweils in neuer Zeile):"));
        panel.add(new JScrollPane(anweisungArea));

        int result = JOptionPane.showConfirmDialog(null, panel, "Neues Rezept hinzufügen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String titel = titelField.getText();
            int vorbereitungszeit;
            try {
                vorbereitungszeit = Integer.parseInt(vorbereitungszeitField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ungültige Vorbereitungszeit.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Rezept rezept = new Rezept(titel, vorbereitungszeit);
            for (String beschreibung : beschreibungArea.getText().split("\n")) {
                rezept.addBeschreibung(beschreibung);
            }
            for (String anweisung : anweisungArea.getText().split("\n")) {
                rezept.addAnweisung(anweisung);
            }

            String error = verwaltung.addRezept(rezept);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                rezeptDropdown.addItem(rezept.getTitel());
                JOptionPane.showMessageDialog(this, "Rezept hinzugefügt.");
            }
        }
    }

    private void removeRezept() {
        String selectedRezeptTitel = (String) rezeptDropdown.getSelectedItem();
        if (selectedRezeptTitel != null) {
            String error = verwaltung.removeRezept(selectedRezeptTitel);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                rezeptDropdown.removeItem(selectedRezeptTitel);
                rezeptAnweisungen.setText("");
                zutatenAnzeige.setText("");
                schrittAnzeige.setText("");
                JOptionPane.showMessageDialog(this, "Rezept entfernt.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Kein Rezept ausgewählt.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}

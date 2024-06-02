package kochVerwaltung;

import java.util.Scanner;

public class RezeptUI {
    private RezeptVerwaltung verwaltung;
    private Scanner scanner;

    public RezeptUI(RezeptVerwaltung verwaltung) {
        this.verwaltung = verwaltung;
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("1. Rezept hinzufügen");
            System.out.println("2. Rezept entfernen");
            System.out.println("3. Rezept anzeigen");
            System.out.println("4. Beenden");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addRezept();
                    break;
                case 2:
                    removeRezept();
                    break;
                case 3:
                    showRezept();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Ungültige Wahl!");
            }
        }
    }

    private void addRezept() {
        System.out.print("Titel: ");
        String titel = scanner.nextLine();
        System.out.print("Vorbereitungszeit (in Minuten): ");
        int vorbereitungszeit = scanner.nextInt();
        scanner.nextLine();

        Rezept rezept = new Rezept(titel, vorbereitungszeit);
        System.out.print("Beschreibung hinzufügen (leere Eingabe zum Beenden): ");
        while (true) {
            String beschreibung = scanner.nextLine();
            if (beschreibung.isEmpty()) {
                break;
            }
            rezept.addBeschreibung(beschreibung);
        }

        System.out.print("Anweisung hinzufügen (leere Eingabe zum Beenden): ");
        while (true) {
            String anweisung = scanner.nextLine();
            if (anweisung.isEmpty()) {
                break;
            }
            rezept.addAnweisung(anweisung);
        }

        String error = verwaltung.addRezept(rezept);
        if (error != null) {
            System.out.println("Fehler: " + error);
        } else {
            System.out.println("Rezept hinzugefügt!");
        }
    }

    private void removeRezept() {
        System.out.print("Titel des zu entfernenden Rezepts: ");
        String titel = scanner.nextLine();
        String error = verwaltung.removeRezept(titel);
        if (error != null) {
            System.out.println("Fehler: " + error);
        } else {
            System.out.println("Rezept entfernt!");
        }
    }

    private void showRezept() {
        System.out.print("Titel des anzuzeigenden Rezepts: ");
        String titel = scanner.nextLine();
        Rezept rezept = verwaltung.getRezeptByTitel(titel);
        if (rezept != null) {
            System.out.println("Titel: " + rezept.getTitel());
            System.out.println("Vorbereitungszeit: " + rezept.getVorbereitungszeit() + " Minuten");
            System.out.println("Beschreibungen: " + String.join(", ", rezept.getBeschreibungen()));
            System.out.println("Anweisungen: ");
            for (String anweisung : rezept.getAnweisungen()) {
                System.out.println("- " + anweisung);
            }
        } else {
            System.out.println("Rezept nicht gefunden!");
        }
    }
}

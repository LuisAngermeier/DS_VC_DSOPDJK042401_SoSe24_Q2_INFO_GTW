package kochVerwaltung;

import java.util.ArrayList;

public class RezeptVerwaltung {
    private ArrayList<Rezept> rezepte;

    public RezeptVerwaltung() {
        rezepte = new ArrayList<>();
    }

    public String addRezept(Rezept rezept) {
        if (rezept == null) {
            return "Rezept ist null.";
        }
        if (rezept.getTitel() == null || rezept.getTitel().isEmpty()) {
            return "Rezepttitel ist ungültig.";
        }
        if (rezept.getVorbereitungszeit() <= 0) {
            return "Vorbereitungszeit muss größer als 0 sein.";
        }
        rezepte.add(rezept);
        return null; // Kein Fehler
    }

    public String removeRezept(String titel) {
        Rezept rezept = getRezeptByTitel(titel);
        if (rezept == null) {
            return "Rezept nicht gefunden.";
        }
        rezepte.remove(rezept);
        return null; // Kein Fehler
    }

    public ArrayList<Rezept> getRezepte() {
        return rezepte;
    }

    public Rezept getRezeptByTitel(String titel) {
        for (Rezept rezept : rezepte) {
            if (rezept.getTitel().equals(titel)) {
                return rezept;
            }
        }
        return null;
    }
}

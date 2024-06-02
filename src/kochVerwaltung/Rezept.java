package kochVerwaltung;

import java.util.ArrayList;
import java.util.LinkedList;

public class Rezept {
    private String titel;
    private ArrayList<String> beschreibungen;
    private LinkedList<String> anweisungen;
    private int vorbereitungszeit;

    public Rezept(String titel, int vorbereitungszeit) {
        this.titel = titel;
        this.vorbereitungszeit = vorbereitungszeit;
        this.beschreibungen = new ArrayList<>();
        this.anweisungen = new LinkedList<>();
    }

    public String getTitel() {
        return titel;
    }

    public int getVorbereitungszeit() {
        return vorbereitungszeit;
    }

    public ArrayList<String> getBeschreibungen() {
        return beschreibungen;
    }

    public LinkedList<String> getAnweisungen() {
        return anweisungen;
    }

    public void addBeschreibung(String beschreibung) {
        beschreibungen.add(beschreibung);
    }

    public void addAnweisung(String anweisung) {
        anweisungen.add(anweisung);
    }
}

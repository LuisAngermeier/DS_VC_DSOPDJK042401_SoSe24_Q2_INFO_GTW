package kochVerwaltung;

public class Main {
    public static void main(String[] args) {
        RezeptVerwaltung verwaltung = new RezeptVerwaltung();

        // Beispielrezepte hinzufügen
        Rezept rezept1 = new Rezept("Pfannkuchen", 20);
        rezept1.addBeschreibung("Einfaches Pfannkuchenrezept");
        rezept1.addAnweisung("Eier und Mehl verrühren");
        rezept1.addAnweisung("Milch hinzufügen");
        rezept1.addAnweisung("In der Pfanne ausbacken");
        verwaltung.addRezept(rezept1);

        Rezept rezept2 = new Rezept("Salat", 10);
        rezept2.addBeschreibung("Frischer Gemüsesalat");
        rezept2.addAnweisung("Gemüse waschen und schneiden");
        rezept2.addAnweisung("Mit Dressing vermengen");
        verwaltung.addRezept(rezept2);

        RezeptUI ui = new RezeptUI(verwaltung);
        ui.start();
        

//        RezeptGUI gui = new RezeptGUI(verwaltung);
//        gui.setVisible(true);
        
        
        
        
        
        
        
        
    }
}

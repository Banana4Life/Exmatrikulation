package de.cubeisland.games.dhbw.entity;

import de.cubeisland.engine.reflect.ReflectedYaml;
import de.cubeisland.engine.reflect.Section;

public class CardPreFab extends ReflectedYaml {
    // initialization of card variables
    // Es werden für die einzelnen Kartentypen jeweils nicht alle Attribute benötigt, der Rest wird einfach mit default Werten befüllt

    public String name;

    // Der Kartentyp: event, object, ability
    public String type;

    // Der Kartentext
    public String description;

    // Belohnung beim erfolgreichen Abschließen eines Ereignisses oder beim Ausrüsten eines Objektes oder einer Fähigkeit
    // z.B. type = math, value = 5
    public class Reward implements Section {
        // path will be: reward.type
        public String type;

        // path will be: reward.value
        public String value;
    }

    // Das Ereignis wurde nicht erfolgreich absolviert (nur Ereigniskarten)
    // es kann auch nur type befüllt werden (z.B. bei Exmatrikulation)
    public class Penalty implements Section {
        // path will be: penalty.type
        public String type;

        // path will be: penalty.value
        public String value;
    }

    // Der Charakterwert auf den sich die Karte bezieht (z.B. math zum Bestehen einer Matheklausur) (nur Ereigniskarten)
    public String subject;

    // Der Wert der zum Bestehen des Ereignisses mind. nötig ist (nur Ereigniskarten)
    public int obstacle;

    // Die Karte kann ausgerüstet werden und bringt permanente Boni falls equipable = true (nur Objekt- und Fähigkeitskarten)
    // ansonsten kann sie nur einmalig benutzt werden
    public boolean equipable;
}

package tryout.hibernate.enumeration;

/**Komplexer Enumeration verwendet.
 * Diese wird bei der Generierung als BLOB in der Tabelle abgelegt.
 * Obwohl es komplexer ist, hat man auf diesem Weg die Möglichkeit die Tabelle mit JPQL abzufragen.
 * 
 * ABER: In ... . Enum.004 wird der gespeicherte Wert nur noch ein String sein. 
 * @author lindhaueradmin
 *
 */
public enum USState004 { ALABAMA("Alabama", "AL"),
ALASKA("Alaska", "AK"),

WYOMING("Wyoming", "WY");

private String name, abbr;

USState004(String fullName, String abbr) {
    this.name = fullName;
    this.abbr = abbr;
}

@Override
public String toString() {
    return this.name;
}

// the identifierMethod ---> Going in DB
public String getAbbreviation() {
    return this.abbr;
}

// the valueOfMethod <--- Translating from DB
public static USState004 fromAbbreviation(String s) {
    for (USState004 state : values()) {
        if (s.equals(state.getAbbreviation()))
            return state;
    }
    throw new IllegalArgumentException("Not a correct state: " + s);
}

}

package de.mfroese.logic;

import java.security.SecureRandom;

/**
 * <h2>Zufallspasswort generator</h2>
 * durch aufruf der newPW wird ein neues Passwort zurueckgegeben.
 */
public class PasswordGenertor {
    //region Konstanten
    /**
     * Alle moeglichen Zeichen die im Password vorkommen koennen werden definiert.
     */
    private static final String LOWERCASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS            = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!*#_";
    //endregion

    //region Funktion

    /**
     * die Funktion newPW erstellt ein zufaellig generiertes Passwort das als rueckgabewert geliefert wird.
     */
    public static String newPW() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Fuege einen zufaelligen Buchstaben aus jeder Kategorie hinzu
        char randomLowercase = LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length()));
        char randomUppercase = UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length()));
        char randomNumber = NUMBERS.charAt(random.nextInt(NUMBERS.length()));
        char randomSpecialChar = SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length()));

        password.append(randomLowercase);
        password.append(randomUppercase);
        password.append(randomNumber);
        password.append(randomSpecialChar);

        // Fuege zufaellige Zeichen hinzu
        String validCharacters = LOWERCASE_LETTERS + UPPERCASE_LETTERS + NUMBERS + SPECIAL_CHARACTERS;
        for (int i = 0; i < 12; i++) {
            char randomCharacter = validCharacters.charAt(random.nextInt(validCharacters.length()));
            password.append(randomCharacter);
        }

        // Mische die Zeichen im Passwort
        for (int i = password.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(j));
            password.setCharAt(j, temp);
        }

        // Ueberpruefe, ob das erste Zeichen ein Buchstabe ist
        if (!Character.isLetter(password.charAt(0))) {
            char firstCharacter = password.charAt(0);
            for (int i = 1; i < password.length(); i++) {
                if (Character.isLetter(password.charAt(i))) {
                    password.setCharAt(0, password.charAt(i));
                    password.setCharAt(i, firstCharacter);
                    break;
                }
            }
        }

        return password.toString();
    }
    //endregion

}

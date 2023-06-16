package de.mfroese.settings;

/**
 * Stellt alle Programmtexte zur Verfuegung
 */
public class AppTexts {

    //region 0. Gui
    public static final String APP_TITLE      = "Pw Maninger";
    public static final String APP_TITLE_LONG = "Passwort Maninger";

    //Registrieren
    public static final String NEW_ADMIN_PASSWORD = "Legen Sie ein Admin Passwort fest:";
    public static final String REGISTER           = "Registrieren";

    //Anmelden
    public static final String ADMIN_PASSWORD = "Admin Passwort:";
    public static final String LOGIN          = "Anmelden";

    //Main
    public static final String YOUR_PASSWORDS = "Deine Passwörter";
    public static final String TITLE          = "Titel";
    public static final String PASSWORD       = "Passwort";

    //Buttons
    public static final String SHOW         = "Pw zeigen";
    public static final String DELETE       = "Löschen";
    public static final String EDIT         = "Bearbeiten";
    public static final String NEW_PASSWORD = "Neues Passwort";
    public static final String OK           = "OK";
    public static final String CANCEL       = "Abbrechen";
    public static final String CONFIRM_DELETE = "Löschen bestätigen";





    //Dialoge
    public static final String ENTER_TITLE     = "Titel eingeben:";
    public static final String ENTER_PASSWORD  = "Passwort eingeben:";
    public static final String RANDOM_PASSWORD = "Zufallspasswort generieren";


    //Userhinweise
    public static final String NOTICE_TITLE                = "Hinweis";
    public static final String ADMIN_PASSWORD_IS_PROTECTED = "Das Admin-Passwort darf nicht gelöscht werden.";
    public static final String PASSWORD_TO_SHORT           = "Das Passwort ist zu kurz!";
    public static final String INVALID_PASSWORD            = "Falsches Passwort!";
    public static final String ERROR                       = "Fehler!";
    //endregion


    //region 5. Konstruktoren

    /**
     * Kann nicht mit new instanziiert
     * werden. Da diese Klasse nur Konstanten enthaelt
     */
    private AppTexts() {
    }
    //endregion
}
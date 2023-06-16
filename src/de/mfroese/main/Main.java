package de.mfroese.main;

import de.mfroese.gui.LoginGui;

/**
 * Einstiegspunkt in das Programm
 * Passwort Maninger
 */
public class Main {

    //region 0. Main
    
    /**
     * Startet das Hauptprogramm
     * @param args : {@link String} : Programmargumente
     */
    public static void main(String[] args) {
        //Instanziierung der Grafische Oberflaeche
        LoginGui gui = new LoginGui();
        gui.startUi();
    }
    //endregion

}
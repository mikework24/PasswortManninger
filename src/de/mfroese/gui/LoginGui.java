package de.mfroese.gui;

import de.mfroese.logic.CsvFileHandler;
import de.mfroese.settings.AppTexts;
import de.mfroese.model.Password;

import java.util.List;
import javax.swing.*;

/**
 * Stellt das Loginfenster der Applikation dar
 * und implementiert die Steuerlogik
 */
public class LoginGui {
    //region Konstanten
    private static final int MIN_CHARS_TO_INPUT = 2;
    //endregion

    //region Attribute
    private final List<Password> passwords;

    private final JFrame loginFrame;
    private final JLabel lblWellcome;
    private final JTextField txtLoginPw;
    private final JLabel lblResponse;
    private final JButton btnSubmit;

    //endregion

    //region Konstruktoren
    public LoginGui() {
        //Ladet die Gespeicherten Passwoerter aus der CSV datei
        this.passwords = CsvFileHandler.getOnlyInstanceOfThisClassEver().readFromCsvFile();

        String btn;
        String label;

        if (this.passwords.isEmpty()) {

            //Admin einrichten Texte
            label = AppTexts.NEW_ADMIN_PASSWORD;
            btn = AppTexts.REGISTER;
        }else{

            //Login Texte
            label = AppTexts.ADMIN_PASSWORD;
            btn = AppTexts.LOGIN;
        }

        loginFrame = new JFrame(AppTexts.APP_TITLE);
        lblWellcome = new JLabel(label);
        txtLoginPw = new JTextField();
        lblResponse = new JLabel("");
        btnSubmit = new JButton(btn);
    }
    //endregion

    //region Methoden
    public void startUi() {
        configureGuiElements();
        showGuiElements();
    }

    private void configureGuiElements() {
        loginFrame.setSize(270, 240);
        loginFrame.setLocation(500, 300);
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setLayout(null);

        lblWellcome.setSize(200, 25);
        lblWellcome.setLocation(25, 20);

        txtLoginPw.setSize(200, 25);
        txtLoginPw.setLocation(25, 60);

        lblResponse.setSize(200, 25);
        lblResponse.setLocation(25, 100);

        btnSubmit.setSize(200, 35);
        btnSubmit.setLocation(25, 140);
        btnSubmit.setActionCommand("submit");


        //Eventhandling des Buttons

        //Enter im Textfeld klickt den button
        txtLoginPw.addActionListener(e -> {
            JRootPane rootPane = SwingUtilities.getRootPane(btnSubmit);
            rootPane.setDefaultButton(btnSubmit);
            btnSubmit.doClick();
        });

        //Registrieren und Anmelden Button
        btnSubmit.addActionListener(actionEvent -> {
            if(actionEvent.getSource() instanceof JButton source){

                String buttonText       = source.getText();
                String inputtedPassword = txtLoginPw.getText();

                if(buttonText.equals(AppTexts.REGISTER)){

                    //Registrieren Klick
                    if (inputtedPassword.length() >= MIN_CHARS_TO_INPUT) {

                        //Admin Passwort setzen
                        Password adminPassword = new Password("APP_PASSWORT", inputtedPassword);

                        //Neuen Eintrag hinzufuegen
                        this.passwords.add(adminPassword);

                        //Aenderung in der Datei Speichern
                        CsvFileHandler.getOnlyInstanceOfThisClassEver().saveToCsvFile(this.passwords);

                        //Gui Aendern auf Anmelden
                        lblWellcome.setText(AppTexts.ADMIN_PASSWORD);
                        txtLoginPw.setText("");
                        lblResponse.setText("");
                        btnSubmit.setText(AppTexts.LOGIN);
                    }else{
                        txtLoginPw.setText("");
                        lblResponse.setText(AppTexts.PASSWORD_TO_SHORT);
                    }
                }else{

                    //Anmelden Klick
                    Password adminPassword = this.passwords.get(0);

                    if(inputtedPassword.equals( adminPassword.getPassword() )){

                        //Admin Passwort ist richtig -> MainGui LADEN
                        MainGui mainGui = new MainGui();
                        mainGui.startUi();

                        //Aktuelles fenster schließen
                        loginFrame.dispose();

                    }else{

                        //Admin Passwort ist falsch
                        txtLoginPw.setText("");
                        lblResponse.setText(AppTexts.INVALID_PASSWORD);
                    }
                }
            }
        });
    }

    private void showGuiElements() {
        loginFrame.add(lblWellcome);
        loginFrame.add(txtLoginPw);
        loginFrame.add(lblResponse);
        loginFrame.add(btnSubmit);
        loginFrame.setVisible(true);
    }
    //endregion

}

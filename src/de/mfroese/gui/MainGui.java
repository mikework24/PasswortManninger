package de.mfroese.gui;

import de.mfroese.logic.*;
import de.mfroese.settings.AppTexts;
import de.mfroese.model.Password;
import de.mfroese.logic.PasswordGenertor;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Stellt das Hauptfenster der Applikation dar
 * und implementiert die Steuerlogik
 */
public class MainGui {
    //region Konstanten
    //endregion

    //region Attribute
    private final List<Password> passwords;

    private final JFrame mainFrame;
    private final JLabel lblPasswordTitle;
    private final JButton btnNewPw;
    private final JTable passwordTable;
    private final DefaultTableModel tableModel;
    //endregion

    //region Konstruktoren
    public MainGui() {
        //Ladet die Gespeicherten Passwoerter aus der CSV datei
        this.passwords = CsvFileHandler.getOnlyInstanceOfThisClassEver().readFromCsvFile();

        mainFrame = new JFrame(AppTexts.APP_TITLE_LONG);
        lblPasswordTitle = new JLabel(AppTexts.YOUR_PASSWORDS);
        btnNewPw = new JButton(AppTexts.NEW_PASSWORD);
        tableModel = new DefaultTableModel(new Object[]{AppTexts.TITLE, AppTexts.PASSWORD, "", "", ""}, 0);
        passwordTable = new JTable(tableModel);
    }
    //endregion

    //region Methoden
    public void startUi() {
        configureGuiElements();
        showGuiElements();
        loadPasswords();
    }

    private void configureGuiElements() {
        mainFrame.setSize(620, 400);
        mainFrame.setLocation(400, 200);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(lblPasswordTitle);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNewPw);

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(new JScrollPane(passwordTable), BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

        btnNewPw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(mainFrame, AppTexts.ENTER_TITLE);
                if (title != null && !title.isEmpty()) {
                    JTextField passwordField = new JTextField();

                    JButton generateButton = new JButton(AppTexts.RANDOM_PASSWORD);
                    generateButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String generatedPassword = PasswordGenertor.newPW();
                            passwordField.setText(generatedPassword);
                        }
                    });

                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(2, 1));
                    panel.add(passwordField);
                    panel.add(generateButton);

                    Object[] options = {AppTexts.OK, AppTexts.CANCEL};
                    int result = JOptionPane.showOptionDialog(mainFrame, panel, AppTexts.ENTER_PASSWORD, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (result == JOptionPane.OK_OPTION) {
                        String password = passwordField.getText();
                        if (password != null) {
                            Password newPassword = new Password(title, password);
                            passwords.add(newPassword);
                            Object[] rowData = {newPassword.getTitle(), maskPassword(newPassword.getPassword()), AppTexts.SHOW, AppTexts.EDIT, AppTexts.DELETE};
                            tableModel.addRow(rowData);
                            savePasswords();
                        }
                    }
                }
            }
        });

        // Setze die Breite der Buttons
        passwordTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        passwordTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        passwordTable.getColumnModel().getColumn(4).setPreferredWidth(20);

        // Renderer der Button in der Tabelle
        passwordTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        passwordTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        passwordTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

        // Buttons in der Tabelle
        passwordTable.getColumnModel().getColumn(2).setCellEditor(new ButtonShow(this));
        passwordTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEdit(this));
        passwordTable.getColumnModel().getColumn(4).setCellEditor(new ButtonDelete(this));
    }

    private void showGuiElements() {
        mainFrame.setVisible(true);
    }

    private void loadPasswords() {
        for (Password password : passwords) {
            Object[] rowData = {password.getTitle(), maskPassword(password.getPassword()), AppTexts.SHOW, AppTexts.EDIT, AppTexts.DELETE};
            tableModel.addRow(rowData);
        }
    }

    private void savePasswords() {
        CsvFileHandler.getOnlyInstanceOfThisClassEver().saveToCsvFile(passwords);
    }


    public void showPassword(int row) {
        if (row >= 0 && row < passwords.size()) {
            Password password = passwords.get(row);
            String passwordText = password.getPassword();

            JTextField passwordField = new JTextField(passwordText);
            passwordField.setEditable(false);

            JOptionPane.showMessageDialog(mainFrame, passwordField, AppTexts.PASSWORD, JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void editPassword(int row) {
        Password oldPassword = passwords.get(row);

        String title = JOptionPane.showInputDialog(mainFrame, AppTexts.ENTER_TITLE, oldPassword.getTitle());
        if (title != null && !title.isEmpty()) {
            JTextField passwordField = new JTextField();
            passwordField.setText(oldPassword.getPassword());

            JButton generateButton = new JButton(AppTexts.RANDOM_PASSWORD);
            generateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String generatedPassword = PasswordGenertor.newPW();
                    passwordField.setText(generatedPassword);
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));
            panel.add(passwordField);
            panel.add(generateButton);

            JOptionPane.showMessageDialog(mainFrame, panel, AppTexts.ENTER_PASSWORD, JOptionPane.PLAIN_MESSAGE);

            String password = passwordField.getText();
            if (password != null) {
                Password editedPassword = passwords.get(row);
                editedPassword.setTitle(title);
                editedPassword.setPassword(password);
                tableModel.setValueAt(title, row, 0);
                tableModel.setValueAt(maskPassword(password), row, 1);
                savePasswords();
            }
        }
    }


    public void deletePassword(int row) {
        if (row >= 0 && row < passwords.size()) {

            // Sicherheitsabfrage
            JPanel panel = new JPanel(new GridLayout(2, 1)); // GridLayout mit 2 Reihen und 1 Spalte
            panel.add(new JLabel(AppTexts.ADMIN_PASSWORD));
            JPasswordField passwordField = new JPasswordField();
            panel.add(passwordField);

            int option = JOptionPane.showConfirmDialog(mainFrame, panel, AppTexts.CONFIRM_DELETE, JOptionPane.OK_CANCEL_OPTION);

            // Das Textfeld markieren
            SwingUtilities.invokeLater(passwordField::requestFocusInWindow);

            if (option == JOptionPane.OK_OPTION) {
                char[] input = passwordField.getPassword();
                String inputPassword = new String(input);

                // Überprüfe, ob das eingegebene Passwort korrekt ist
                Password adminPassword = this.passwords.get(0);
                if (inputPassword.equals(adminPassword.getPassword())) {
                    passwords.remove(row);
                    tableModel.removeRow(row);
                    tableModel.fireTableDataChanged();
                    savePasswords();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, AppTexts.INVALID_PASSWORD, AppTexts.ERROR, JOptionPane.ERROR_MESSAGE);                }
            }

        }
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }
    //endregion

    /**
     * Gibt einen gleichlangen String zurueck der nur aus * besteht
     *
     * @return ******** : {@link String} [] : ersetzt alle zeichen durch ein *****
     */
    public static String maskPassword(String input) {
        return "*".repeat(input.length());
    }
}

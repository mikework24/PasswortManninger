package de.mfroese.logic;

import de.mfroese.gui.MainGui;
import de.mfroese.settings.AppTexts;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonDelete extends AbstractCellEditor implements TableCellEditor {
    private final JButton deleteButton;
    private int rowIndex;
    private MainGui mainGui;

    /**
     * <h2>Erstellt einen eigenen Button Loeschen</h2>
     */
    public ButtonDelete(MainGui mainGui) {
        deleteButton = new JButton(AppTexts.DELETE);
        this.mainGui = mainGui;

        // Eventhandling für den Bearbeiten-Button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainGui != null) {
                    if(rowIndex > 0){
                        mainGui.deletePassword(rowIndex);
                    }else{
                        //Admin Passwort kann nicht geloescht werden
                        JOptionPane.showMessageDialog(mainGui.getMainFrame(),
                                AppTexts.ADMIN_PASSWORD_IS_PROTECTED,
                                AppTexts.NOTICE_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                    }
                }

                // Beende die Bearbeitung und übergebe den Wert des Editors
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        rowIndex = row; // Index wird zwischengespeichert

        return deleteButton;
    }

    public Object getCellEditorValue() {
        return deleteButton.getText();
    }
}

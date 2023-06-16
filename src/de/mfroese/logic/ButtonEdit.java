package de.mfroese.logic;

import de.mfroese.gui.MainGui;
import de.mfroese.settings.AppTexts;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h2>Erstellt einen eigenen Button PW Bearbeiten</h2>
 */
public class ButtonEdit extends AbstractCellEditor implements TableCellEditor {
    private final JButton editButton;
    private int rowIndex;
    private MainGui mainGui;


    public ButtonEdit(MainGui mainGui) {
        editButton = new JButton(AppTexts.EDIT);
        this.mainGui = mainGui;

        // Eventhandling für den Bearbeiten-Button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainGui != null) {
                    mainGui.editPassword(rowIndex);
                }

                // Beende die Bearbeitung und übergebe den Wert des Editors
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        rowIndex = row; // Index wird zwischengespeichert

        return editButton;
    }

    public Object getCellEditorValue() {
        return editButton.getText();
    }
}

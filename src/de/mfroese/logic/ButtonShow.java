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
 * <h2>Erstellt einen eigenen Button PW Zeigen</h2>
 */
public class ButtonShow extends AbstractCellEditor implements TableCellEditor {
    private final JButton showButton;
    private int rowIndex;
    private MainGui mainGui;


    public ButtonShow(MainGui mainGui) {
        showButton = new JButton(AppTexts.SHOW);
        this.mainGui = mainGui;

        // Eventhandling für den Bearbeiten-Button
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainGui != null) {
                    mainGui.showPassword(rowIndex);
                }

                // Beende die Bearbeitung und übergebe den Wert des Editors
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        rowIndex = row; // Index wird zwischengespeichert

        return showButton;
    }

    public Object getCellEditorValue() {
        return showButton.getText();
    }
}

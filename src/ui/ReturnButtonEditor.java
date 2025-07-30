package ui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;

public class ReturnButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private MyLoansPanel myLoansPanel;

    public ReturnButtonEditor(JCheckBox checkBox, MyLoansPanel myLoansPanel) {
        super(checkBox);
        this.myLoansPanel = myLoansPanel;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "İade Et" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = myLoansPanel.table.getSelectedRow();
            int loanId = (int) myLoansPanel.tableModel.getValueAt(row, 0);
            myLoansPanel.returnSelectedLoan(loanId);
        }
        isPushed = false;
        return label;
    }
}
package ui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import model.User;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private BookPanel bookPanel;
    private User user;

    public ButtonEditor(JCheckBox checkBox, BookPanel bookPanel, User user) {
        super(checkBox);
        this.bookPanel = bookPanel;
        this.user = user;
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
        label = (value == null) ? "Ödünç Al" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = bookPanel.table.getSelectedRow();
            int bookId = (int) bookPanel.tableModel.getValueAt(row, 0);
            bookPanel.loanSelectedBook(user, bookId);
        }
        isPushed = false;
        return label;
    }
}
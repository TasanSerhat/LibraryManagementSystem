
package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import service.*;
import model.*;

public class MyLoansPanel extends javax.swing.JPanel {
    
    private LoanService loanService = new LoanServiceImpl();
    private BookService bookService = new BookServiceImpl();
    private MainMenuPanel mainMenuPanel;
    private User user;
    public JTable table;
    public DefaultTableModel tableModel;
    private JButton btnReturnBook;

    public MyLoansPanel(User user, MainMenuPanel mainMenuPanel) {
        this.user = user;
        this.mainMenuPanel = mainMenuPanel;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Kitap", "Veriliş", "İade", "Gerçek İade","İşlem"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 5; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        table.getColumn("İşlem").setCellRenderer(new ButtonRenderer());
        table.getColumn("İşlem").setCellEditor(new ReturnButtonEditor(new JCheckBox(), this));

        loadMyLoans();
    }
    
    public void loadMyLoans() {
        List<Loan> loans = loanService.getLoansByMemberId(user.getId());
        tableModel.setRowCount(0);
        for (Loan l : loans) {
            Book book = bookService.getBookById(l.getBookId());
            String islem = "İade et";
            tableModel.addRow(new Object[]{
                l.getId(),
                book != null ? book.getTitle() : "Bilinmiyor",
                l.getLoanDate(),
                l.getReturnDate(),
                l.getActualReturnDate() != null ? l.getActualReturnDate() : "", islem
            });
        }
    }
    
    public void returnSelectedLoan(int loanId) {
        int confirm = JOptionPane.showConfirmDialog(this, "İade etmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                loanService.returnBook(loanId);
                loadMyLoans();
                if (mainMenuPanel != null) {
                    mainMenuPanel.refreshBookPanel();
                    mainMenuPanel.refreshLoanPanel();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

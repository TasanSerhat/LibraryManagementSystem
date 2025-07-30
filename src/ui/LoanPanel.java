
package ui;

import model.Loan;
import model.Member;
import model.Book;
import service.LoanService;
import service.LoanServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;
import service.BookService;
import service.BookServiceImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;
import javax.swing.table.TableCellRenderer;

public class LoanPanel extends javax.swing.JPanel {
    
    private LoanService loanService = new LoanServiceImpl();
    private MemberService memberService = new MemberServiceImpl();
    private BookService bookService = new BookServiceImpl();
    private MainMenuPanel mainMenuPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAddLoan, btnReturnBook;

    public LoanPanel(MainMenuPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
        setLayout(new BorderLayout());
        
        tableModel = new DefaultTableModel(new Object[]{"ID", "Kitap", "Üye", "Veriliş", "İade", "Gerçek İade"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnAddLoan = new JButton("Ödünç Ver");
        btnReturnBook = new JButton("İade Al");
        buttonPanel.add(btnAddLoan);
        buttonPanel.add(btnReturnBook);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAddLoan.addActionListener(e -> showAddLoanDialog());
        btnReturnBook.addActionListener(e -> returnSelectedLoan());

        loadAllLoans();
    }
    
    public void loadAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        fillTable(loans);
    }
    
    private void fillTable(List<Loan> loans) {
        tableModel.setRowCount(0);
        for (Loan l : loans) {
            Book book = bookService.getBookById(l.getBookId());
            Member member = memberService.getMemberById(l.getMemberId());
            tableModel.addRow(new Object[]{
                l.getId(),
                book != null ? book.getTitle() : "Bilinmiyor",
                member != null ? member.getName() + " " + member.getSurname() : "Bilinmiyor",
                l.getLoanDate(),
                l.getReturnDate(),
                l.getActualReturnDate() != null ? l.getActualReturnDate() : ""
            });
        }
    }
    
    private void showAddLoanDialog() {
        AddLoanDialog dialog = new AddLoanDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            try {
                loanService.addLoan(dialog.getLoan());
                loadAllLoans();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        }
    }
    
    private void returnSelectedLoan() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ödünç kaydı seçin.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "İade almak istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                loanService.returnBook(id);
                loadAllLoans();
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


package ui;

import java.awt.GridLayout;
import java.util.Date;
import javax.swing.*;
import model.Book;
import model.Loan;
import model.Member;
import service.BookService;
import service.BookServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;

public class AddLoanDialog extends javax.swing.JDialog {
    
    private JComboBox<Member> cmbMember;
    private JComboBox<Book> cmbBook;
    private JTextField txtLoanDate, txtReturnDate;
    private JButton btnSave, btnCancel;
    private boolean saved = false;
    private Loan loan;

    public AddLoanDialog(java.awt.Frame parent) {
        super(parent, true);
        
        setTitle("Ödünç Ver");
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 5, 5));

        MemberService memberService = new MemberServiceImpl();
        BookService bookService = new BookServiceImpl();

        add(new JLabel("Üye:"));
        cmbMember = new JComboBox<>(memberService.getAllMembers().toArray(new Member[0]));
        add(cmbMember);

        add(new JLabel("Kitap:"));
        cmbBook = new JComboBox<>(bookService.getAllBooks().toArray(new Book[0]));
        add(cmbBook);

        add(new JLabel("Veriliş Tarihi (yyyy-MM-dd):"));
        txtLoanDate = new JTextField();
        add(txtLoanDate);

        add(new JLabel("İade Tarihi (yyyy-MM-dd):"));
        txtReturnDate = new JTextField();
        add(txtReturnDate);

        btnSave = new JButton("Kaydet");
        btnCancel = new JButton("İptal");
        add(btnSave); add(btnCancel);
        
        txtReturnDate.addActionListener(e -> btnSave.doClick());

        btnSave.addActionListener(e -> {
            try {
                Member member = (Member) cmbMember.getSelectedItem();
                Book book = (Book) cmbBook.getSelectedItem();
                Date loanDate = java.sql.Date.valueOf(txtLoanDate.getText().trim());
                Date returnDate = java.sql.Date.valueOf(txtReturnDate.getText().trim());
                this.loan = new Loan(0, book.getId(), member.getId(), loanDate, returnDate, null);
                saved = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hatalı giriş: " + ex.getMessage());
            }
        });
        btnCancel.addActionListener(e -> dispose());

    }
    
    public boolean isSaved() { return saved; }
    public Loan getLoan() { return loan; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

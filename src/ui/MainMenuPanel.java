
package ui;

import java.awt.BorderLayout;
import model.User;
import javax.swing.*;

public class MainMenuPanel extends javax.swing.JPanel {
    
    private JTabbedPane tabbedPane;
    private BookPanel bookPanel;
    private MemberPanel memberPanel;
    private LoanPanel loanPanel;
    private MyLoansPanel myLoansPanel;
    private ProfilePanel profilePanel;

    public MainMenuPanel(User user, MainFrame mainFrame) {
        JTabbedPane tabbedPane = new JTabbedPane();

        bookPanel = new BookPanel(user, this);
        profilePanel = new ProfilePanel(user, mainFrame);

        tabbedPane.addTab("Kitaplar", new ImageIcon(getClass().getResource("/icons/books.png")), bookPanel);

        if (user.getRole().equalsIgnoreCase("admin")) {
            memberPanel = new MemberPanel();
            loanPanel = new LoanPanel(this);
            tabbedPane.addTab("Üyeler", new ImageIcon(getClass().getResource("/icons/members.png")), memberPanel);
            tabbedPane.addTab("Ödünçler", new ImageIcon(getClass().getResource("/icons/loans.png")), loanPanel);
        } else {
            myLoansPanel = new MyLoansPanel(user, this);
            tabbedPane.addTab("Ödünçlerim", new ImageIcon(getClass().getResource("/icons/loans.png")), myLoansPanel);
        }
        
        tabbedPane.addTab("Profilim", new ImageIcon(getClass().getResource("/icons/profile.png")), profilePanel);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    // Yenileme metodları
    public void refreshMyLoansPanel() {
        if (myLoansPanel != null) myLoansPanel.loadMyLoans();
    }
    public void refreshLoanPanel() {
        if (loanPanel != null) loanPanel.loadAllLoans();
    }  
    public void refreshBookPanel() {
        if (bookPanel != null) bookPanel.loadAllBooks();
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

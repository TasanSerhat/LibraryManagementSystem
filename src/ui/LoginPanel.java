
package ui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends javax.swing.JPanel {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblMessage;

    public LoginPanel() {   
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout(0, 10));

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icons/app_icon.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(scaledImage));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblTitle = new JLabel("Kütüphane Yönetim Sistemi");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(lblLogo, BorderLayout.NORTH);
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        GridBagConstraints gbcHeader = new GridBagConstraints();
        gbcHeader.gridx = 0; 
        gbcHeader.gridy = 0; 
        gbcHeader.gridwidth = 2; 
        gbcHeader.insets = new Insets(20, 20, 30, 20);
        gbcHeader.anchor = GridBagConstraints.CENTER;
        add(headerPanel, gbcHeader);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel lblUsername = new JLabel("Kullanıcı Adı:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        formGbc.gridx = 0; 
        formGbc.gridy = 0; 
        formGbc.anchor = GridBagConstraints.EAST;
        formGbc.insets = new Insets(5, 5, 5, 10);
        formPanel.add(lblUsername, formGbc);

        txtUsername = new JTextField(20);
        formGbc.gridx = 1; 
        formGbc.gridy = 0; 
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(txtUsername, formGbc);

        JLabel lblPassword = new JLabel("Şifre:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formGbc.gridx = 0; 
        formGbc.gridy = 1; 
        formGbc.anchor = GridBagConstraints.EAST;
        formGbc.insets = new Insets(5, 5, 5, 10);
        formPanel.add(lblPassword, formGbc);

        txtPassword = new JPasswordField(20);
        formGbc.gridx = 1; 
        formGbc.gridy = 1; 
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(txtPassword, formGbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        
        btnLogin = new JButton("Giriş Yap");
        btnLogin.setPreferredSize(new Dimension(100, 35));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnRegister = new JButton("Üye Ol");
        btnRegister.setPreferredSize(new Dimension(100, 35));
        btnRegister.setFont(new Font("Arial", Font.BOLD, 12));
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        formGbc.gridx = 0; 
        formGbc.gridy = 2; 
        formGbc.gridwidth = 2; 
        formGbc.insets = new Insets(20, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, formGbc);

        gbc.gridx = 0; 
        gbc.gridy = 1; 
        gbc.gridwidth = 2; 
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(formPanel, gbc);

        // Mesaj alanı
        lblMessage = new JLabel("");
        lblMessage.setForeground(Color.DARK_GRAY);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; 
        gbc.gridy = 2; 
        gbc.gridwidth = 2; 
        gbc.insets = new Insets(10, 10, 10, 10);
        add(lblMessage, gbc);
        
        txtPassword.addActionListener(e -> btnLogin.doClick());
        txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());
        
        // Alt bilgi
        JLabel lblFooter = new JLabel("v1.0 - © 2025 TasanSerhat");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbcFooter = new GridBagConstraints();
        gbcFooter.gridx = 0;
        gbcFooter.gridy = 5; 
        gbcFooter.gridwidth = 2;
        gbcFooter.insets = new Insets(20, 0, 5, 0);
        gbcFooter.anchor = GridBagConstraints.CENTER;
        add(lblFooter, gbcFooter);
    }
    
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnRegister() { return btnRegister; }
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void setMessage(String msg) { lblMessage.setText(msg); }

    
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

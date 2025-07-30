
package ui;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends javax.swing.JPanel {
    
    private JTextField txtUsername, txtName, txtSurname, txtEmail, txtPhone;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;
    private JLabel lblMessage;

    public RegisterPanel() {
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        int y = 0;

        add(new JLabel("Kullanıcı Adı:"), gbc(0, y));
        txtUsername = new JTextField(15);
        add(txtUsername, gbc(1, y++));

        add(new JLabel("Şifre:"), gbc(0, y));
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc(1, y++));

        add(new JLabel("Ad:"), gbc(0, y));
        txtName = new JTextField(15);
        add(txtName, gbc(1, y++));

        add(new JLabel("Soyad:"), gbc(0, y));
        txtSurname = new JTextField(15);
        add(txtSurname, gbc(1, y++));

        add(new JLabel("E-posta:"), gbc(0, y));
        txtEmail = new JTextField(15);
        add(txtEmail, gbc(1, y++));

        add(new JLabel("Telefon:"), gbc(0, y));
        txtPhone = new JTextField(15);
        add(txtPhone, gbc(1, y++));

        btnRegister = new JButton("Kayıt Ol");
        add(btnRegister, gbc(0, y));

        btnBack = new JButton("Geri Dön");
        add(btnBack, gbc(1, y++));

        lblMessage = new JLabel("");
        lblMessage.setForeground(Color.DARK_GRAY);
        gbc.gridwidth = 2;
        add(lblMessage, gbc(0, y));
        
        txtPassword.addActionListener(e -> btnRegister.doClick());
        txtUsername.addActionListener(e -> btnRegister.doClick());
        txtName.addActionListener(e -> btnRegister.doClick());
        txtSurname.addActionListener(e -> btnRegister.doClick());
        txtEmail.addActionListener(e -> btnRegister.doClick());
        txtPhone.addActionListener(e -> btnRegister.doClick());
    }
    
    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }
    
    public JButton getBtnRegister() { return btnRegister; }
    public JButton getBtnBack() { return btnBack; }
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getNameField() { return txtName.getText(); }
    public String getSurnameField() { return txtSurname.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getPhone() { return txtPhone.getText(); }
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


package ui;

import java.awt.GridLayout;
import javax.swing.*;
import model.Member;

public class MemberFormDialog extends javax.swing.JDialog {
    
    private JTextField txtName, txtSurname, txtEmail, txtPhone;
    private JButton btnSave, btnCancel;
    private boolean saved = false;
    private Member member;

    public MemberFormDialog(java.awt.Frame parent, Member member) { 
        super(parent, true);
        
        setTitle(member == null ? "Üye Ekle" : "Üye Güncelle");
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Ad:")); txtName = new JTextField(); add(txtName);
        add(new JLabel("Soyad:")); txtSurname = new JTextField(); add(txtSurname);
        add(new JLabel("E-posta:")); txtEmail = new JTextField(); add(txtEmail);
        add(new JLabel("Telefon:")); txtPhone = new JTextField(); add(txtPhone);

        btnSave = new JButton("Kaydet"); btnCancel = new JButton("İptal");
        add(btnSave); add(btnCancel);
        
        txtPhone.addActionListener(e -> btnSave.doClick());

        if (member != null) {
            txtName.setText(member.getName());
            txtSurname.setText(member.getSurname());
            txtEmail.setText(member.getEmail());
            txtPhone.setText(member.getPhone());
        }

        btnSave.addActionListener(e -> {
            try {
                String name = txtName.getText().trim();
                String surname = txtSurname.getText().trim();
                String email = txtEmail.getText().trim();
                String phone = txtPhone.getText().trim();
                if (member == null) {
                    this.member = new Member(0, name, surname, email, phone, 0);
                } else {
                    this.member = new Member(member.getId(), name, surname, email, phone, member.getId());
                }
                saved = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hatalı giriş: " + ex.getMessage());
            }
        });
        btnCancel.addActionListener(e -> dispose());
    }
    
    public boolean isSaved() { return saved; }
    public Member getMember() { return member; }

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

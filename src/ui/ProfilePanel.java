package ui;

import model.Member;
import model.User;
import service.MemberService;
import service.MemberServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private User user;
    private Member member;
    private MemberService memberService = new MemberServiceImpl();
    private UserService userService = new UserServiceImpl();
    private MainFrame mainFrame;

    private JTextField txtName, txtSurname, txtEmail, txtPhone;
    private JButton btnUpdate, btnLogout;
    private JLabel lblMessage;

    public ProfilePanel(User user, MainFrame mainFrame) {
        this.user = user;
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        member = memberService.getMemberById(user.getId());

        int y = 0;
        add(new JLabel("Kullanıcı Adı:"), gbc(0, y));
        add(new JLabel(user.getUsername()), gbc(1, y++));
        add(new JLabel("Ad:"), gbc(0, y));
        txtName = new JTextField(member != null ? member.getName() : "", 15);
        add(txtName, gbc(1, y++));
        add(new JLabel("Soyad:"), gbc(0, y));
        txtSurname = new JTextField(member != null ? member.getSurname() : "", 15);
        add(txtSurname, gbc(1, y++));
        add(new JLabel("E-posta:"), gbc(0, y));
        txtEmail = new JTextField(member != null ? member.getEmail() : "", 15);
        add(txtEmail, gbc(1, y++));
        add(new JLabel("Telefon:"), gbc(0, y));
        txtPhone = new JTextField(member != null ? member.getPhone() : "", 15);
        add(txtPhone, gbc(1, y++));

        btnUpdate = new JButton("Güncelle");
        btnLogout = new JButton("Çıkış Yap");
        add(btnUpdate, gbc(0, y));
        add(btnLogout, gbc(1, y++));

        lblMessage = new JLabel("");
        lblMessage.setForeground(Color.RED);
        gbc.gridwidth = 2;
        add(lblMessage, gbc(0, y));

        btnUpdate.addActionListener(e -> updateProfile());
        btnLogout.addActionListener(e -> logout());
    }

    private void updateProfile() {
        try {
            member.setName(txtName.getText().trim());
            member.setSurname(txtSurname.getText().trim());
            member.setEmail(txtEmail.getText().trim());
            member.setPhone(txtPhone.getText().trim());
            memberService.updateMember(member);
            lblMessage.setForeground(new Color(0, 128, 0));
            lblMessage.setText("Profil güncellendi!");
        } catch (Exception ex) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("Hata: " + ex.getMessage());
        }
    }

    private void logout() {
        if (mainFrame != null) {
            mainFrame.showLoginPanel();
        }
    }
    
    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }
}
package ui;

import java.awt.BorderLayout;
import javax.swing.*;
import model.Member;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

public class MainFrame extends JFrame {

    private JPanel topBar;
    private JLabel lblUser;
    private JComboBox<String> themeCombo;
    private JPanel contentPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private UserService userService = new UserServiceImpl();
    private MemberService memberService = new MemberServiceImpl();

    public MainFrame() {
        setIconImage(new ImageIcon(getClass().getResource("/icons/app_icon.png")).getImage());
        setTitle("Kütüphane Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

         // 1. Üst bar
        topBar = new JPanel(new BorderLayout());
        lblUser = new JLabel(" ");
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // 2. Tema seçici ve etiketi
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        themePanel.setOpaque(false);
        JLabel lblTheme = new JLabel("Tema :");
        lblTheme.setFont(new Font("Arial", Font.BOLD, 14));
        String[] themes = { "Nimbus", "FlatLight", "FlatDark" };
        themeCombo = new JComboBox<>(themes);
        themeCombo.setSelectedItem("Nimbus");
        themeCombo.setToolTipText("Tema Seç");
        themeCombo.setFocusable(false);
        themeCombo.setMaximumSize(new Dimension(120, 28));
        themePanel.add(lblTheme);
        themePanel.add(themeCombo);

        topBar.add(lblUser, BorderLayout.WEST);
        topBar.add(themePanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // 5. Login panel
        loginPanel = new LoginPanel();
        setContent(loginPanel);

        themeCombo.addActionListener(e -> changeTheme());

        // İçerik paneli
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // LoginPanel başlat ve göster
        loginPanel = new LoginPanel();
        setContent(loginPanel);

        loginPanel.getBtnLogin().addActionListener(e -> handleLogin());
        loginPanel.getBtnRegister().addActionListener(e -> showRegisterPanel());
    }

    private void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void changeTheme() {
        String selected = (String) themeCombo.getSelectedItem();
        try {
            if ("Nimbus".equals(selected)) {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } else if ("FlatLight".equals(selected)) {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            } else if ("FlatDark".equals(selected)) {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            }
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Giriş işlemi
    private void handleLogin() {
        String username = loginPanel.getUsername();
        String password = loginPanel.getPassword();

        try {
            User user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                loginPanel.setMessage("Giriş başarılı!");
                showMainMenu(user);
            } else {
                loginPanel.setMessage("Kullanıcı adı veya şifre hatalı!");
            }
        } catch (Exception ex) {
            loginPanel.setMessage("Hata: " + ex.getMessage());
        }
    }

    // Kayıt işlemi
    private void handleRegister() {
        String username = registerPanel.getUsername();
        String password = registerPanel.getPassword();
        String name = registerPanel.getNameField();
        String surname = registerPanel.getSurnameField();
        String email = registerPanel.getEmail();
        String phone = registerPanel.getPhone();

        try {
            // 1. User ekle
            User user = new User(0, username, password, "member");
            userService.addUser(user);

            // 2. User'ı tekrar bul (id için)
            User createdUser = userService.getUserByUsername(username);

            try {
                // 3. Member ekle
                Member member = new Member(createdUser.getId(), name, surname, email, phone, createdUser.getId());
                memberService.addMember(member);
                registerPanel.setMessage("Kayıt başarılı! Giriş yapabilirsiniz.");
            } catch (Exception memberEx) {
                // Member eklenemezse User'ı sil
                userService.deleteUser(createdUser.getId());
                registerPanel.setMessage("Hata: " + memberEx.getMessage());
            }
        } catch (Exception ex) {
            registerPanel.setMessage("Hata: " + ex.getMessage());
        }
    }

    // Ana menüye geçiş
    private void showMainMenu(User user) {
        MainMenuPanel mainMenuPanel = new MainMenuPanel(user, this);
        setContentPane(mainMenuPanel);
        revalidate();
        repaint();
        JLabel lblUser = new JLabel("Hoşgeldiniz, " + user.getUsername() + " (" + user.getRole() + ")");
        add(lblUser, BorderLayout.NORTH);
    }

    // RegisterPanel göster
    private void showRegisterPanel() {
        if (registerPanel == null) {
            registerPanel = new RegisterPanel();
            registerPanel.getBtnRegister().addActionListener(e -> handleRegister());
            registerPanel.getBtnBack().addActionListener(e -> showLoginPanel());
        }
        // Eski mesajı temizle
        registerPanel.setMessage("");
        setContentPane(registerPanel);
        revalidate();
        repaint();
    }

    // LoginPanel göster
    public void showLoginPanel() {
        // Eski mesajı temizle
        loginPanel.setMessage("");
        setContentPane(loginPanel);
        revalidate();
        repaint();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

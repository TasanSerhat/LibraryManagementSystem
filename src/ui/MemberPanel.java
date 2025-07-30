
package ui;

import model.Member;
import service.MemberService;
import service.MemberServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableCellRenderer;

public class MemberPanel extends javax.swing.JPanel {
    
    private MemberService memberService = new MemberServiceImpl();

    private JTextField txtSearch;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;

    public MemberPanel() {
        
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Ara", new ImageIcon(getClass().getResource("/icons/search.png")));
        topPanel.add(new JLabel("Ad/Soyad/E-posta Ara:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        add(topPanel, BorderLayout.NORTH);
        
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { searchMembers(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { searchMembers(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { searchMembers(); }
        });

        tableModel = new DefaultTableModel(new Object[]{"ID", "Ad", "Soyad", "E-posta", "Telefon"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Ekle");
        btnEdit = new JButton("Güncelle");
        btnDelete = new JButton("Sil");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSearch.addActionListener(e -> searchMembers());
        btnAdd.addActionListener(e -> showMemberForm(null));
        btnEdit.addActionListener(e -> editSelectedMember());
        btnDelete.addActionListener(e -> deleteSelectedMember());
        txtSearch.addActionListener(e -> searchMembers());

        loadAllMembers();

    }
    
    private void loadAllMembers() {
        List<Member> members = memberService.getAllMembers();
        fillTable(members);
    }
    
    private void searchMembers() {
        String keyword = txtSearch.getText().trim();
        List<Member> members;
        if (keyword.isEmpty()) {
            members = memberService.getAllMembers();
        } else {
            members = memberService.searchMembers(keyword);
        }
        fillTable(members);
    }
    
    private void fillTable(List<Member> members) {
        tableModel.setRowCount(0);
        for (Member m : members) {
            tableModel.addRow(new Object[]{
                m.getId(),
                m.getName(),
                m.getSurname(),
                m.getEmail(),
                m.getPhone()
            });
        }
    }
    
    private void showMemberForm(Member member) {
        MemberFormDialog dialog = new MemberFormDialog((JFrame) SwingUtilities.getWindowAncestor(this), member);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            try {
                if (member == null) {
                    memberService.addMember(dialog.getMember());
                } else {
                    memberService.updateMember(dialog.getMember());
                }
                loadAllMembers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        }
    }
    
    private void editSelectedMember() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir üye seçin.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Member member = memberService.getMemberById(id);
        showMemberForm(member);
    }
    
    private void deleteSelectedMember() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir üye seçin.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                memberService.deleteMember(id);
                loadAllMembers();
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


package ui;

import model.Book;
import model.User;
import service.BookService;
import service.BookServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableCellRenderer;
import model.Loan;
import model.Member;
import service.LoanService;
import service.LoanServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;

public class BookPanel extends javax.swing.JPanel {
    
    private User user;
    private BookService bookService = new BookServiceImpl();
    private MainMenuPanel mainMenuPanel;

    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnLoan;
    public JTable table;
    public DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;

    public BookPanel(User user, MainMenuPanel mainMenuPanel) {
        this.user = user;
        this.mainMenuPanel = mainMenuPanel;
        
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Ara", new ImageIcon(getClass().getResource("/icons/search.png")));
        topPanel.add(new JLabel("Kitap/ Yazar Ara:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        add(topPanel, BorderLayout.NORTH);
        
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { searchBooks(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { searchBooks(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { searchBooks(); }
        });

        tableModel = new DefaultTableModel(new Object[]{"ID", "Başlık", "Yazar", "Yayınevi", "Yıl", "Stok", "İşlem"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 6; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        table.getColumn("İşlem").setCellRenderer(new ButtonRenderer());
        table.getColumn("İşlem").setCellEditor(new ButtonEditor(new JCheckBox(), this, user));

        btnSearch.addActionListener(e -> searchBooks());
        txtSearch.addActionListener(e -> searchBooks());
        
        if (user.getRole().equalsIgnoreCase("admin")) {
            JPanel buttonPanel = new JPanel();
            btnAdd = new JButton("Ekle", new ImageIcon(getClass().getResource("/icons/add.png")));
            btnEdit = new JButton("Güncelle", new ImageIcon(getClass().getResource("/icons/update.png")));
            btnDelete = new JButton("Sil", new ImageIcon(getClass().getResource("/icons/delete.png")));
            buttonPanel.add(btnAdd);
            buttonPanel.add(btnEdit);
            buttonPanel.add(btnDelete);
            add(buttonPanel, BorderLayout.SOUTH);

            btnAdd.addActionListener(e -> showBookForm(null));
            btnEdit.addActionListener(e -> editSelectedBook());
            btnDelete.addActionListener(e -> deleteSelectedBook());
        }
       
        loadAllBooks();
    }
    
    public void loanSelectedBook(User user, int bookId) {
        MemberService memberService = new MemberServiceImpl();
        Member member = memberService.getMemberById(user.getId());
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Üye kaydınız bulunamadı!");
            return;
        }

        java.sql.Date loanDate = new java.sql.Date(System.currentTimeMillis());
        java.sql.Date returnDate = java.sql.Date.valueOf(loanDate.toLocalDate().plusDays(15));

        try {
            LoanService loanService = new LoanServiceImpl();
            Loan loan = new Loan(0, bookId, member.getId(), loanDate, returnDate, null);
            loanService.addLoan(loan);
            JOptionPane.showMessageDialog(this, "Kitap ödünç alındı!");
            loadAllBooks();
            if (mainMenuPanel != null) {
                mainMenuPanel.refreshMyLoansPanel();
                mainMenuPanel.refreshLoanPanel();
                mainMenuPanel.refreshBookPanel();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
    
    private void showBookForm(Book book) {
        BookFormDialog dialog = new BookFormDialog((JFrame) SwingUtilities.getWindowAncestor(this), book);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            try {
                if (book == null) {
                    bookService.addBook(dialog.getBook());
                } else {
                    bookService.updateBook(dialog.getBook());
                }
                loadAllBooks();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        }
    }

    private void editSelectedBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçin.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Book book = bookService.getBookById(id);
        showBookForm(book);
    }

    private void deleteSelectedBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir kitap seçin.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                bookService.deleteBook(id);
                loadAllBooks();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        }
    }

    public void loadAllBooks() {
        List<Book> books = bookService.getAllBooks();
        fillTable(books);
    }

    private void searchBooks() {
        String keyword = txtSearch.getText().trim();
        List<Book> books;
        if (keyword.isEmpty()) {
            books = bookService.getAllBooks();
        } else {
            books = bookService.searchBooks(keyword);
        }
        fillTable(books);
    }

    private void fillTable(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYear(),
                book.getStock(),
                "Ödünç al"
            });
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

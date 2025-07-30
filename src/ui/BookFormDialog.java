
package ui;

import java.awt.GridLayout;
import javax.swing.*;
import model.Book;

public class BookFormDialog extends javax.swing.JDialog {
    
    private JTextField txtTitle, txtAuthor, txtPublisher, txtYear, txtStock;
    private JButton btnSave, btnCancel;
    private boolean saved = false;
    private Book book;

    public BookFormDialog(java.awt.Frame parent, Book book) {
        super(parent, true);
        
        setTitle(book == null ? "Kitap Ekle" : "Kitap Güncelle");
        setSize(300, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(7, 2, 5, 5));
        
        add(new JLabel("Başlık:")); txtTitle = new JTextField(); add(txtTitle);
        add(new JLabel("Yazar:")); txtAuthor = new JTextField(); add(txtAuthor);
        add(new JLabel("Yayınevi:")); txtPublisher = new JTextField(); add(txtPublisher);
        add(new JLabel("Yıl:")); txtYear = new JTextField(); add(txtYear);
        add(new JLabel("Stok:")); txtStock = new JTextField(); add(txtStock);
        
        btnSave = new JButton("Kaydet"); btnCancel = new JButton("İptal");
        add(btnSave); add(btnCancel);
        
        txtStock.addActionListener(e -> btnSave.doClick());
        
        if (book != null) {
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtPublisher.setText(book.getPublisher());
            txtYear.setText(String.valueOf(book.getYear()));
            txtStock.setText(String.valueOf(book.getStock()));
        }

        btnSave.addActionListener(e -> {
            try {
                String title = txtTitle.getText().trim();
                String author = txtAuthor.getText().trim();
                String publisher = txtPublisher.getText().trim();
                int year = Integer.parseInt(txtYear.getText().trim());
                int stock = Integer.parseInt(txtStock.getText().trim());
                if (book == null) {
                    this.book = new Book(0, title, author, publisher, year, stock);
                } else {
                    this.book = new Book(book.getId(), title, author, publisher, year, stock);
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
    public Book getBook() { return book; }

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

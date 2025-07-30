
package service;

import dao.BookDAO;
import dao.BookDAOImpl;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookServiceImpl implements BookService{
    
    private BookDAO bookDAO = new BookDAOImpl();
            
    @Override
    public void addBook(Book book) {
        validateBook(book, false);
        try {
            bookDAO.addBook(book);
        } catch (Exception e) {
            throw new RuntimeException("Kitap eklenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void updateBook(Book book) {
        validateBook(book, true);
        Book existing = bookDAO.getBookById(book.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Güncellenecek kitap bulunamadı!");
        }
        try {
            bookDAO.updateBook(book);
        } catch (Exception e) {
            throw new RuntimeException("Kitap güncellenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void deleteBook(int id) {
        Book existing = bookDAO.getBookById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Silinecek kitap bulunamadı!");
        }
        try {
            bookDAO.deleteBook(id);
        } catch (Exception e) {
            throw new RuntimeException("Kitap silinirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public Book getBookById(int id) {
        Book book = bookDAO.getBookById(id);
        if (book == null) {
            throw new IllegalArgumentException("Kitap bulunamadı!");
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        List<Book> allBooks = bookDAO.getAllBooks();
        List<Book> result = new ArrayList<>();
        for (Book book : allBooks) {
            if ((book.getTitle() != null && book.getTitle().toLowerCase().contains(keyword.toLowerCase())) ||
                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) ||
                (book.getPublisher() != null && book.getPublisher().toLowerCase().contains(keyword.toLowerCase()))) {
                result.add(book);
            }
        }
        return result;
    }
    
    private void validateBook(Book book, boolean isUpdate) {
        if (isUpdate && book.getId() <= 0) {
            throw new IllegalArgumentException("Kitap ID geçersiz!");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap adı boş olamaz!");
        }
        if (book.getYear() < 0) {
            throw new IllegalArgumentException("Yıl negatif olamaz!");
        }
        if (book.getStock() < 0) {
            throw new IllegalArgumentException("Stok negatif olamaz!");
        }
        // Gerekirse başka validasyonlar eklenebilir
    }
      
}

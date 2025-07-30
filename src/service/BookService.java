
package service;

import java.util.List;
import model.Book;

public interface BookService {
    
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
    Book getBookById(int id);
    List<Book> getAllBooks();
    List<Book> searchBooks(String keyword);
       
}

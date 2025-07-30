
package dao;

import java.util.List;
import model.Book;

public interface BookDAO {
    
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
    Book getBookById(int id);
    List<Book> getAllBooks();
    
}

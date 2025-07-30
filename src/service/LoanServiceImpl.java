
package service;

import dao.BookDAO;
import dao.BookDAOImpl;
import dao.LoanDAO;
import dao.LoanDAOImpl;
import dao.MemberDAO;
import dao.MemberDAOImpl;
import java.util.Date;
import java.util.List;
import model.Book;
import model.Loan;
import model.Member;

public class LoanServiceImpl implements LoanService {
    
    private LoanDAO loanDAO = new LoanDAOImpl();
    private BookDAO bookDAO = new BookDAOImpl();
    private MemberDAO memberDAO = new MemberDAOImpl();


    @Override
    public void addLoan(Loan loan) {
        validateLoan(loan, false);
        
        // 1. Kitap var mı ve stokta mı?
        Book book = bookDAO.getBookById(loan.getBookId());
        if (book == null) {
            throw new IllegalArgumentException("Kitap bulunamadı!");
        }
        if (book.getStock() <= 0) {
            throw new IllegalArgumentException("Kitap stokta yok!");
        }

        // 2. üye var mı?
        Member member = memberDAO.getMemberById(loan.getMemberId());
        if (member == null) {
            throw new IllegalArgumentException("Üye bulunamadı!");
        }

        // 3. üyenin aktif ödünç sayısı kontrolü (en fazla 3 kitap)
        List<Loan> memberLoans = loanDAO.getLoansByMemberId(loan.getMemberId());
        long activeLoans = memberLoans.stream()
                .filter(l -> l.getActualReturnDate() == null)
                .count();
        if (activeLoans >= 3) {
            throw new IllegalArgumentException("Bir üye aynı anda en fazla 3 kitap alabilir!");
        }

        // 4. Kitap stokunu azalt
        book.setStock(book.getStock() - 1);
        bookDAO.updateBook(book);

        // 5. Ödünç kaydını ekle
        try {
            loanDAO.addLoan(loan);
        } catch (Exception e) {
            // Hata durumunda stoku geri al
            book.setStock(book.getStock() + 1);
            bookDAO.updateBook(book);
            throw new RuntimeException("Ödünç kaydı eklenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void updateLoan(Loan loan) {
        validateLoan(loan, true);
        
        Loan existing = loanDAO.getLoanById(loan.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Güncellenecek ödünç kaydı bulunamadı!");
        }

        try {
            loanDAO.updateLoan(loan);
        } catch (Exception e) {
            throw new RuntimeException("Ödünç kaydı güncellenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void deleteLoan(int id) {
        Loan existing = loanDAO.getLoanById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Silinecek ödünç kaydı bulunamadı!");
        }

        // Eğer kitap henüz iade edilmemişse, stoku geri al
        if (existing.getActualReturnDate() == null) {
            Book book = bookDAO.getBookById(existing.getBookId());
            if (book != null) {
                book.setStock(book.getStock() + 1);
                bookDAO.updateBook(book);
            }
        }

        try {
            loanDAO.deleteLoan(id);
        } catch (Exception e) {
            throw new RuntimeException("Ödünç kaydı silinirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public Loan getLoanById(int id) {
        Loan loan = loanDAO.getLoanById(id);
        if (loan == null) {
            throw new IllegalArgumentException("Ödünç kaydı bulunamadı!");
        }
        return loan;
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }

    @Override
    public List<Loan> getLoansByMemberId(int memberId) {
        return loanDAO.getLoansByMemberId(memberId);
    }

    @Override
    public List<Loan> getLoansByBookId(int bookId) {
        return loanDAO.getLoansByBookId(bookId);
    }
    @Override
    public void returnBook(int loanId) {
        Loan loan = loanDAO.getLoanById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("Ödünç kaydı bulunamadı!");
        }
        if (loan.getActualReturnDate() != null) {
            throw new IllegalArgumentException("Bu kitap zaten iade edilmiş!");
        }

        // İade tarihini güncelle
        loan.setActualReturnDate(new Date());
        
        // Kitap stokunu artır
        Book book = bookDAO.getBookById(loan.getBookId());
        if (book != null) {
            book.setStock(book.getStock() + 1);
            bookDAO.updateBook(book);
        }

        try {
            loanDAO.updateLoan(loan);
        } catch (Exception e) {
            throw new RuntimeException("Kitap iade edilirken hata oluştu: " + e.getMessage());
        }
    }
    
    private void validateLoan(Loan loan, boolean isUpdate) {
        if (isUpdate && loan.getId() <= 0) {
            throw new IllegalArgumentException("Ödünç ID geçersiz!");
        }
        if (loan.getBookId() <= 0) {
            throw new IllegalArgumentException("Kitap ID geçersiz!");
        }
        if (loan.getMemberId() <= 0) {
            throw new IllegalArgumentException("üye ID geçersiz!");
        }
        if (loan.getLoanDate() == null) {
            throw new IllegalArgumentException("Ödünç verme tarihi boş olamaz!");
        }
        if (loan.getReturnDate() == null) {
            throw new IllegalArgumentException("iade tarihi boş olamaz!");
        }
        if (loan.getReturnDate().before(loan.getLoanDate())) {
            throw new IllegalArgumentException("iade tarihi, ödünç verme tarihinden önce olamaz!");
        }
        // Gerekirse başka validasyonlar eklenebilir
    }
    
}

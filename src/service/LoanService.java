
package service;

import java.util.List;
import model.Loan;

public interface LoanService {
    
    void addLoan(Loan loan);
    void updateLoan(Loan loan);
    void deleteLoan(int id);
    void returnBook(int id);
    Loan getLoanById(int id);
    List<Loan> getAllLoans();
    List<Loan> getLoansByMemberId(int memberId);
    List<Loan> getLoansByBookId(int bookId);
    
}

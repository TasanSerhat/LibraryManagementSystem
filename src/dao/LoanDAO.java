
package dao;

import java.util.List;
import model.Loan;

public interface LoanDAO {
    
    void addLoan(Loan loan);
    void updateLoan(Loan loan);
    void deleteLoan(int id);
    Loan getLoanById(int id);
    List<Loan> getAllLoans();
    List<Loan> getLoansByMemberId(int memberId);
    List<Loan> getLoansByBookId(int bookId);
    
}

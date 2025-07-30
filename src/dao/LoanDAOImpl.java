
package dao;

import java.util.List;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import model.Loan;
import util.DBConnection;

public class LoanDAOImpl implements LoanDAO{

    @Override
    public void addLoan(Loan loan) {
        String sql = "INSERT INTO Loan (book_id, member_id, loan_date, return_date, actual_return_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loan.getBookId());
            stmt.setInt(2, loan.getMemberId());
            stmt.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            stmt.setDate(4, new java.sql.Date(loan.getReturnDate().getTime()));
            if (loan.getActualReturnDate() != null) {
                stmt.setDate(5, new java.sql.Date(loan.getActualReturnDate().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLoan(Loan loan) {
        String sql = "UPDATE Loan SET book_id=?, member_id=?, loan_date=?, return_date=?, actual_return_date=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loan.getBookId());
            stmt.setInt(2, loan.getMemberId());
            stmt.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            stmt.setDate(4, new java.sql.Date(loan.getReturnDate().getTime()));
            if (loan.getActualReturnDate() != null) {
                stmt.setDate(5, new java.sql.Date(loan.getActualReturnDate().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            stmt.setInt(6, loan.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLoan(int id) {
        String sql = "DELETE FROM Loan WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Loan getLoanById(int id) {
        String sql = "SELECT * FROM Loan WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Loan(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    rs.getDate("loan_date"),
                    rs.getDate("return_date"),
                    rs.getDate("actual_return_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loan";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    rs.getDate("loan_date"),
                    rs.getDate("return_date"),
                    rs.getDate("actual_return_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> getLoansByMemberId(int memberId) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loan WHERE member_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    rs.getDate("loan_date"),
                    rs.getDate("return_date"),
                    rs.getDate("actual_return_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public List<Loan> getLoansByBookId(int bookId) {
         List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loan WHERE book_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    rs.getDate("loan_date"),
                    rs.getDate("return_date"),
                    rs.getDate("actual_return_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }
     
}

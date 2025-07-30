
package dao;

import java.util.List;
import model.Member;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class MemberDAOImpl implements MemberDAO{

    @Override
    public void addMember(Member member) {
        String sql = "INSERT INTO Member (id, name, surname, email, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, member.getId());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getSurname());
            stmt.setString(4, member.getEmail());
            stmt.setString(5, member.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMember(Member member) {
        String sql = "UPDATE Member SET name=?, surname=?, email=?, phone=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getSurname());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getPhone());
            stmt.setInt(5, member.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMember(int id) {
        String sql = "DELETE FROM Member WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Member getMemberById(int id) {
        String sql = "SELECT * FROM Member WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getInt("id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Member";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getInt("id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
      
}

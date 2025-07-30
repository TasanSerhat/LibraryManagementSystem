
package dao;

import java.util.List;
import model.Member;

public interface MemberDAO {
    
    void addMember(Member member);
    void updateMember(Member member);
    void deleteMember(int id);
    Member getMemberById(int id);
    List<Member> getAllMembers();
    
}


package service;

import java.util.List;
import model.Member;

public interface MemberService {
    
    void addMember(Member member);
    void updateMember(Member member);
    void deleteMember(int id);
    Member getMemberById(int id);
    List<Member> getAllMembers();
    List<Member> searchMembers(String keyword);
    
}

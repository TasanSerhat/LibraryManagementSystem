
package service;

import dao.MemberDAO;
import dao.MemberDAOImpl;
import java.util.List;
import java.util.regex.Pattern;
import model.Member;

public class MemberServiceImpl implements MemberService{
    
    private MemberDAO memberDAO = new MemberDAOImpl();
    
    @Override
    public void addMember(Member member) {
        validateMember(member, false);
        // E-posta benzersiz mi?
        List<Member> allMembers = memberDAO.getAllMembers();
        for (Member m : allMembers) {
            if (m.getEmail().equalsIgnoreCase(member.getEmail())) {
                throw new IllegalArgumentException("Bu e-posta ile kayıtlı bir üye zaten var!");
            }
        }
        try {
            memberDAO.addMember(member);
        } catch (Exception e) {
            throw new RuntimeException("Üye eklenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void updateMember(Member member) {
        validateMember(member, true);
        Member existing = memberDAO.getMemberById(member.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Güncellenecek üye bulunamadı!");
        }
        try {
            memberDAO.updateMember(member);
        } catch (Exception e) {
            throw new RuntimeException("Üye güncellenirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void deleteMember(int id) {
        Member existing = memberDAO.getMemberById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Silinecek üye bulunamadı!");
        }
        try {
            memberDAO.deleteMember(id);
        } catch (Exception e) {
            throw new RuntimeException("Üye silinirken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public Member getMemberById(int id) {
        Member member = memberDAO.getMemberById(id);
        if (member == null) {
            throw new IllegalArgumentException("Üye bulunamadı!");
        }
        return member;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }
    
    @Override
    public List<Member> searchMembers(String keyword) {
        List<Member> allMembers = memberDAO.getAllMembers();
        return allMembers.stream()
                .filter(m -> m.getName().toLowerCase().contains(keyword.toLowerCase())
                        || m.getSurname().toLowerCase().contains(keyword.toLowerCase())
                        || m.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
    
    private void validateMember(Member member, boolean isUpdate) {
        if (isUpdate && member.getId() <= 0) {
            throw new IllegalArgumentException("Üye ID geçersiz!");
        }
        if (member.getName() == null || member.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Üye adı boş olamaz!");
        }
        if (member.getSurname() == null || member.getSurname().trim().isEmpty()) {
            throw new IllegalArgumentException("Üye soyadı boş olamaz!");
        }
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-posta boş olamaz!");
        }
        if (!isValidEmail(member.getEmail())) {
            throw new IllegalArgumentException("E-posta formatı geçersiz!");
        }
        if (member.getPhone() != null && member.getPhone().length() > 20) {
            throw new IllegalArgumentException("Telefon numarası çok uzun!");
        }
        // Gerekirse başka validasyonlar eklenebilir
    }

    private boolean isValidEmail(String email) {
        //e-posta regex kontrolü
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }
    
}

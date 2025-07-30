
package model;

public class Member {
    
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private int userId;

    public Member() {
    }

    public Member(int id, String name, String surname, String email, String phone, int userId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return (name != null ? name : "") + " " + (surname != null ? surname : "") + " (" + (email != null ? email : "") + ")";
    }
    
}

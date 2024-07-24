package app.entities;

public class Customer {
    private int userid;
    private String name;
    private String lastName;
    private String email;
    private String password;

    public Customer(int userid, String name, String lastName, String email, String lastname) {
        this.userid = userid;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Customer(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

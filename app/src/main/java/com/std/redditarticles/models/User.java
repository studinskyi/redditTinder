package com.std.redditarticles.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private boolean isAdministrator;
    private String firstName;
    private String surname;
    private String secondName;
    private String email;
    private String phone;

    public User(String login, String password, Boolean isAdministrator
            , String firstName, String surname, String secondName
            , String email, String phone) {
        this.id = System.currentTimeMillis() + (int) (Math.random() * 100); //(new Random()).nextInt(100)
        this.login = login;
        this.password = password;
        this.isAdministrator = isAdministrator;
        this.firstName = firstName;
        this.surname = surname;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
    }

    public User(long id, String login, String password, Boolean isAdministrator
            , String firstName, String surname, String secondName
            , String email, String phone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isAdministrator = isAdministrator;
        this.firstName = firstName;
        this.surname = surname;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof User))
            return false;

        if (this.id != ((User) obj).getId()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return (int) (21 + id * 41);
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
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
}

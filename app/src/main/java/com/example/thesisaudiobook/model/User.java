package com.example.thesisaudiobook.model;

public class User {

    private String userName, email, access, photo, rol;

    public User() {
    }

    public User(String userName, String email, String access, String photo, String rol) {
        this.userName = userName;
        this.email = email;
        this.access = access;
        this.photo = photo;
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

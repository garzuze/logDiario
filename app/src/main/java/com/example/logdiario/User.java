package com.example.logdiario;

public class User {

    public User() {
    }

    public User(int idUser, String nameUser, String emailUser, String pwdUser) {
        this.setidUser(idUser);
        this.setnameUser(nameUser);
        this.setemailUser(emailUser);
        this.setpwdUser(pwdUser);
    }

    private void pwdUser(String pwdUser) {

    }

    @Override
    public String toString() {
        return "User{ idUser=" + idUser + ", nameUser=" + nameUser + ", emailUser=" + emailUser +
                ", pwdUser=" + pwdUser + "}";
    }

    private int idUser;
    private String nameUser;
    private String emailUser;
    private String pwdUser;


    protected int getIdUser() {
        return idUser;
    }

    protected void setidUser(int idUser) {
        this.idUser = idUser;
    }

    protected String getNameUser() {
        return nameUser;
    }

    protected void setnameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    protected String getemailUser() {
        return emailUser;
    }

    protected void setemailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    protected void setpwdUser(String pwdUser) {
        this.pwdUser = pwdUser;
    }

    protected String getpwdUser() {
        return pwdUser;
    }
}

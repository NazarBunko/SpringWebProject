package spring.web.project.models;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String photo;

    /*public Person(int id, String name, String surname, String email, String password, String photo) throws UnsupportedEncodingException {
        this.name = convertText(name);
        this.surname = convertText(surname);
        this.email = convertText(email);
        this.password = convertText(password);
        this.id = id;
        this.photo = photo;
    }

    private String convertText(String text) throws UnsupportedEncodingException {
        return new String(text.getBytes("ISO-8859-1"), Charset.forName("UTF-8"));
    }*/

    public Person(int id, String name, String surname, String email, String password, String photo) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.id = id;
        this.photo = photo;
    }

    public Person() {}

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

    public String getPhoto() {
        return "http://surl.li/tzttyg";
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

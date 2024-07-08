package spring.web.project.models;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String photo;

    public Person(int id, String name, String surname, String email, String photo) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Person getPerson(){
        return new Person(id, name, surname, email, photo);
    }

    public static String decodeHtmlEntities(String input) {
        Pattern pattern = Pattern.compile("&#(\\d+);");
        Matcher matcher = pattern.matcher(input);
        StringBuffer decodedString = new StringBuffer();

        while (matcher.find()) {
            String charCode = matcher.group(1);
            int codePoint = Integer.parseInt(charCode);
            String character = new String(Character.toChars(codePoint));
            matcher.appendReplacement(decodedString, character);
        }
        matcher.appendTail(decodedString);

        return decodedString.toString();
    }
}

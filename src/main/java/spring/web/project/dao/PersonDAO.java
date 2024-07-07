package spring.web.project.dao;

import org.springframework.stereotype.Component;
import spring.web.project.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people = new ArrayList<>();
    private static int COUNT = -1;

    public void addPerson(String name, String surname, String email, String photo){
        people.add(new Person(++COUNT, name, surname, email, photo));
    }

    public List<Person> index(){
        return people;
    }

    public Person one(String email){
        for (Person person : people) {
            if (person.getEmail().equals(email)) {
                return person;
            }
        }
        return null;
    }

    public void delete(String email) {
        people.removeIf(person -> person.getEmail().equals(email));
    }

}
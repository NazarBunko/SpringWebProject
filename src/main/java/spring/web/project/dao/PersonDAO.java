package spring.web.project.dao;

import org.springframework.stereotype.Component;
import spring.web.project.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people = new ArrayList<>();
    private static int COUNT = 0;

    public void add(Person person) {
        person.setId(COUNT++);
        people.add(person);
    }

    public List<Person> index() {
        return people;
    }

    public Person one(int id) {
        for (Person person : people) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }

    public void update(int id, Person updatedPerson) {
        for (Person person : people) {
            if (person.getId() == id) {
                person.setName(updatedPerson.getName());
                person.setSurname(updatedPerson.getSurname());
                person.setEmail(updatedPerson.getEmail());
                return;
            }
        }
    }

    public Boolean checkEmail(String email){
        for (Person person : people) {
            if (person.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
}

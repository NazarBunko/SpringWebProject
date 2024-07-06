package spring.web.project.dao;

import org.springframework.stereotype.Component;
import spring.web.project.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people = new ArrayList<>();
    private static int COUNT = -1;

    public int addPeople(String name, String surname, String email, String photo){
        people.add(new Person(++COUNT, name, surname, email, photo));
        return COUNT;
    }

    public List<Person> index(){
        return people;
    }

    public Person one(int id){
        return people.get(id);
    }
}

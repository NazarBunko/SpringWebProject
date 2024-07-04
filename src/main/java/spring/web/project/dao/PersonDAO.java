package spring.web.project.dao;

import org.springframework.stereotype.Component;
import spring.web.project.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people;
    private static int COUNT;

    {
        people = new ArrayList<>();

        people.add(new Person(++COUNT, "Nazar"));
        people.add(new Person(++COUNT, "Denys"));
        people.add(new Person(++COUNT, "Max"));
    }

    public List<Person> index(){
        return people;
    }

    public Person one(int id){
        return people.get(id - 1);
    }
}

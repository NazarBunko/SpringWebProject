package spring.web.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.web.project.dao.PersonDAO;
import spring.web.project.models.Person;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO dao;

    @Autowired
    public PeopleController(PersonDAO dao) {
        this.dao = dao;
    }

    @GetMapping("/{id}")
    public String one(@PathVariable("id") int id, Model model){
        model.addAttribute("person", dao.one(id));
        return "people/one";
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", dao.index());
        return "people/index";
    }

    @GetMapping("/new")
    public String add(){
        return "people/new";
    }

    @PostMapping("/newPage")
    public String newPerson(Model model, HttpServletRequest request){
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String photo = request.getParameter("photo");

        int id = dao.addPeople(name, surname, email, photo);

        model.addAttribute("person", dao.one(id));
        return "people/one";
    }
}

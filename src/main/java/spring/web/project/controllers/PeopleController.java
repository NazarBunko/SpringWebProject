package spring.web.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.web.project.dao.PersonDAO;
import spring.web.project.models.Person;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("people", dao.index());
        return "/people/index";
    }

    @GetMapping("/new")
    public String add(){
        return "people/new";
    }

    @PostMapping("")
    public String newPerson(Model model, HttpServletRequest request){
        System.setProperty("file.encoding", "UTF-8");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String photo = request.getParameter("photo");
        photo = "https://kartinki.pics/pics/uploads/posts/2022-09/thumbs/1662405711_5-kartinkin-net-p-ikonka-cheloveka-minimalizm-vkontakte-5.png";
        name = Person.decodeHtmlEntities(name);
        surname = Person.decodeHtmlEntities(surname);
        email = Person.decodeHtmlEntities(email);

        dao.addPerson(name, surname, email, photo);
        model.addAttribute("people", dao.index());
        return "/people/index";
    }
}

package spring.web.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.web.project.dao.PersonDAO;
import spring.web.project.models.Person;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO dao = new PersonDAO();

    @GetMapping("/person")
    public String one(@RequestParam("email") String email, Model model){
        if(dao.one(email) == null){
            model.addAttribute("people", dao.index());
            return "/people/index";
        }
        model.addAttribute("person", dao.one(email));
        return "people/one";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("email") String email, Model model){
        dao.delete(email);
        model.addAttribute("people", dao.index());
        return "people/index";
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

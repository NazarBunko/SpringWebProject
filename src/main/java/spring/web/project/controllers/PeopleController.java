package spring.web.project.controllers;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.web.project.dao.PersonDAO;
import spring.web.project.models.Person;

import javax.servlet.http.HttpServletRequest;

@Controller
@PropertySource("classpath:properties.properties")
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO dao = new PersonDAO();

    @GetMapping("/person")
    public String one(@RequestParam("id") int id, Model model){
        if(dao.one(id) == null){
            return "redirect:/people";
        }
        model.addAttribute("person", dao.one(id));
        return "people/one.html";
    }

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("people", dao.index());
        return "/people/index.html";
    }

    @GetMapping("/new")
    public String add(){
        return "people/new.html";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", dao.one(id));
        return "people/edit.html";
    }

    @PatchMapping("/{id}/edit")
    public String updatePerson(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        dao.update(id, new Person(0, request.getParameter("name"), request.getParameter("surname"), request.getParameter("email"), ""));
        model.addAttribute("people", dao.index());
        return "redirect:/people";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model){
        dao.delete(id);
        model.addAttribute("people", dao.index());
        return "redirect:/people";
    }

    @PostMapping("")
    public String newPerson(Model model, HttpServletRequest request){
        Person person = new Person(0, request.getParameter("name"), request.getParameter("surname"), request.getParameter("email"), "image.png");
        person.setPhoto("https://kartinki.pics/pics/uploads/posts/2022-09/thumbs/1662405711_5-kartinkin-net-p-ikonka-cheloveka-minimalizm-vkontakte-5.png");
        dao.add(person);
        model.addAttribute("people", dao.index());
        return "redirect:/people";
    }
}

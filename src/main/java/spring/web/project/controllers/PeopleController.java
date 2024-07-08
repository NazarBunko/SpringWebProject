package spring.web.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.web.project.dao.PersonDAO;
import spring.web.project.models.Person;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO dao = new PersonDAO();

    @GetMapping("/person")
    public String one(@RequestParam("id") int id, Model model){
        if(dao.one(id) == null){
            model.addAttribute("people", dao.index());
            return "/people/index";
        }
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

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", dao.one(id));
        return "people/edit";
    }

    @PatchMapping("/{id}/edit")
    public String updatePerson(@ModelAttribute("person.getPerson()") Person updatedPerson, @PathVariable("id") int id) {
        dao.update(id, updatedPerson);
        return "redirect:/people";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model){
        System.out.println("hello");
        dao.delete(id);
        model.addAttribute("people", dao.index());
        return "/people/index";
    }

    @PostMapping("")
    public String newPerson(Model model, HttpServletRequest request){
        Person person = new Person(0, request.getParameter("name"), request.getParameter("surname"), request.getParameter("email"), "");
        person.setPhoto("https://kartinki.pics/pics/uploads/posts/2022-09/thumbs/1662405711_5-kartinkin-net-p-ikonka-cheloveka-minimalizm-vkontakte-5.png");
        dao.add(person);
        model.addAttribute("people", dao.index());
        return "redirect:/people";
    }
}

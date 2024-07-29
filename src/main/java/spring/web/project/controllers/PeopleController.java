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
        if(dao.show(id) == null){
            return "redirect:/people";
        }
        model.addAttribute("person", dao.show(id));
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
        model.addAttribute("person", dao.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}/edit")
    public String updatePerson(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        String email = request.getParameter("email");

        if (!dao.checkEmail(email, id)) {
            model.addAttribute("error", true);
            model.addAttribute("person", dao.show(id));
            return "/people/one";
        } else {
            dao.update(id, new Person(id, request.getParameter("name"), request.getParameter("surname"), email, request.getParameter("password"), null));
            model.addAttribute("person", dao.show(id));
            return "/people/one";
        }
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model){
        dao.delete(id);
        model.addAttribute("people", dao.index());
        return "redirect:/people";
    }

    @PostMapping("")
    public String newPerson(Model model, HttpServletRequest request) {
        String email = request.getParameter("email");

        if (!dao.checkEmail(email, 0)) {
            model.addAttribute("error", true);
            return "/people/new";
        } else {
            Person person = new Person(0, request.getParameter("name"), request.getParameter("surname"), email, request.getParameter("password"), null);
            person.setPhoto("http://surl.li/tzttyg");
            dao.add(person);
            return "redirect:/people";
        }
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        if (dao.checkLogin(request.getParameter("email"), request.getParameter("password"))) {
            return "redirect:/people";
        } else {
            return null;
        }
    }
}

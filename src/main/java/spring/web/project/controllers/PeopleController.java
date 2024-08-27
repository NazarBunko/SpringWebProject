package spring.web.project.controllers;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.web.project.dao.PersonDAO;
import spring.web.project.models.Person;

@Controller
@PropertySource("classpath:properties.properties")
@RequestMapping("/people")
public class PeopleController {

    private static int id;

    private final PersonDAO dao = new PersonDAO();

    @GetMapping("/person")
    public String one(@RequestParam("id") int id, Model model) {
        Person person = dao.show(id);
        if (person == null) {
            return "redirect:/people/" + this.id;
        }
        model.addAttribute("id", this.id);
        model.addAttribute("person", person);
        return "/people/one";
    }

    @GetMapping("/{id}")
    public String index(Model model, @PathVariable("id") int id) {
        model.addAttribute("people", dao.index());
        model.addAttribute("person", dao.show(id));
        return "/people/index";
    }

    @GetMapping("/new")
    public String add() {
        return "/people/new";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", dao.show(id));
        return "/people/edit";
    }

    @PostMapping("/{id}/edited")
    public String updatePerson(@PathVariable("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model) {
        if (!dao.checkEmail(email, id)) {
            model.addAttribute("error", true);
            model.addAttribute("person", dao.show(id));
            return "people/one";
        } else {
            dao.update(id, new Person(id, name, surname, email, password, null));
            model.addAttribute("person", dao.show(id));
            return "people/one";
        }
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        dao.delete(id);
        if (id == this.id) {
            return "redirect:/people/login";
        }
        model.addAttribute("people", dao.index());
        return "redirect:/people/" + this.id;
    }

    @PostMapping("/add")
    public String newPerson(@RequestParam("name") String name,
                            @RequestParam("surname") String surname,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            Model model) {
        if (!dao.checkEmail(email, 0)) {
            model.addAttribute("error", true);
            return "/people/new";
        } else {
            Person person = new Person(0, name, surname, email, password, null);
            person.setPhoto("http://surl.li/tzttyg");
            int id = dao.add(person);
            this.id = id;
            return "redirect:/people/" + id;
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        Person person = dao.checkLogin(email, password);
        if (person != null) {
            id = person.getId();
            return "redirect:/people/" + this.id;
        }
        model.addAttribute("error", true);
        return "/people/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "/people/login";
    }
}

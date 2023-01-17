package ru.job4j.cinema.contrroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.util.HttpSessionGet;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String session(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user", user);
        HttpSessionGet.getHttpSession(model, httpSession);
        return "index";
    }
}

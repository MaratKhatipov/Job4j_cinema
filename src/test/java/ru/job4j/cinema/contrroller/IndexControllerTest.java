package ru.job4j.cinema.contrroller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndexControllerTest {

    @Test
    void whenSession() {
        User user = new User(1, "userName", "email", "phone");

        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("user")).thenReturn(user);

        IndexController indexController = new IndexController();
        String page = indexController.session(model, httpSession);
        assertEquals(page, "index");
    }
}
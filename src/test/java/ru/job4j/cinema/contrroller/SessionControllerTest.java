package ru.job4j.cinema.contrroller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SessionControllerTest {
    Model model = mock(Model.class);
    HttpSession httpSession = mock(HttpSession.class);
    SessionService sessionService = mock(SessionService.class);
    MultipartFile multipartFile = mock(MultipartFile.class);
    @Test
    void whenSessions() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "Film1"),
                new Session(2, "Film2")
        );

        when(sessionService.findAll()).thenReturn(sessions);
        SessionController sessionController = new SessionController(sessionService);

        String page = sessionController.sessions(model, httpSession);
        System.out.println(page);

        verify(model).addAttribute("sessions", sessions);
        assertEquals(page, "session/sessions");
    }

    @Test
    void whenAddSession() {
        SessionController sessionController = new SessionController(sessionService);

        String page = sessionController.addSession(model, httpSession);
        System.out.println(page);
        assertEquals(page, "session/addSession");
    }


    @Test
    void whenCreateSession() throws IOException {
        Session input = new Session(1, "film", new byte[0]);

        SessionController sessionController = new SessionController(sessionService);

        String page = sessionController.createSession(input, multipartFile);
        System.out.println(page);

        verify(sessionService).save(input);
        assertEquals(page, "redirect:/sessions");
    }


    @Test
    void choiceRow() {
        Session input = new Session(1, "film", new byte[0]);
        int id = 1;

        when(sessionService.findById(id)).thenReturn(input);

        SessionController sessionController = new SessionController(sessionService);

        String page = sessionController.choiceRow(model, httpSession, id);
        System.out.println(page);

        verify(model).addAttribute("session", input);
        verify(model).addAttribute("rows", sessionService.getRowNumbers());
        assertEquals(page, "/session/formChoiceRow");
    }

    @Test
    void choiceCell() {
        Ticket input = new Ticket(1, 1, 1, 3, 10);
        when(httpSession.getAttribute("ticket")).thenReturn(input);

        SessionController sessionController = new SessionController(sessionService);

        String page = sessionController.choiceCell(model, httpSession, input);
        System.out.println(page);

        verify(model).addAttribute("freeCells",
                sessionService.getFreeCells(input.getSessionId(), input.getRow()));
        assertEquals(page, "/session/choiceCell");
    }
}
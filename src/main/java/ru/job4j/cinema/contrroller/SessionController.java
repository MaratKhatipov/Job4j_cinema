package ru.job4j.cinema.contrroller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.util.HttpSessionGet;

import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ThreadSafe
@Controller
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions")
    public String sessions(Model model, HttpSession httpSession) {
        HttpSessionGet.getHttpSession(model, httpSession);
        model.addAttribute("sessions", sessionService.findAll());
        return "session/sessions";
    }

    @GetMapping("/formAddSession")
    public String addSession(Model model, HttpSession httpSession) {
        HttpSessionGet.getHttpSession(model, httpSession);
        model.addAttribute("session", new Session(0, "session name", new byte[0]));
        return "session/addSession";
    }

    @PostMapping("/createSession")
    public String createSession(@ModelAttribute Session session,
                                @RequestParam("file")MultipartFile file) throws IOException {
        session.setPhoto(file.getBytes());
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @GetMapping("/posterSession/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") Integer sessionId) {
        Session session = sessionService.findById(sessionId);
        return ResponseEntity
                .ok()
                .headers(new HttpHeaders())
                .contentLength(session.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(session.getPhoto()));
    }

    @GetMapping("formChoiceRow/{sessionId}")
    public String choiceRow(Model model, HttpSession httpSession,
                            @PathVariable ("sessionId") int id) {
        Ticket sessionTicket = new Ticket();
        Session session = sessionService.findById(id);
        sessionTicket.setSessionId(id);
        httpSession.setAttribute("ticket", sessionTicket);
        model.addAttribute("session", session);
        model.addAttribute("rows", sessionService.getRowNumbers());
        HttpSessionGet.getHttpSession(model, httpSession);
        return "/session/formChoiceRow";
    }

    @GetMapping("formChoiceCell")
        public String choiceCell(Model model, HttpSession httpSession,
                                 @ModelAttribute("ticket") Ticket ticket) {
        Ticket ticketSession = (Ticket) httpSession.getAttribute("ticket");
        model.addAttribute("freeCells",
                sessionService.getFreeCells(ticketSession.getSessionId(),
                ticket.getRow()));
        ticketSession.setRow(ticket.getRow());
        HttpSessionGet.getHttpSession(model, httpSession);
        return "/session/choiceCell";
        }
}

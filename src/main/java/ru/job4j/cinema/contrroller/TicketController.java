package ru.job4j.cinema.contrroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.HttpSessionGet;

import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class TicketController {
    private final TicketService ticketService;
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }
    @GetMapping("/showInfo")
    public String ticketInfo(Model model,
                             HttpSession httpSession,
                             @ModelAttribute("ticket")Ticket ticket) {
        User user = (User) httpSession.getAttribute("user");
        Ticket ticketSession = (Ticket) httpSession.getAttribute("ticket");
        ticketSession.setCell(ticket.getCell());
        model.addAttribute("user", user);
        model.addAttribute("movie", sessionService.findById(ticketSession.getSessionId()));
        return "/ticket/ticketInfo";
    }

    @PostMapping("/createTicket")
    public String createTicket(Model model, HttpSession httpSession) {
        Ticket ticketSession = (Ticket) httpSession.getAttribute("ticket");
        User user = (User) httpSession.getAttribute("user");
        HttpSessionGet.getHttpSession(model, httpSession);
        ticketSession.setUserId(user.getId());
        Optional<Ticket> ticketOptional = ticketService.save(ticketSession);
        if (ticketOptional.isPresent()) {
            return "/ticket/successfulBuy";
        } else {
            return "/ticket/failBuy";
        }
    }
}

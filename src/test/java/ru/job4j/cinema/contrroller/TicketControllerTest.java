package ru.job4j.cinema.contrroller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    TicketService ticketService = mock(TicketService.class);
    SessionService sessionService = mock(SessionService.class);
    Model model = mock(Model.class);
    HttpSession httpSession = mock(HttpSession.class);
    @Test
    void ticketInfo() {
        Session session = new Session(1, "Film");
        Ticket ticket = new Ticket(1, 1, 1, 2, 10);
        User user = new User(1, "NameUser");

        when(httpSession.getAttribute("user")).thenReturn(user);
        when(httpSession.getAttribute("ticket")).thenReturn(ticket);
        when(sessionService.findById(ticket.getSessionId())).thenReturn(session);
        TicketController ticketController = new TicketController(ticketService, sessionService);

        String page = ticketController.ticketInfo(model, httpSession, ticket);
        System.out.println(page);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("movie", sessionService.findById(ticket.getSessionId()));

        assertEquals("/ticket/ticketInfo", page);
    }

    @Test
    void whenCouldCreateTicket() {
        Ticket ticket = new Ticket(1, 1, 1, 2, 10);
        User user = new User(1, "NameUser");

        when(httpSession.getAttribute("user")).thenReturn(user);
        when(httpSession.getAttribute("ticket")).thenReturn(ticket);
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));
        TicketController ticketController = new TicketController(ticketService, sessionService);

        String page = ticketController.createTicket(model, httpSession);
        System.out.println(page);

        assertEquals("/ticket/successfulBuy", page);
    }

    @Test
    void whenCouldNotCreateTicket() {
        Ticket ticket = new Ticket(1, 1, 1, 2, 10);
        User user = new User(1, "NameUser");

        when(httpSession.getAttribute("user")).thenReturn(user);
        when(httpSession.getAttribute("ticket")).thenReturn(ticket);
        when(ticketService.save(ticket)).thenReturn(Optional.empty());
        TicketController ticketController = new TicketController(ticketService, sessionService);

        String page = ticketController.createTicket(model, httpSession);
        System.out.println(page);

        assertEquals("/ticket/failBuy", page);
    }
}
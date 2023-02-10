package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Job4jCinemaApplication;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TicketRepositoryTest {
    private static final BasicDataSource POOL = new Job4jCinemaApplication().loadPool();
    private static  final TicketRepository TICKET_REPOSITORY = new TicketRepository(POOL);
    private static final SessionRepository SESSION_REPOSITORY = new SessionRepository(POOL);
    private static final UserRepository USER_REPOSITORY = new UserRepository(POOL);

    User user = new User(1, "Name", "Email", "123", "Pass");
    Session session = new Session(1, "CreateFilm");
    @BeforeEach
    public void addData() {
        USER_REPOSITORY.add(user);
        SESSION_REPOSITORY.insert(session);
    }

    @AfterEach
    public void reset() {
        TICKET_REPOSITORY.reset();
        SESSION_REPOSITORY.reset();
        USER_REPOSITORY.reset();
    }


    @Test
    void whenAddTicket() {
        Ticket ticket = new Ticket(1, session.getId(), user.getId(), 3, 10);
        TICKET_REPOSITORY.add(ticket);
        Ticket ticketInDB = TICKET_REPOSITORY.findById(ticket.getId());
        System.out.println(ticketInDB);
        Assertions.assertThat(ticket.getSessionId()).isEqualTo(ticketInDB.getSessionId());
    }

    @Test
    void findSessionAndRow() {
        Ticket ticket1 = TICKET_REPOSITORY.add(new Ticket(1, session.getId(), user.getId(), 3, 10)).get();
        Ticket ticket2 = TICKET_REPOSITORY.add(new Ticket(2, session.getId(), user.getId(), 3, 9)).get();
        Ticket ticket3 = TICKET_REPOSITORY.add(new Ticket(3, session.getId(), user.getId(), 3, 8)).get();
        Ticket ticket4 = TICKET_REPOSITORY.add(new Ticket(4, session.getId(), user.getId(), 3, 7)).get();
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2, ticket3, ticket4);
        List<Ticket> ticketInDB = TICKET_REPOSITORY.findSessionAndRow(ticket1.getSessionId(), ticket1.getRow());
        System.out.println(ticketInDB);
        Assertions.assertThat(tickets).isEqualTo(ticketInDB);
    }
}
package ru.job4j.cinema.service.ipml;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.SessionRepository;
import ru.job4j.cinema.repository.TicketRepository;
import ru.job4j.cinema.service.SessionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SessionServiceImpl implements SessionService {

    private final static List<Integer> ROW_NUMBERS = IntStream.rangeClosed(1, 5).boxed().toList();
    private final static List<Integer> CELL_NUMBERS = IntStream.rangeClosed(1, 10).boxed().toList();
    private final SessionRepository store;
    private final TicketRepository ticketRepository;

    public SessionServiceImpl(SessionRepository store, TicketRepository ticketRepository) {
        this.store = store;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Collection<Session> findAll() {
        return store.findAll();
    }

    @Override
    public Session findById(int id) {
        return store.findById(id);
    }

    @Override
    public void save(Session session) {
        store.insert(session);
    }

    @Override
    public List<Integer> getRowNumbers() {
        return new ArrayList<>(ROW_NUMBERS);
    }

    @Override
    public List<Integer> getCellNumbers() {
        return new ArrayList<>(CELL_NUMBERS);

    }

    @Override
    public List<Integer> getFreeCells(int sessionId, int posRow) {
        List<Integer> cells = getCellNumbers();
        TicketServiceImpl ticketService = new TicketServiceImpl(ticketRepository);
        for (Ticket ticket : ticketService.findSessionAndRow(sessionId, posRow)) {
            if (cells.contains(ticket.getCell())) {
                cells.remove(Integer.valueOf(ticket.getCell()));
            }
        }
        return cells;
    }
}

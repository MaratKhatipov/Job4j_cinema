package ru.job4j.cinema.service.ipml;

import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;
import ru.job4j.cinema.service.TicketService;

import java.util.Collection;
import java.util.Optional;

public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.add(ticket);
    }

    @Override
    public Collection<Ticket> findSessionAndRow(int sessionId, int posRow) {
        return ticketRepository.findSessionAndRow(sessionId, posRow);
    }
}

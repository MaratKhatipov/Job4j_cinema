package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import javax.annotation.concurrent.ThreadSafe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
public class TicketRepository {
    private static final Logger LOG_TICKET_STORE = LoggerFactory.getLogger(TicketRepository.class.getName());
    private final BasicDataSource pool;

    private static final String SELECT = "SELECT * FROM ticket";

    private static final String INSERT = "INSERT INTO ticket(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";

    private static final String FIND_BY_ID = "SELECT * FROM ticket WHERE id = ?";


    private static final String FIND = "SELECT FROM ticket WHERE session_id = ? and pos_row = ?";

    public TicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getPosRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(1);
                }
                result = Optional.of(ticket);
            }
        } catch (SQLException e) {
            LOG_TICKET_STORE.error("add ticket, SQLException", e);
        }
        return result;
    }
    public List<Ticket> findSessionAndRow(int sessionId, int posRow) {
        List<Ticket> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)) {
            ps.setInt(1, sessionId);
            ps.setInt(2, posRow);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(new Ticket(
                            it.getInt("id"),
                            it.getInt("session_id"),
                            it.getInt("user_id"), it.getInt("pos_row"), it.getInt("cell")));
                }
            }
        } catch (SQLException e) {
            LOG_TICKET_STORE.error("findSessionAndRow, SQLException", e);
        }
        return result;
    }


}

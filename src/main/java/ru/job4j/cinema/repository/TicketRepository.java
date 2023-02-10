package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;
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

    private static final String INSERT = "INSERT INTO ticket(session_id, user_id, pos_row, cell) VALUES (?, ?, ?, ?)";


    private static final String FIND = "SELECT * FROM ticket WHERE session_id = ? and pos_row = ?";

    private static final String FIND_BY_ID = "select * from ticket where id = ?";

    private final static String DELETE = "DELETE FROM ticket";

    public TicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getUserId());
            ps.setInt(3, ticket.getRow());
            ps.setInt(4, ticket.getCell());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt("id"));
                }
                result = Optional.of(ticket);
            }
        } catch (SQLException e) {
            LOG_TICKET_STORE.error("add ticket, SQLException", e);
        }
        return result;
    }

    /**
     * метод находит в базе данных все купленные билеты в ряду
     *
     * @param sessionId - id сессии
     * @param posRow       - ряд
     * @return возвращает коллекцию List с оплаченными билетами в ряду
     */
    public List<Ticket> findSessionAndRow(int sessionId, int posRow) {
        List<Ticket> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)) {
            ps.setInt(1, sessionId);
            ps.setInt(2, posRow);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(getTicket(it));
                    LOG_TICKET_STORE.info("добавил билет с рядом " + posRow);
                }
            }
        } catch (SQLException e) {
            LOG_TICKET_STORE.error("findSessionAndRow, SQLException", e);
        }
        return result;
    }

    public Ticket findById(int id) {
        Ticket ticket = new Ticket();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    ticket = getTicket(it);
                }
            }
        } catch (SQLException e) {
            LOG_TICKET_STORE.error("find by id, SQLException", e);
        }
        return ticket;
    }

    private Ticket getTicket(ResultSet it) throws SQLException {
        return new Ticket(
                it.getInt("id"),
                it.getInt("session_id"),
                it.getInt("user_id"),
                it.getInt("pos_row"),
                it.getInt("cell"));
    }

    public void reset() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(DELETE)
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG_TICKET_STORE.error("Error:", e);
        }
    }
}

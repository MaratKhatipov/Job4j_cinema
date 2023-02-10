package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionRepository {
    private static final Logger LOG_SESSION = LoggerFactory.getLogger(SessionRepository.class.getName());

    private final BasicDataSource pool;

    private static final String FIND_ALL = "select * from session";

    private static final String INSERT = "insert into session(name, photo) values (?, ?)";

    private static final String FIND_BY_ID = "select * from session where id = ?";

    private final static String DELETE = "DELETE FROM session";

    public SessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Session session = new Session(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBytes("photo")
                    );
                    sessions.add(session);
                }
            }
        } catch (SQLException e) {
            LOG_SESSION.error("find ALL, SQL Exception", e);
        }
        return sessions;
    }

    public Session insert(Session session) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(INSERT,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.setBytes(2, session.getPhoto());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            LOG_SESSION.error("insert, SQLException", e);
        }
        return session;
    }

    public Session findById(int id) {
        Session session = new Session();
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    session = new Session(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getBytes("photo")
                    );
                }
            }
        } catch (SQLException e) {
            LOG_SESSION.error("find by id, SQLException", e);
        }
        return session;
    }

    public void reset() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(DELETE)
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG_SESSION.error("Error:", e);
        }
    }
}

package ru.job4j.job4j_cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final Logger LOG_U_DB_STORE = LoggerFactory.getLogger(UserRepository.class.getName());
    private final BasicDataSource pool;

    private static final String SELECT = "SELECT * FROM users";

    private static final String INSERT = "INSERT INTO users(username, email, phone, password) VALUES (?, ?, ?, ?)";

    private static final String UPDATE = """
                                         UPDATE users
                                         SET email = ?, password = ?
                                         WHERE id = ?
                                         """;
    private static final String FIND_BY_ID = "SELECT FROM users WHERE id = ?";

    private static final String FIND_BY_EMAIL_PWD = """
                                                    SELECT id, username, email, phone, password 
                                                    FROM users 
                                                    WHERE email = ? and password = ?
                                                    """;

    public UserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    User user = initUser(it);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            LOG_U_DB_STORE.error("Exception in findAll", e);
        }
        return users;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(1);
                }
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            LOG_U_DB_STORE.error("add, SQLException", e);
        }
        return result;
    }

    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG_U_DB_STORE.error("SQLException in update", e);
        }
    }

    public User findById(int id) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet it = ps.executeQuery();
            if (it.next()) {
                user = initUser(it);
            }
        } catch (SQLException e) {
            LOG_U_DB_STORE.error("SQLException in findById", e);
        }
        return user;
    }

    public Optional<User> findByEmailAndPwd(String email, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_EMAIL_PWD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(initUser(it));
                }
            }
        } catch (SQLException e) {
            LOG_U_DB_STORE.error("SQLException in findByEmailAndPwd", e);
        }
        return result;
    }

    private static User initUser(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("username"),
                it.getString("email"),
                it.getString("phone"),
                it.getString("password")

        );
    }
}

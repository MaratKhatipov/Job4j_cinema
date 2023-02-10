package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final Logger LOG_U_DB_STORE = LoggerFactory.getLogger(UserRepository.class.getName());
    private final BasicDataSource pool;

    private static final String INSERT = "INSERT INTO users(username, email, phone, password) VALUES (?, ?, ?, ?)";

    private static final String FIND_BY_EMAIL_PWD = """
                                                    SELECT id, username, email, phone, password
                                                    FROM users
                                                    WHERE email = ? and password = ?
                                                    """;
    private final static String DELETE = "DELETE FROM users";

    public UserRepository(BasicDataSource pool) {
        this.pool = pool;
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
                    user.setId(id.getInt(1));
                }
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            LOG_U_DB_STORE.error("UserRepository ошибка добавления пользователя, SQLException", e);
        }
        return result;
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
            LOG_U_DB_STORE.error("UserRepository ошибка поиска пользователя по email, password", e);
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
    public void reset() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(DELETE)
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG_U_DB_STORE.error("Error:", e);
        }
    }

}

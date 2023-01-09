package ru.job4j.cinema.service.ipml;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;
import ru.job4j.cinema.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository store;

    public UserServiceImpl(UserRepository store) {
        this.store = store;
    }

    @Override
    public Optional<User> add(User user) {
        return store.add(user);
    }

    @Override
    public Optional<User> findByEmailAndPwd(String email, String password) {
        return store.findByEmailAndPwd(email, password);
    }
}

package ru.job4j.cinema.service.ipml;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;
import ru.job4j.cinema.service.SessionService;

import java.util.Collection;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository store;

    public SessionServiceImpl(SessionRepository store) {
        this.store = store;
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
    public void update(Session session) {
        store.update(session);
    }
}

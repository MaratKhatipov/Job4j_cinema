package ru.job4j.cinema.service.ipml;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;
import ru.job4j.cinema.service.SessionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class SessionServiceImpl implements SessionService {

    private final static List<Integer> ROW_NUMBERS = IntStream.rangeClosed(1, 5).boxed().toList();
    private final static List<Integer> CELL_NUMBERS = IntStream.rangeClosed(1, 10).boxed().toList();
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

    @Override
    public List<Integer> getRowNumbers() {
        return new ArrayList<>(ROW_NUMBERS);
    }

    @Override
    public List<Integer> getCellNumbers() {
        return new ArrayList<>(CELL_NUMBERS);

    }
}

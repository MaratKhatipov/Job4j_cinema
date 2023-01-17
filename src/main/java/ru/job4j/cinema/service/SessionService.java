package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.Collection;
import java.util.List;

public interface SessionService {
    Collection<Session> findAll();

    Session findById(int id);

    void save(Session session);

    void update(Session session);

    public List<Integer> getRowNumbers();

    public List<Integer> getCellNumbers();
}

package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Job4jCinemaApplication;
import ru.job4j.cinema.model.Session;

import java.util.ArrayList;
import java.util.List;

class SessionRepositoryTest {
    private static final BasicDataSource POOL = new Job4jCinemaApplication().loadPool();
    private static final SessionRepository STORE = new SessionRepository(POOL);

    @AfterEach
    public void reset() {
        STORE.reset();
    }

    @Test
    public void whenCreateSession() {
        Session session = new Session(1, "CreateFilm");
        STORE.insert(session);
        Session sessionInDB = STORE.findById(session.getId());
        Assertions.assertThat(session.getName()).isEqualTo(sessionInDB.getName());
    }

    @Test
    public void whenCreateSessionAndFindById() {
        Session session = STORE.insert(new Session(1, "findFilmById"));
        Session sessionExpected1 = STORE.findById(session.getId());
        Assertions.assertThat(sessionExpected1).isEqualTo(session);
    }

    @Test
    public void whenCreateThenFindAll() {
        Session session1 = STORE.insert(new Session(1, "Film1"));
        Session session2 = STORE.insert(new Session(2, "Film2"));
        Session session3 = STORE.insert(new Session(3, "Film3"));
        Session session4 = STORE.insert(new Session(4, "Film4"));
        List<Session> list = new ArrayList<>();
        list.add(session1);
        list.add(session2);
        list.add(session3);
        list.add(session4);
        List<Session> expectedResult = STORE.findAll();
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertThat(expectedResult.get(i).getName()).isEqualTo(list.get(i).getName());
        }
    }

}
package ru.job4j.cinema;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
public class Job4jCinemaApplication {
    private static final Logger LOG = LoggerFactory.getLogger(Job4jCinemaApplication.class.getName());

    private Properties loadDBProperties() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(Job4jCinemaApplication.class.getClassLoader().getResourceAsStream("db.properties")))
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error("IllegalStateException db.properties - ", e);
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOG.error("IllegalStateException jdbc.driver - ", e);
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    @Bean
    public BasicDataSource loadPool() {
        Properties cfg = loadDBProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    public static void main(String[] args) {
        SpringApplication.run(Job4jCinemaApplication.class, args);
        System.out.println("Go to http://localhost:8080/session");
    }

}

package ru.job4j.cinema.model;

import java.util.Arrays;
import java.util.Objects;

public class Session {
    private int id;
    private String name;
    private byte[] photo;

    public Session() {
    }

    public Session(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Session(int id, String name, byte[] photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id && Objects.equals(name, session.name) && Arrays.equals(photo, session.photo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Session{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", photo=" + Arrays.toString(photo) + '}';
    }
}

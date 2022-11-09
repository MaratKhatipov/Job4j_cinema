package ru.job4j.job4j_cinema.model;

import java.util.Objects;

public class Ticket {
    private int id;
    private int sessionId;
    private int user_id;
    private int posRow;
    private int cell;

    public Ticket() {
    }

    public Ticket(int id, int sessionId, int user_id, int posRow, int cell) {
        this.id = id;
        this.sessionId = sessionId;
        this.user_id = user_id;
        this.posRow = posRow;
        this.cell = cell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{"
               + "id=" + id
               + ", sessionId=" + sessionId
               + ", user_id=" + user_id
               + ", posRow=" + posRow
               + ", cell=" + cell
               + '}';
    }
}

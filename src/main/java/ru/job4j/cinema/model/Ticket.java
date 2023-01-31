package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {
    private int id;
    private int sessionId;
    private int userId;
    private int row;
    private int cell;

    public Ticket() {
    }

    public Ticket(int id, int sessionId, int userId, int row, int cell) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.row = row;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
               + ", user_id=" + userId
               + ", posRow=" + row
               + ", cell=" + cell
               + '}';
    }
}

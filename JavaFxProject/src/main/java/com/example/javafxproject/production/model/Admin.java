package com.example.javafxproject.production.model;

import java.util.Objects;

public class Admin extends User{

    public Admin(Long id, String username, String password) {
        super(id, username, password);
    }
    public Admin(User user) {
        super(user.getId(), user.getUsername(), user.getPassword());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin admin)) return false;
        return Objects.equals(getId(), admin.getId()) && Objects.equals(getUsername(), admin.getUsername()) && Objects.equals(getPassword(), admin.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword());
    }
}

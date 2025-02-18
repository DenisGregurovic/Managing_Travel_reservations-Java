package production.model;

import production.abstractClass.NamedEntity;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger extends NamedEntity {
    Long id;
    String surname;
    String email;
    String phoneNumber;
    LocalDate dateOfBirth;
    String username;
    String password;

    public Passenger(Long id, String name, String surname, String email, String phoneNumber, LocalDate dateOfBirth,String username,String password) {
        super(name);
        this.id = id;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.username=username;
        this.password=password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), passenger.getId()) && Objects.equals(getSurname(), passenger.getSurname()) && Objects.equals(getEmail(), passenger.getEmail()) && Objects.equals(getPhoneNumber(), passenger.getPhoneNumber()) && Objects.equals(getDateOfBirth(), passenger.getDateOfBirth()) && Objects.equals(getUsername(), passenger.getUsername()) && Objects.equals(getPassword(), passenger.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getSurname(), getEmail(), getPhoneNumber(), getDateOfBirth(), getUsername(), getPassword());
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", username=" + username +
                ", password=" + password +
                '}';
    }
}

package org.goriachev.homework.entities;


import java.util.Date;

// Класс Пациент
public class Patient {

    // id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // id персоны
    private int personId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    // персона
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // дата рождения
    private Date bornDate;

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    // адрес
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // паспорт
    private String passport;

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }


    // конструктор по умолчанию
    public Patient() {
    }

    // конструктор инициализирующий
    public Patient(int id, int personId, Person person, Date bornDate, String address, String passport) {
        this.setId(id);
        this.setPersonId(personId);
        this.setPerson(person);
        this.setBornDate(bornDate);
        this.setAddress(address);
        this.setPassport(passport);
    }

    // конструктор инициализирующий
    public Patient(int id, int personId, String personSurname, String personName,
                   String personPatronymic, Date bornDate, String address, String passport) {
        this.setId(id);
        this.setPersonId(personId);
        this.setPerson(new Person(personId, personSurname, personName, personPatronymic));
        this.setBornDate(bornDate);
        this.setAddress(address);
        this.setPassport(passport);
    }
}

package org.goriachev.homework.entities;


// Класс Доктор
public class Doctor {

    // id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // id персоны
    public int personId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    // персона
    public Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // id специальности
    public int specialityId;

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    // специальность
    public Speciality speciality;

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    // цена
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // процент отчисления за приём врачу
    private double percent;

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    // зарплата
    public double getSalary() {
        return price * percent * 0.13;
    }

    // конструктор по умолчанию
    public Doctor() {
    }

    // конструктор инициализирующий
    public Doctor(int id, int personId, Person person, int specialityId, Speciality speciality, int price, double percent) {
        this.setId(id);
        this.setPersonId(personId);
        this.setPerson(person);
        this.setSpecialityId(specialityId);
        this.setSpeciality(speciality);
        this.setPrice(price);
        this.setPercent(percent);
    }

    // конструктор инициализирующий
    public Doctor(int id, int personId, String personSurname, String personName,
                  String personPatronymic, int specialityId, String specialityName,
                  int price, double percent) {
        this.setId(id);
        this.setPersonId(personId);
        this.setPerson(new Person(personId, personSurname, personName, personPatronymic));
        this.setSpecialityId(specialityId);
        this.setSpeciality(new Speciality(specialityId, specialityName));
        this.setPrice(price);
        this.setPercent(percent);
    }
}

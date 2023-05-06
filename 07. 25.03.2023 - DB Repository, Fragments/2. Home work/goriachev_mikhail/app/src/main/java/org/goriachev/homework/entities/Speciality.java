package org.goriachev.homework.entities;


// Класс Специальность
public class Speciality {

    // id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // название
    private String speciality;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }


    // конструктор по умолчанию
    public Speciality() {
    }

    // конструктор инициализирующий
    public Speciality(int id, String speciality) {
        this.setId(id);
        this.setSpeciality(speciality);
    }
}

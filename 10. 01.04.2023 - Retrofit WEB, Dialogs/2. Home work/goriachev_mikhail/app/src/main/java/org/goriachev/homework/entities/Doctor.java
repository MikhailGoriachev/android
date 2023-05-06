package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import org.goriachev.homework.infrastructure.JsonEntity;
import org.goriachev.homework.repositories.PeopleRepository;
import org.goriachev.homework.repositories.SpecialitiesRepository;

// Класс Доктор
public class Doctor extends JsonEntity<Doctor> {

    // название таблицы
    public static final String TABLE_NAME = "doctors";

    // поля
    public static final String PERSON_ID = "id_person";
    public static final String SPECIALITY_ID = "id_speciality";
    public static final String PRICE = "price";
    public static final String PERCENT = "percent";

    // персона
    public Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
    public Doctor(int id, Person person, Speciality speciality, int price, double percent) {
        this.setId(id);
        this.setPerson(person);
        this.setSpeciality(speciality);
        this.setPrice(price);
        this.setPercent(percent);
    }


    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static Doctor loadEntity(Context context, Cursor cursor) {

        var peopleRepository = new PeopleRepository(context);
        var specialityRepository = new SpecialitiesRepository(context);

        return new Doctor(
                cursor.getInt(cursor.getColumnIndex(ID)),
                peopleRepository.getById(cursor.getInt(cursor.getColumnIndex(PERSON_ID))),
                specialityRepository.getById(cursor.getInt(cursor.getColumnIndex(SPECIALITY_ID))),
                cursor.getInt(cursor.getColumnIndex(PRICE)),
                cursor.getInt(cursor.getColumnIndex(PERCENT))
        );
    }

    // установка данных из JSON
    public static Doctor getEntityFromJson(Context context, JsonObject jsonObject) {

        var peopleRepository = new PeopleRepository(context);
        var specialityRepository = new SpecialitiesRepository(context);

        return new Doctor(
                jsonObject.get(ID).getAsInt(),
                peopleRepository.getById(jsonObject.get(PERSON_ID).getAsInt()),
                specialityRepository.getById(jsonObject.get(SPECIALITY_ID).getAsInt()),
                jsonObject.get(PRICE).getAsInt(),
                jsonObject.get(PERCENT).getAsInt()
        );
    }

    // получение данных в формате JSON
    @Override
    public JsonObject getJsonFromEntity() {
        var object = new JsonObject();

        object.addProperty(Doctor.ID, getId());
        object.addProperty(Doctor.PERSON_ID, person.getId());
        object.addProperty(Doctor.SPECIALITY_ID, speciality.getId());
        object.addProperty(Doctor.PRICE, price);
        object.addProperty(Doctor.PERCENT, percent);

        return object;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s. %s. %s",
                person.getSurname(),
                person.getName().charAt(0),
                person.getPatronymic().charAt(0),
                speciality.getSpecialityName()
        );
    }
}

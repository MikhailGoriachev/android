package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import org.goriachev.homework.infrastructure.JsonEntity;
import org.goriachev.homework.repositories.PeopleRepository;
import org.goriachev.homework.utils.Utils;

import java.text.ParseException;
import java.util.Date;

// Класс Пациент
public class Patient extends JsonEntity<Patient> {

    // название таблицы
    public static final String TABLE_NAME = "patients";

    // поля
    public static final String PERSON_ID = "id_person";
    public static final String BORN_DATE = "born_date";
    public static final String ADDRESS = "address";
    public static final String PASSPORT = "passport";

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
    public Patient(int id, Person person, Date bornDate, String address, String passport) {
        this.setId(id);
        this.setPerson(person);
        this.setBornDate(bornDate);
        this.setAddress(address);
        this.setPassport(passport);
    }


    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static Patient loadEntity(Context context, Cursor cursor) {

        var peopleRepository = new PeopleRepository(context);

        try {
            return new Patient(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    peopleRepository.getById(cursor.getInt(cursor.getColumnIndex(PERSON_ID))),
                    Utils.getDate(cursor.getString(cursor.getColumnIndex(BORN_DATE))),
                    cursor.getString(cursor.getColumnIndex(ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(PASSPORT))
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // установка данных из JSON
    public static Patient getEntityFromJson(Context context, JsonObject jsonObject) {

        var peopleRepository = new PeopleRepository(context);

        try {
            return new Patient(
                    jsonObject.get(ID).getAsInt(),
                    peopleRepository.getById(jsonObject.get(PERSON_ID).getAsInt()),
                    Utils.getDate(jsonObject.get(BORN_DATE).getAsString()),
                    jsonObject.get(ADDRESS).getAsString(),
                    jsonObject.get(PASSPORT).getAsString()
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // получение данных в формате JSON
    @Override
    public JsonObject getJsonFromEntity() {
        var object = new JsonObject();

        object.addProperty(Patient.ID, getId());
        object.addProperty(Patient.PERSON_ID, person.getId());
        object.addProperty(Patient.BORN_DATE, Utils.dateHtmlToFormat(getBornDate()));
        object.addProperty(Patient.ADDRESS, address);
        object.addProperty(Patient.PASSPORT, passport);

        return object;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s. %s. %s",
                person.getSurname(),
                person.getName().charAt(0),
                person.getPatronymic().charAt(0),
                passport);
    }
}
